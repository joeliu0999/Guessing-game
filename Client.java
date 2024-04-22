import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static String IP = "10.136.225.22";
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
                System.out.println("Enter Start to play or Exit to quit: ");
                request = System.console().readLine();
                //error handling - allow only "Joke 1" or "Joke 2" or "Joke 3" or "Exit"
                while(!request.equals("Start") && !request.equals("Exit")){
                    System.out.println("Invalid request. Please enter a valid request: Start, Exit. Case Sensitive.");
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
                    reader.close();

                    String myWord= fileContent.toString();
                    System.out.println(myWord);
                    System.out.println("the word is "+myWord.length()+" charater long");
                    //
                    List<Character> wordCharaterList = new ArrayList<>();
                    for (int i=0; i<myWord.length();i++){
                        wordCharaterList.add(myWord.charAt(i));
                    }
                    for (int i=0; i<myWord.length(); i++){
                        System.out.println(wordCharaterList.get(i));
                    }
                    Scanner scanner = new Scanner(System.in);
                    String guesses="";
                    while(!guesses.equals("Exit")){
                        System.out.print("Enter a letter: ");
                        guesses = scanner.nextLine();
                        System.out.println(guesses);
                    }
                    scanner.close();


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