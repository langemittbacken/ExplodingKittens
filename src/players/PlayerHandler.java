package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import cards.Card;
import exceptions.PlayerNotFoundException;

/**
 * handles all the players in a game and keeps track of whose turn it is.
 * Singleton design pattern
 * @author langemittbacken
 *
 */
public class PlayerHandler {
   
   private LinkedList<Player> activePlayers;
   private LinkedList<Player> explodedPlayers;
   private static int secondsToInterruptWithNope = 5;
   private static PlayerHandler instance = new PlayerHandler();

   private Player currentPlayer;// the player first in the player list
   private int turnsLeft;
   private boolean clockwiseTurnOrder;
   private boolean ongoingAttack;

   private PlayerHandler() {
      activePlayers = new LinkedList<Player>();
      explodedPlayers = new LinkedList<Player>();
      clockwiseTurnOrder = true;
      turnsLeft = 1;
      ongoingAttack = false;
   }

   public static PlayerHandler getInstance() {
      return instance;
   }
   
   /**
    * called to start the turn order of the game
    * @param randomStartPlayer if first player should be randomized.
    */
   public void startTurnOrder(boolean randomStartPlayer) {
      
      currentPlayer = activePlayers.getFirst();
      if (!randomStartPlayer) {
         return;
      }
      
      int positionsToMoveForward = (int)(Math.random()*activePlayers.size());
     
      while(positionsToMoveForward>0) {
         nextPlayer();
         positionsToMoveForward--;
      }
   }

   public void attackNextPlayer(int totalNrOfTurns) {
      turnsLeft = totalNrOfTurns;
      nextPlayer();
      ongoingAttack = true;
   }

   public void attackPlayer(Player player, int totalNrOfTurns) {
      turnsLeft = totalNrOfTurns;
      while(currentPlayer != player) {
         nextPlayer();
      }
      ongoingAttack = true;
   }

   public void nextTurn() {
      if(turnsLeft<=1){
         ongoingAttack = false;
         nextPlayer();
         turnsLeft = 1;
      } else {
         turnsLeft --;
      }
      
   }
   
   private Player nextPlayer() {
      if (clockwiseTurnOrder) {
         currentPlayer = activePlayers.removeFirst(); //should already be first if nothing unpredicted has happened
         activePlayers.addLast(currentPlayer);
         currentPlayer = activePlayers.getFirst();
         
      } else {
         currentPlayer = activePlayers.removeLast();
         activePlayers.addFirst(currentPlayer);
      }
      return currentPlayer;
   }

   public static int getSecondsToInterruptWithNope(){
      return secondsToInterruptWithNope;
   }

   public void addPlayer(int playerID, boolean isBot, Socket connectionSocket, ObjectInputStream inFromClient,
         ObjectOutputStream outToClient) {
      
      activePlayers.add(new Player(playerID, isBot, connectionSocket, inFromClient, outToClient));    
   }

   public Player getPlayer(int playerID) throws PlayerNotFoundException{
      for (Player p : activePlayers) {
         if(p.getPlayerID() == playerID) return p;
      }
      throw new PlayerNotFoundException("Player with playerID "+ playerID +" not found in PlayerHandler");
   }

   public LinkedList<Player> getAllPlayers(){
      LinkedList<Player>returnList = new LinkedList<Player>(activePlayers);
      returnList.addAll(explodedPlayers);
      return returnList;
   }

   public LinkedList<Player> getActivePlayers(){
      return activePlayers;
   }

   public LinkedList<Player> getExplodedPlayers(){
      return explodedPlayers;
   }
   
   public Player getCurrentPlayer() {
      return currentPlayer;
   }
   
   public boolean getClockwiseTurnOrder() {
      return clockwiseTurnOrder;
   }
   
   public void setClockwiseTurnOrder(boolean clockwise) {
      clockwiseTurnOrder = clockwise;
   }
   
   public void toggleClockwiseTurnOrder() {
      clockwiseTurnOrder = !clockwiseTurnOrder;
   }

   public int getTurnsLeft() {
      return turnsLeft;
   }

   public boolean OngoingAttack() {
      return ongoingAttack;
   }

   /**
    * remove player from the game and return the players hand
    * @param player - player to explode
    * @return the players hand
    */
   public LinkedList<Card> explodePlayer(Player player) {
      activePlayers.remove(player);
      explodedPlayers.add(player);
      return player.emptyHand();
   }

   /**
    * this method assumes the argument is looking for the player ID of a player
    * @param player - either a number ex "0" or "player 0"
    * @return the player with PlayerID same as argument
    * @throws PlayerNotFoundException - if argument does not match any player
    */
   public Player getActivePlayerFromString(String wantedPlayer) throws PlayerNotFoundException{
      String player = wantedPlayer.trim();

      if(player.startsWith("player") || player.startsWith("Player")) {
         player = player.substring("player".length()).trim();
      }
      try {
         int playerID = Integer.parseInt(player);

         return getPlayer(playerID);

      } catch (Exception e) {
         throw new PlayerNotFoundException(wantedPlayer + "was invalid input");
      } 
   }
}
