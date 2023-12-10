package Server;

import API.API;

import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static Server.MultiThreadedServer.api;

public class MultiThreadedServer {
    public static API api = new API();
    public static void main(String[] args) {
        int portNumber = 8000;


        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is running on port " + portNumber);
            api.connect();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                // Создаем новый поток для обработки клиента
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            api.disconect();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try (PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\JSON\\test.json"))){
            out.write(api.GET());
        }catch (Exception e){
            e.printStackTrace();
        }
        try (
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from " + clientSocket.getInetAddress() + ": " + inputLine);
                if (inputLine.equals("GET")){
                    File file = new File("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\JSON\\test.json");
                    dos.writeLong(file.length());

                    try (FileInputStream fis = new FileInputStream(file)){
                        byte[] buffer = new byte[4096];
                        int bytesRead;

                        while ((bytesRead = fis.read(buffer)) != -1){
                            dos.write(buffer, 0, bytesRead);
                        }
                        System.out.println("File sent to client");
                    }
                }
                else {
                    System.out.println("Invalid request from client.");
                }

            }
        } catch (IOException e) {
            System.out.println("Client " + clientSocket.getInetAddress() + " disconnected");
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
