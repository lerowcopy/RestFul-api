package Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.*;

public class GreetClient {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static BufferedReader reader;

    public static void main(String[] args){
        try{
            try{
                clientSocket = new Socket("127.0.0.1", 8000);

                reader = new BufferedReader(new InputStreamReader(System.in));

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                while (clientSocket.isConnected()){
                    System.out.println("Введите запрос:");
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();
                    /*String serverWord = in.readLine();
                    System.out.println(serverWord);*/
                    char[] buffer = new char[4096];
                    int bytesRead;

                    while ((bytesRead = in.read(buffer)) != -1){
                        String data = new String(buffer, 0, bytesRead);
                        System.out.println("Received from server: " + data);
                    }
                    out.flush();

                }

            }finally {
                clientSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
