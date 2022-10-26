package testing;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.*;

import cards.Card;
import decks.DeckHandler;
import exceptions.PlayerNotFoundException;
import explodingKittensGame.GameHandler;
import players.Player;
import players.PlayerHandler;
import settings.GamemodeSettings;


public class JUnitTest {
   
   GameHandler gameHandler;
   PlayerHandler playerHandler;
   DeckHandler deckHandler;

   private final int NR_TEST_PLAYERS = 2;

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

   private void emptyAllHands(){
      for(Player player : playerHandler.getActivePlayers()){
         player.emptyHand();
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

   /**
    * 3. The deck should consist of the following cards before the first hands are dealt: 
   a. 2 Defuse cards for 2-4 players, 1 Defuse card for 5 players. 
   b. 4 Attack cards 
   c. 4 Favor cards 
   d. 5 Nope cards 
   e. 4 Shuffle cards 
   f. 4 Skip cards 
   g. 5 SeeTheFuture cards 
   h. 4 HairyPotatoCat cards 
   i. 4 Cattermelon cards 
   j. 4 RainbowRalphingCat cards 
   k. 4 TacoCat cards 
   l. 4 OverweightBikiniCat cards 
    * @param nrOfPlayers
    */
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

   /**
    * 4. Shuffle the deck before the first hands are dealt. 
    * @throws PlayerNotFoundException
    */
   @Test
   public void shuffleDeckBeforeDealing() throws PlayerNotFoundException{
      gameHandler.testSetupDeckAndPlayerHands(playerHandler);

      LinkedList<Card> savedHand = playerHandler.getPlayer(0).emptyHand();

      emptyAllHands();
      emptyDeck();

      gameHandler.testSetupDeckAndPlayerHands(playerHandler);

      assertNotEquals(savedHand, playerHandler.getPlayer(0).emptyHand());

      emptyAllHands();
      emptyDeck();
   }

   /**
    * 5. deal 7 cards (aka 8 total cards in hand with the defuse)
    */
   @Test
   public void deal7Cards(){
      emptyAllHands();
      emptyDeck();
      gameHandler.testSetupDeckAndPlayerHands(playerHandler);

      for(Player player : playerHandler.getActivePlayers()){
         assertEquals(8, player.emptyHand().size());
      }
      emptyAllHands();
      emptyDeck();
   }

   /**
    * 6. Insert enough ExplodingKitten cards into the deck 
    * so there is 1 fewer than the number of players 
    */
   @Test
   public void insertExplodingKittens(){

      int explKittens;
      emptyDeck();
      gameHandler.testSetupDeckAndPlayerHands(playerHandler);

      explKittens = 0;
      while(deckHandler.getPlaydeckSize() > 0){
         if(deckHandler.drawCard().getName().equals("Exploding Kitten")){
            explKittens++;
         }
      }
      assertEquals(playerHandler.getActivePlayers().size() - 1, explKittens);
      emptyDeck();
      emptyAllHands();
   }

   /**
    * 7. Shuffle the deck before the game starts 
    * if the 10 first cards are same order, we consider the deck unshuffled
    * if >=8 it will also fail
    */
   @Test
   public void shuffleBeforeGameStarts(){

      LinkedList<Card> savedPile = new LinkedList<Card>();
      int sameCardOnSamePlace = 0;

      emptyDeck();
      gameHandler.testSetupDeckAndPlayerHands(playerHandler);

      

      while(deckHandler.getPlaydeckSize() > 0 && savedPile.size() < 10){
         savedPile.add(deckHandler.drawCard());
      }

      emptyAllHands();
      emptyDeck();
      gameHandler.testSetupDeckAndPlayerHands(playerHandler);

      while(deckHandler.getPlaydeckSize() > 0 && savedPile.size() > 0){
         if(savedPile.removeFirst().getName().equals(deckHandler.drawCard().getName())){
            sameCardOnSamePlace++;
         }
      }

      emptyAllHands();
      emptyDeck();

      assertNotEquals(10, sameCardOnSamePlace);
      assertNotEquals(9, sameCardOnSamePlace);
      assertNotEquals(8, sameCardOnSamePlace);
      


   }

   /**
    * 8. Random player goes first 
    */
   @Test
   public void randomPlayerStarts(){

      int savedSize = 10;

      Player[] savedPlayers = new Player[savedSize];
      int sameStartingPlayerOccurance=0;

      for(int i = 0; i < savedSize; i++){
         playerHandler.startTurnOrder(true);
         savedPlayers[i] = playerHandler.getCurrentPlayer();
      }

      playerHandler.startTurnOrder(true);
      
      for(Player savedPlayer : savedPlayers){
         if(playerHandler.getCurrentPlayer() == savedPlayer){
            sameStartingPlayerOccurance++;
         }
      }      
      assertNotEquals(savedSize, sameStartingPlayerOccurance);

   }


}
