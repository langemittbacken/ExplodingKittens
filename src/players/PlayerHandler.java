package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import exceptions.PlayerNotFoundException;

/**
 * handles all the players in a game and keeps track of whose turn it is.
 * Singleton design pattern
 * @author langemittbacken
 *
 */
public class PlayerHandler {
   
   private LinkedList<Player> allPlayers;
   private static int secondsToInterruptWithNope = 5;
   private static PlayerHandler instance = new PlayerHandler();

   private Player currentPlayer;// the player first in the player list
   private int turnsLeft;
   private boolean clockwiseTurnOrder;
   private boolean ongoingAttack;

   private PlayerHandler() {
      allPlayers = new LinkedList<Player>();
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
      
      currentPlayer = allPlayers.getFirst();
      if (!randomStartPlayer) {
         return;
      }
      
      int positionsToMoveForward = (int)(Math.random()*allPlayers.size());
     
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
         currentPlayer = allPlayers.removeFirst(); //should already be first if nothing unpredicted has happened
         allPlayers.addLast(currentPlayer);
         currentPlayer = allPlayers.getFirst();
         
      } else {
         currentPlayer = allPlayers.removeLast();
         allPlayers.addFirst(currentPlayer);
      }
      return currentPlayer;
   }

   public static int getSecondsToInterruptWithNope(){
      return secondsToInterruptWithNope;
   }

   public void addPlayer(int playerID, boolean isBot, Socket connectionSocket, ObjectInputStream inFromClient,
         ObjectOutputStream outToClient) {
      
      allPlayers.add(new Player(playerID, isBot, connectionSocket, inFromClient, outToClient));    
   }

   public Player getPlayer(int playerID) throws PlayerNotFoundException{
      for (Player p : allPlayers) {
         if(p.getPlayerID() == playerID) return p;
      }
      throw new PlayerNotFoundException("Player with playerID "+ playerID +" not found in PlayerHandler");
   }

   public LinkedList<Player> getAllPlayers(){
      return allPlayers;
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
}
