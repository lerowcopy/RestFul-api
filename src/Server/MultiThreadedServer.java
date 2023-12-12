package Server;

import API.API;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static Server.MultiThreadedServer.api;
import static Server.MultiThreadedServer.objectMapper;

public class MultiThreadedServer {
    public static API api = new API();
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) {
        int portNumber = 8000;


        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is running on port " + portNumber);
            api.connect();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

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
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        try (
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true)
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received from " + clientSocket.getInetAddress() + ": " + inputLine);
                if (inputLine.equals("GET")){
                    GETServer(dos);
                }
                else if (inputLine.equals("POST")) {

                    POSTServer(dis);
                }
                else if (inputLine.equals("PUT")){
                    PUTServer(dis);
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

    public void GETServer(DataOutputStream dos) throws IOException{
        try (PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Server\\test.json"))){
            out.write(api.GET());
        }catch (Exception e){
            e.printStackTrace();
        }

        File file = new File("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Server\\test.json");
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

    public void POSTServer (DataInputStream dis) throws IOException{
        long fileSize = dis.readLong();
        if (fileSize != -1){
            try (FileOutputStream fos = new FileOutputStream("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Server\\put.json")){
                byte[] buffer = new byte[4096];
                int bytesRead = dis.read(buffer);
                fos.write(buffer, 0, bytesRead);
                System.out.println("File received from client.");

                try{
                    File file = new File("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Server\\put.json");

                    JsonNode jsonNode = objectMapper.readTree(file);

                    api.POST(jsonNode);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void PUTServer (DataInputStream dis) throws IOException{
        long fileSize = dis.readLong();
        if (fileSize != -1){
            try (FileOutputStream fos = new FileOutputStream("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Server\\post.json")){
                byte[] buffer = new byte[4096];
                int bytesRead = dis.read(buffer);
                fos.write(buffer, 0, bytesRead);
                System.out.println("File received from client.");

                try{
                    File file = new File("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Server\\post.json");

                    JsonNode jsonNode = objectMapper.readTree(file);

                    api.PUT(jsonNode);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
