package Client;

import java.io.*;
import java.net.*;

public class FirstClient {
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

                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                out = new PrintWriter(clientSocket.getOutputStream(), true);
                while (clientSocket.isConnected()){
                    System.out.println("Enter a request:");
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();

                    if (word.equals("GET")){
                        long fileSize = dis.readLong();
                        if (fileSize != -1){
                            try (FileOutputStream fos = new FileOutputStream("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Client\\test.json")){
                                byte[] buffer = new byte[4096];
                                int bytesRead = dis.read(buffer);
                                fos.write(buffer, 0, bytesRead);
                                System.out.println("File received from server.");
                            }
                        }
                        else {
                            System.out.println("File does not exist on the server.");
                        }
                    }
                    else if (word.equals("PUT")){
                        File file = new File("C:\\Users\\79531\\IdeaProjects\\RESTFULL api\\Client\\put.json");
                        dos.writeLong(file.length());

                        try (FileInputStream fis = new FileInputStream(file)){
                            byte[] buffer = new byte[4096];
                            int bytesRead;

                            while ((bytesRead = fis.read(buffer)) != -1){
                                dos.write(buffer, 0, bytesRead);
                            }
                            System.out.println("File sent to server");
                        }
                    }
                }
            }finally {
                clientSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
