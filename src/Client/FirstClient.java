package Client;

import java.io.*;
import java.net.*;

public class FirstClient {
    private static Socket clientSocket;

    public static void main(String[] args){
        try{
            try{
                clientSocket = new Socket("127.0.0.1", 8000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
                DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                while (clientSocket.isConnected()){
                    System.out.println("Enter a request:");
                    String word = reader.readLine();
                    out.write(word + "\n");
                    out.flush();

                    switch (word) {
                        case "GET" -> GETClient(dis);
                        case "POST" -> POSTClient(dos);
                        case "PUT" -> PUTClient(dos);
                        case "DELETE" -> DELETEClient(dos);
                    }
                }
            }finally {
                clientSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void GETClient(DataInputStream dis) throws IOException {
        long fileSize = dis.readLong();
        if (fileSize != -1){
            String path = System.getProperty("user.dir") + "\\Client\\get.json";
            try (FileOutputStream fos = new FileOutputStream(path)){
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

    public static void POSTClient(DataOutputStream dos) throws IOException{
        String path = System.getProperty("user.dir") + "\\Client\\post.json";
        File file = new File(path);
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

    public static void PUTClient(DataOutputStream dos) throws IOException{
        String path = System.getProperty("user.dir") + "\\Client\\put.json";
        File file = new File(path);
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

    public static void DELETEClient (DataOutputStream dos) throws IOException{
        String path = System.getProperty("user.dir") + "\\Client\\delete.json";
        File file = new File(path);
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
