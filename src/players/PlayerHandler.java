package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * handles all the players in a game and keeps track of whose turn it is.
 * @author langemittbacken
 *
 */
public class PlayerHandler {
   
   private ArrayList<Player> allPlayers;
   
   public PlayerHandler() {
      allPlayers = new ArrayList<Player>();
   }
   
   

   private static int secondsToInterruptWithNope = 5;
   
   public static int getSecondsToInterruptWithNope(){
      return secondsToInterruptWithNope;
   }

   public void addPlayer(int playerID, boolean isBot, Socket connectionSocket, ObjectInputStream inFromClient,
         ObjectOutputStream outToClient) {
      
      allPlayers.add(new Player(playerID, isBot, connectionSocket, inFromClient, outToClient));
      
   }
   
}
