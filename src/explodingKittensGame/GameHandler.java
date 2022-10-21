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
         Player currentPlayer = playerHandler.getCurrentPlayer();

         action = getActionFromCurrentPlayer(currentPlayer);
         
         if(action == "pass"){
            currentPlayer.addCardToHand(deckHandler.drawCard());
         } else {
            currentPlayer.getCard ////////////////////////////////////////// HITTA KORT I HANDEN, TA KORTET OCH SPELA KORTET
         }

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
      server.sendMsgToAllPlayers("It is now Player " + Integer.toString(id) + "'s turn");
   }

   private String getActionFromCurrentPlayer(Player currentPlayer) {

      server.sendMessage(currentPlayer, currentPlayer.printHand());
      server.sendMessage(currentPlayer, "*options*: play card or pass");

      return server.readMessage(currentPlayer, false);

   }
}
