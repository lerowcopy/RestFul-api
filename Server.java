package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

public class Server {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main (String[] args){
        try{
            try{

                try{
                        server = new ServerSocket(8000);
                        System.out.println("Сервер запущен");
                        clientSocket = server.accept();
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    while (clientSocket.isConnected()){

                        String word = in.readLine();

                        System.out.println(word);

                        out.write("Вы написали" + word);

                        out.flush();
                    }

                }finally {
                    /*clientSocket.close();
                    in.close();
                    out.close();*/
                }
            }finally {
                /*server.close();*/
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
