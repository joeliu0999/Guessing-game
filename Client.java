import java.io.*;
import java.net.*;
public class Client {
    public static String IP = "100.64.7.92";
    public static void main(String[] args) throws Exception {
        
        String request ="Joke 1";
        String myIP = IP;
        boolean exit = false;
        try{
              //Get the user's request via system.in
            System.out.println("Sending request to server...");
            Socket clientSocket = new Socket(myIP, 8099);
            System.out.println("Connected to server");
            InputStream in = clientSocket.getInputStream();
            OutputStream out = clientSocket.getOutputStream();
            StringBuilder fileContent = new StringBuilder(); // use to read string

            //print writer to send request to server
            PrintWriter outToServer = new PrintWriter(out, true);
            while(exit == false){
                //get the request from user via system.in into request
                System.out.println("Enter a request: ");
                request = System.console().readLine();
                //error handling - allow only "Joke 1" or "Joke 2" or "Joke 3" or "Exit"
                while(!request.equals("Joke 1") && !request.equals("Joke 2") && !request.equals("Joke 3") && !request.equals("Exit")){
                    System.out.println("Invalid request. Please enter a valid request: Exit, Joke 1, Joke 2, or Joke 3. Case Sensitive.");
                    request = System.console().readLine();
                }
                outToServer.println(request);
                if(request == null || request.equals("Exit")){
                    exit = true;
                }else{
                    //receive the file from server
                    FileOutputStream fileOut = new FileOutputStream("In-client/" + request + ".txt");
                    byte[] buffer = new byte[2048];
                    //read the file from server and write it to the file - while loop to deal with larger files 
                    int bytesRead;
                    if ((bytesRead = in.read(buffer)) != -1){
                        fileOut.write(buffer, 0, bytesRead);
                    }
                    fileOut.close(); 
                    System.out.println(request + " received from server");
                    
                    //
                    BufferedReader reader = new BufferedReader(new FileReader("In-client/" +request+".txt"));
                    String line;
        
                    // Read each line and append it to the fileContent StringBuilder
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line).append("\n"); // Append each line with a newline character
                    }
        
                    // Close the file
                    reader.close();
                    System.out.println(fileContent.toString());
                    //

                }
                //close stuff on exit
                if(exit){
                    clientSocket.close();
                    in.close();
                    out.close();
                    outToServer.close();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}