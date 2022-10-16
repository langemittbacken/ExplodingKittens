package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * represent a player playing the game.
 * @author langemittbacken
 *
 */
public class Player{
   PlayerHand hand;
   private int playerID;
   private Socket connection;
   private ObjectInputStream inFromClient;
   private ObjectOutputStream outToClient;
   private boolean isBot;
   private boolean online;

   public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
      this.playerID = playerID; this.connection = connection; this.inFromClient = inFromClient; this.outToClient = outToClient; this.isBot = isBot;
      if(connection == null) {
         this.online = false;
      } else {
         this.online = true;
      }
      this.hand = new PlayerHand();
      
   }
   
   public boolean isOnline() {
      return online;
      
   }

}
