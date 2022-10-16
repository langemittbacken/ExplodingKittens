package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * handles all the players in a game and keeps track of whose turn it is.
 * Singleton design pattern
 * @author langemittbacken
 *
 */
public class PlayerHandler {
   
   private static ArrayList<Player> allPlayers;
   private static int secondsToInterruptWithNope = 5;
   private static PlayerHandler instance = new PlayerHandler();

   private PlayerHandler() {
      allPlayers = new ArrayList<Player>();
   }

   public static PlayerHandler getInstance() {
      return instance;
   }

   public static int getSecondsToInterruptWithNope(){
      return secondsToInterruptWithNope;
   }

   public void addPlayer(int playerID, boolean isBot, Socket connectionSocket, ObjectInputStream inFromClient,
         ObjectOutputStream outToClient) {
      
      allPlayers.add(new Player(playerID, isBot, connectionSocket, inFromClient, outToClient));
      
   }

   public Player getPlayer(int playerID){
      return allPlayers.get(playerID);
   }

   public ArrayList<Player> getAllPlayers(){
      return allPlayers;
   }
   
}
