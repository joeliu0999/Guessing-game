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
                
                while(!request.equals("Start") && !request.equals("Exit")){
                    System.out.println("Invalid request. Please enter a valid request: Start, Exit. Case Sensitive.");
                    request = System.console().readLine();
                }
                outToServer.println(request);
                if(request == null || request.equals("Exit")){
                    exit = true;
                }else{
                    
                    //
                    BufferedReader reader = new BufferedReader(new FileReader(request+".txt"));
                    String line;
        
                    // Read each line and append it to the fileContent StringBuilder
                    while ((line = reader.readLine()) != null) {
                        fileContent.append(line); // Append each line with a newline character
                    }
                    reader.close();

                    String myWord= fileContent.toString();
                    System.out.println(myWord);
                    System.out.println("the word is "+myWord.length()+" charater long");
                    //
                    List<Character> wordCharaterList = new ArrayList<>();
                    List<Character> initList = new ArrayList<>();
                    for (int i=0; i<myWord.length();i++){
                        wordCharaterList.add(myWord.charAt(i));
                        initList.add('_');
                    }
                    
                    for (int i=0; i<myWord.length(); i++){
                        System.out.println(wordCharaterList.get(i));
                    }
                    Scanner scanner = new Scanner(System.in);
                    String guesses="";
                    int counter = 0;
                    boolean win=false;
                    while(counter < 7 && !win){
                        boolean correct = false;
                        System.out.print("Enter a letter: ");
                        guesses = scanner.nextLine();
                        if (guesses.equals("Exit")){
                            break;
                        }
                        if (guesses.length() == 1 && Character.isLetter(guesses.charAt(0))){
                            for (int i=0; i<wordCharaterList.size();i++){
                                if(guesses.equals(String.valueOf(wordCharaterList.get(i)))){
                                    initList.set(i,wordCharaterList.get(i));
                                    correct = true;
                                }
                            }
                            if(correct){
                                System.out.println(guesses+" is in the word");
                            }
                            else{
                                System.out.println(guesses+" is not in the word");
                                counter++;
                            }
                            System.out.println(7-counter+ " chance left");
                        }
                        else{
                            System.out.println("invalid input");
                        }

                        for(int i=0;i<initList.size();i++){
                            System.out.print(initList.get(i));
                        }
                        System.out.println("\n");
                        if (initList.equals(wordCharaterList)){
                            System.out.println("You win!");
                            win=true;
                        }
                    }
                    if(!win) System.out.println("You lose!");
                    scanner.close();
                }
                exit=true;
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