package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import cards.Card;

/**
 * represent a player playing the game.
 * @author langemittbacken
 *
 */
public class Player{
   private PlayerHand hand;
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

   public void addCardToHand(Card card) {
      hand.addCard(card);
   }

   public int getPlayerID(){
      return playerID;
   }

   public Socket getConnection(){
      return connection;
   }
   
   public ObjectInputStream getInFromClient(){
      return inFromClient;
   }

   public ObjectOutputStream getOutToClient(){
      return outToClient;
   }

   public boolean isBot(){
      return isBot;
   }

   public boolean isOnline(){
      return online;
      
   }

}
