import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Server {
    public static void main(String argv[]) throws Exception{
        try{
            Random random = new Random();
            String clientRequest = null;
            ServerSocket welcomeSocket = new ServerSocket(8099);
            System.out.println("attempting to connect to client...");
            Socket connectionSocket = welcomeSocket.accept();
            System.out.println("Client connected!");

            String wordsString = "Elephant, Computer, Rainbow, Butterfly, Sunshine, Guitar, Pizza, Universe, Chocolate, Adventure, Mountain, Jellyfish, Strawberry, Waterfall, Friendship, Dragonfly, Happiness, Cucumber, Galaxy, Keyboard, Zebra, Octopus, Watermelon, Pineapple, Dragon, Tiger, Lion, Ocean, Beach, Vacation, Moonlight, Starlight, Fireworks, Carnival, Carousel, Penguin, Kangaroo, Giraffe, Rhinoceros, Moon, Constellation, Satellite, Supernova, Astronaut, Spaceship, Rocket, Observatory, Eclipse, Planetarium, Astronomer, Shooting Star, Black Hole, Comet, Solar System, Milky Way, Nebula, Orbit, Celestial, Cosmology, Spacewalk, Gravity, Meteor, Aurora, Astronomy";
            String[] wordsArray = wordsString.split("\\s*,\\s*");
            List<String> wordBank = Arrays.asList(wordsArray);

                while(true) {
                    File file = new File("start.txt");
                    int randomNumber = random.nextInt(wordBank.size());
                    String myWord = wordBank.get(randomNumber);
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(myWord);
                    fileWriter.close();
                    
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

                    File outFile = new File("start.txt");
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