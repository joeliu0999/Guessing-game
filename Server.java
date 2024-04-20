import java.io.*;
import java.net.*;
public class Server {
    public static void main(String argv[]) throws Exception{
        try{
            String clientRequest = null;
            ServerSocket welcomeSocket = new ServerSocket(8099);
            System.out.println("attempting to connect to client...");
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Client connected!");
                while(true) {
                    //input/output streams
                    System.out.println("Awaiting request from client...");

                    InputStream in = connectionSocket.getInputStream();
                    OutputStream out = connectionSocket.getOutputStream();

                    //read the request from the client
                    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(in));
                    clientRequest = inFromClient.readLine();
                    System.out.println("Received request from client: " + clientRequest);
                    
                    //if Exit is called, close the connection
                    //check if client request is NULL

                    if(clientRequest == null || clientRequest.equals("Exit")){
                        welcomeSocket.close();
                        connectionSocket.close();
                        in.close();
                        out.close();
                        inFromClient.close();
                        break;
                    }
                        //send the file to the client
                    //read the file into a filestream
                    File outFile = new File("Out/" + clientRequest + ".txt");
                    FileInputStream fileIn = new FileInputStream(outFile);
                    byte[] buffer = new byte[2048];
                    int bytesRead;
                    while((bytesRead = fileIn.read(buffer)) != -1){
                        out.write(buffer, 0, bytesRead);
                    }
                    fileIn.close();
                    System.out.println("File sent. Waiting for next request.");
                    clientRequest = null;
                }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}