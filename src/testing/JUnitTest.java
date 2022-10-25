package testing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.*;

import cards.Card;
import cards.implCards.Defuse;
import cards.implCards.TacoCat;
import decks.DeckHandler;
import explodingKittensGame.GameHandler;
import players.Player;
import players.PlayerHandler;
import settings.GamemodeSettings;

/**
 * Some test can not be run by themselves, since they depend on setup in earlier tests
 */
public class JUnitTest {
   
   GameHandler gameHandler;
   PlayerHandler playerHandler;
   DeckHandler deckHandler;

   private final int NR_TEST_PLAYERS = 5;

   @Before
   public void setup() throws Exception {
      gameHandler = new GameHandler(0, NR_TEST_PLAYERS);
      playerHandler = PlayerHandler.getInstance();
      deckHandler = DeckHandler.getInstance();
   }

   private void emptyDeck() {
      while(deckHandler.getPlaydeckSize() > 0){
         deckHandler.drawCard();
      }
   }

   /**
    * 1. There can be between 2 and 5 players
    this test only viable for the base deck
    */
   @Test
   public void between2to5players(){
      assertEquals(2, GamemodeSettings.getMinPlayers());
      assertEquals(5, GamemodeSettings.getMaxAllowedPlayers());

      try {
         new GameHandler(1, 0);
      } catch (Exception e) {
         assertEquals("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings"
                     , e.getMessage());
      }

      try {
         new GameHandler(0, 1);
      } catch (Exception e) {
         assertEquals("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings"
                     , e.getMessage());
      }

      try {
         new GameHandler(3, 3);
      } catch (Exception e) {
         assertEquals("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings"
                     , e.getMessage());
      }

      try {
         new GameHandler(6, 0);
      } catch (Exception e) {
         assertEquals("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings"
                     , e.getMessage());
      }

      try {
         new GameHandler(0, 6);
      } catch (Exception e) {
         assertEquals("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings"
                     , e.getMessage());
      }

      try {
         new GameHandler(0, 2);
         
         assertTrue(true);
      } catch (Exception e) {
         assertTrue(false); //if this is reached, the GameHandler above failed
      }

      try {
         new GameHandler(0, 5);
         
         assertTrue(true);
      } catch (Exception e) {
         assertTrue(false); //if this is reached, the GameHandler above failed
      }




   }

   @Test
   public void check2PlayersSetup(){
      checkPlayerCardSetup(2);
   }

   @Test
   public void check4PlayersSetup(){
      checkPlayerCardSetup(4);
   }

   @Test
   public void check5PlayerSetup(){
      checkPlayerCardSetup(5);
   }

   public void checkPlayerCardSetup(int nrOfPlayers) {

      int defuseCards = (nrOfPlayers<5) ?  2 : 1;

      Card drawnCard;
      class CountCards {
         Card card;
         int occurance;

         public CountCards(Card card){
            this.card = card;
            occurance = 1;
         }
      }

      emptyDeck();
      GamemodeSettings.setupGameDeck(nrOfPlayers);

      LinkedList<CountCards> cardOccurance = new LinkedList<CountCards>();
      boolean found;

      while(deckHandler.getPlaydeckSize() > 0){
         drawnCard = deckHandler.drawCard();

         found = false;
         for(CountCards countCards : cardOccurance) {

            if(drawnCard.getName().equals(countCards.card.getName())){

               countCards.occurance++;
               found = true;
               break;
            }
         }
         if(!found){
            cardOccurance.add(new CountCards(drawnCard));
         }
         
      }

      assertEquals(12, cardOccurance.size());

      while(cardOccurance.peek() != null){
         CountCards countCards = cardOccurance.removeFirst();

         switch (countCards.card.getName()) {
            case "Defuse":
               assertEquals(defuseCards, countCards.occurance);
               break;

            case "Nope":
               assertEquals(5, countCards.occurance);
               break;

            case "See The Future":
               assertEquals(5, countCards.occurance);
               break;
         
            default:
               assertEquals(4, countCards.occurance);
               break;
         }
      }

   }
   
   /**
    * 2. Each player should be given 1 Defuse card 
    */
   @Test
   public void oneDefusePerPlayer() {

      emptyDeck();
      GamemodeSettings.setupGameDeck(NR_TEST_PLAYERS);
      GamemodeSettings.dealCards();
      for(Player p : playerHandler.getActivePlayers()) {
         assertTrue(p.hasDefuse());
      }
      emptyDeck();

   }

   @Test
   public void shuffleDeckBeforeDealing(){

      
   }


}
