package explodingKittensGame;

import java.util.concurrent.TimeUnit;

import cards.Card;
import decks.DeckHandler;
import exceptions.CardNotFoundException;
import players.Player;
import players.PlayerHandler;
import server.Server;
import settings.GamemodeSettings;

/**
 * puts together all game logic.
 * @author langemittbacken
 *
 */
public class GameHandler {
   
   Server server;
   PlayerHandler playerHandler;
   DeckHandler deckHandler;

   Player currentPlayer;


    public GameHandler(int nrOfPlayers, int nrOfBots) throws Exception{

      if(GamemodeSettings.getMaxAllowedPlayers()<(nrOfPlayers+nrOfBots) || GamemodeSettings.getMinPlayers()>(nrOfPlayers+nrOfBots)) {
         throw new Exception("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings");
      };

      try {
         Server.startInstance(nrOfPlayers,nrOfBots);
         server = Server.getInstance();

      } catch (Exception e1) {
         e1.printStackTrace();
      } 
      
      playerHandler = PlayerHandler.getInstance();
      deckHandler = DeckHandler.getInstance();

      setupDeckAndPlayerHands(nrOfPlayers+nrOfBots);

      sendHandToPlayers();

      playerHandler.startTurnOrder(true);
      sendGameStartedMessage();

      while(true) {
         gameLoop();
         if(playerHandler.getActivePlayers().size() == 1){
            break;
         }
      }
      server.sendMsgToAllPlayers("Player " + playerHandler.getAllPlayers().getFirst().getPlayerID() + " Won!\n");
    }

   private void gameLoop() {
      String action;
      Card card;

      if(currentPlayer != playerHandler.getCurrentPlayer()) {
         currentPlayer = playerHandler.getCurrentPlayer();
         sendWhoseTurnItIs(currentPlayer);
      }
      
      action = getActionFromCurrentPlayer(currentPlayer).trim();
      System.out.println(action + " sent by Player" + currentPlayer.getPlayerID());
      
      //playing 2 cards
      if(action.length() > 2 && action.substring(0, 2).equalsIgnoreCase("2x")){
         server.sendMessage(currentPlayer, "Sorry but 2x is yet to be implemented");
         //TO-DO ----------------------------------------------------------------------------
         return;
      }
      //playing 3 cards
      if(action.length() > 2 && action.substring(0, 2).equalsIgnoreCase("3x")){
         server.sendMessage(currentPlayer, "Sorry but 3x is yet to be implemented");
         //TO-DO ----------------------------------------------------------------------------
         return;
      }

      if(!action.equalsIgnoreCase("pass")){
         try {
            card = currentPlayer.takeCardFromHand(action);
            deckHandler.playCard(card);
            server.sendMsgToAllPlayers("Player " + currentPlayer.getPlayerID() + " played [" + card.getName() + "]");

         } catch (Exception e) {
            server.sendMessage(currentPlayer, "could not handle your action \"" + action + "\"");
         }
         return;
      }

      server.sendMsgToAllPlayers("Player " + currentPlayer.getPlayerID() + " drew a card");
      currentPlayer.addCardToHand(deckHandler.drawCard());
      sendHandToPlayer(currentPlayer);
      
      playerHandler.nextTurn();
   }

   private void setupDeckAndPlayerHands(int totalPlayers) {

      GamemodeSettings.setupGameDeck(totalPlayers);
      deckHandler.shuffleDeck();
      GamemodeSettings.dealCards();
      GamemodeSettings.finalizeSetup(totalPlayers);
      deckHandler.shuffleDeck();
   }
   
/**
 * only for JUnit testing, should not be used!
 * @param pHandler - just to make sure playerhandler is present
 * @param deckHandler - just to make sure deckHandler is present
 */
   public void testSetupDeckAndPlayerHands(PlayerHandler pHandler, DeckHandler deckHandler) {
      setupDeckAndPlayerHands(playerHandler.getActivePlayers().size());
   }
   
   private void sendHandToPlayers() {
      for(Player p : playerHandler.getActivePlayers()){
         sendHandToPlayer(p);
      }
   }
   
   private void sendHandToPlayer(Player p) {
      server.sendMessage(p, "Your hand:");
      server.sendMessage(p, p.printHand() + "\n");
   }

   private void sendGameStartedMessage() {
      server.sendMsgToAllPlayers("Game started!\n");
   }

   private void sendWhoseTurnItIs(Player currentPlayer) {

      int id = currentPlayer.getPlayerID();
      server.sendMsgToAllPlayers("Player " + Integer.toString(id) + "'s turn\n");
      if(playerHandler.getTurnsLeft() > 1){
         server.sendMsgToAllPlayers("Player " + Integer.toString(id) + " have " + playerHandler.getTurnsLeft() + " turns to play\n");
      }
   }

   private String getActionFromCurrentPlayer(Player currentPlayer) {

      server.sendMessage(currentPlayer, currentPlayer.printHand());
      server.sendMessage(currentPlayer, "*options*: play card(s) or pass");

      return server.readMessage(currentPlayer, false);

   }
}
