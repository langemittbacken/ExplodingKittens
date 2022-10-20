package explodingKittensGame;

import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;

import decks.DeckHandler;
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

      server.sendMsgToAllPlayers("goa gubbar i göteborg");

      while(true) {
         //HERE THE GAME WILL BE CONTROLLED IN LIKE TURNS AND STUFF
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
}
