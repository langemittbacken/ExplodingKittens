package explodingKittensGame;

import java.util.concurrent.TimeUnit;

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
   String action;


    public GameHandler(int nrOfPlayers, int nrOfBots) throws Exception{

      if(GamemodeSettings.getMaxAllowedPlayers()<(nrOfPlayers+nrOfBots) || GamemodeSettings.getMinPlayers()>(nrOfPlayers+nrOfBots)) {
         throw new Exception("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings");
      };

      try {
         server = new Server(nrOfPlayers,nrOfBots);

      } catch (Exception e1) {
         e1.printStackTrace();
      } 
      
      playerHandler = PlayerHandler.getInstance();
      deckHandler = DeckHandler.getInstance();

      setupDeckAndPlayerHands(nrOfPlayers+nrOfBots);

      sendHandToPlayers();

      playerHandler.startTurnOrder(true);
      sendGameStartedMessage();

      while(true) {//HERE THE GAME WILL BE CONTROLLED IN LIKE TURNS AND STUFF
         sendWhoseTurnItIs();
         currentPlayer = playerHandler.getCurrentPlayer();

         action = getActionFromCurrentPlayer(currentPlayer).trim();
         System.out.println(action + " sent by Player" + currentPlayer.getPlayerID());
         
         //playing 2 cards
         if(action.length() > 2 && action.substring(0, 2).equalsIgnoreCase("2x")){
            server.sendMessage(currentPlayer, "Sorry but 2x is yet to be implemented");
            //TO-DO ----------------------------------------------------------------------------
            continue;
         }
         //playing 3 cards
         if(action.length() > 2 && action.substring(0, 2).equalsIgnoreCase("3x")){
            server.sendMessage(currentPlayer, "Sorry but 3x is yet to be implemented");
            //TO-DO ----------------------------------------------------------------------------
            continue;
         }



         if(!action.equalsIgnoreCase("pass")){
            try {
               deckHandler.playCard(currentPlayer.takeCardFromHand(action));
               

            } catch (Exception e) {
               server.sendMessage(currentPlayer, "could not handle your action \"" + action + "\"");
            }
            continue;
         }

         currentPlayer.addCardToHand(deckHandler.drawCard());
         playerHandler.nextTurn();
      }
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
      setupDeckAndPlayerHands(playerHandler.getAllPlayers().size());
   }
   
   private void sendHandToPlayers() {
      for(Player p : playerHandler.getAllPlayers()){
         server.sendMessage(p, "Your hand:");
         server.sendMessage(p, p.printHand() + "\n");
      }
   }
   
   private void sendGameStartedMessage() {
      server.sendMsgToAllPlayers("Game started!\n");
   }

   private void sendWhoseTurnItIs() {

      int id = playerHandler.getCurrentPlayer().getPlayerID();
      server.sendMsgToAllPlayers("Player " + Integer.toString(id) + "'s turn\n");
   }

   private String getActionFromCurrentPlayer(Player currentPlayer) {

      server.sendMessage(currentPlayer, currentPlayer.printHand());
      server.sendMessage(currentPlayer, "*options*: play card or pass");

      return server.readMessage(currentPlayer, false);

   }
}
