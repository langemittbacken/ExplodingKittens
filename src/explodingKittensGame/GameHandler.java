package explodingKittensGame;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cards.Card;
import cards.CardActions;
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

   


    public GameHandler(int nrOfPlayers, int nrOfBots) throws Exception{

      Player currentPlayer = null;

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
         gameLoop(currentPlayer);
         if(playerHandler.getActivePlayers().size() == 1){
            break;
         }
      }
      server.sendMsgToAllPlayers("Player " + playerHandler.getAllPlayers().getFirst().getPlayerID() + " Won!\n");
    }

   private void gameLoop(Player currentPlayer) {
      String action;
      

      if(currentPlayer != playerHandler.getCurrentPlayer()) {
         currentPlayer = playerHandler.getCurrentPlayer();
         sendWhoseTurnItIs(currentPlayer);
      }
      
      action = getActionFromCurrentPlayer(currentPlayer).trim();
      System.out.println(action + " sent by Player" + currentPlayer.getPlayerID());
      
      //playing 2 cards
      if(action.length() > 2 && action.substring(0, 2).equalsIgnoreCase("2x")){
         try {
            playTwoCardCombo(currentPlayer, action.substring(2).trim());
         } catch (CardNotFoundException e) {
            server.sendMessage(currentPlayer, e.getMessage());
            return;
         }
         return;
      }
      //playing 3 cards
      if(action.length() > 2 && action.substring(0, 2).equalsIgnoreCase("3x")){
         try {
            playThreeCardCombo(currentPlayer, action.substring(2).trim());
         } catch (CardNotFoundException e) {
            server.sendMessage(currentPlayer, e.getMessage());
            return;
         }
         return;
      }

      if(!action.equalsIgnoreCase("pass")){
         playCard(currentPlayer, action);
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
      server.sendMsgToAllPlayers("How to play: \n"
                                 +" type \"pass\" draw a card\n"
                                 +" type \"cardname\" to play card\n"
                                 +" type number of the card to play card\n"
                                 +" type\"2xcardname\" to play a 2 card combo\n"
                                 +" type \"3xcardname\" to play a 3 card combo\n"
                                 +" \"2x\" and \"3x\" also works with number of the card \n");
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

      return server.readMessage(currentPlayer, true);

   }
   
   private void playTwoCardCombo(Player currentPlayer, String cardNameOrIndex) throws CardNotFoundException {
      int nrOfCards = 2;

      playCardCombo(nrOfCards, currentPlayer, cardNameOrIndex);

      try {

         if(askPlayersforNope()){
            return;
         }
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      CardActions.stealCardFromPlayer();
   }

   

   private void playThreeCardCombo(Player currentPlayer, String cardNameOrIndex) throws CardNotFoundException {
      int nrOfCards = 3;

      playCardCombo(nrOfCards, currentPlayer, cardNameOrIndex);
      
      try {
         if(askPlayersforNope()){
            return;
         }
      } catch (InterruptedException e) {
         e.printStackTrace();
      }

      CardActions.askForSpecifiedCardFromPlayer();

   }

   private void playCardCombo(int nrOfCards, Player currentPlayer, String cardNameOrIndex) throws CardNotFoundException {
      
      String cardName = cardNameOrIndex;

      try {
         cardName = currentPlayer.getCardName(cardNameOrIndex); //if an index
      } catch (Exception e) {
      }

      if (!currentPlayer.cardMeetsMinimumOccurance(nrOfCards, cardName)){
         throw new CardNotFoundException("your input \"" + cardName +"\" was only found " + currentPlayer.countCardsOf(cardName) + " time(s)");
      }

      LinkedList<Card> combo = currentPlayer.takeCardsFromHand(nrOfCards, cardName);
      deckHandler.toDiscardPile(combo);
      
      server.sendMsgToAllPlayers("Player " + currentPlayer.getPlayerID() + " played a " + nrOfCards + " cards combo of [" + cardName + "]");
   }

   private void playCard(Player currentPlayer, String action) {
      Card card;
      try {
         card = currentPlayer.takeCardFromHand(action);
         if(card.isComboCard()){
            server.sendMessage(currentPlayer, "[" + card.getName() + "] cannot be played by itself");
            currentPlayer.addCardToHand(card);
            return;
         }

         server.sendMsgToAllPlayers("Player " + currentPlayer.getPlayerID() + " played [" + card.getName() + "]");
         
      } catch (CardNotFoundException e) {
         server.sendMessage(currentPlayer, "could not handle your action \"" + action + "\"");
         return;
      }
      
      try {
         if(card.isNopeable() && askPlayersforNope()){
            deckHandler.toDiscardPile(card);
         } else {
            deckHandler.playCard(card);
         }
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   /**
    * After an interruptable card is played everyone has playerHandler.getSecondsToInterruptWithNope() seconds to play Nope
    * @return true if nr of nope played is odd, false if even (or none)
    * @throws InterruptedException
    */
   private boolean askPlayersforNope() throws InterruptedException {

		int nopePlayed = checkNrNope(); 
		ExecutorService threadpool = Executors.newFixedThreadPool(playerHandler.getActivePlayers().size());

		for(Player p : playerHandler.getActivePlayers()) {
			
			if(p.hasNope()) { //only give the option to interrupt to those who have a Nope card
				server.sendMessage(p, "Press <Enter> to play [Nope]");

				Runnable task = new Runnable() {
		        	@Override
		        	public void run() {

	        			try {
			        		String nextMessage = server.readMessage(p, true); //Read that is interrupted after secondsToInterruptWithNope

			        		if(!nextMessage.equals(" ") && p.hasNope()) {
		    	    			
		    	    			deckHandler.toDiscardPile(p.takeNope());

                        server.sendMsgToAllPlayers("Player " + p.getPlayerID() + " played Nope");
			        		}
	        			} catch(Exception e) {
	        				System.out.println("addToDiscardPile: " +e.getMessage());
	        			}
	        		}
	        	};
            threadpool.execute(task);
			}
		}
      threadpool.awaitTermination( (PlayerHandler.getSecondsToInterruptWithNope() * 1000) + 500, TimeUnit.MILLISECONDS);//add an additional delay to avoid concurrancy problems with the ObjectInputStream
		
      server.sendMsgToAllPlayers("The timewindow to play Nope passed");

		if(checkNrNope()>nopePlayed) {
         
         server.sendMsgToAllPlayers("will someone play another nope?");

			return !askPlayersforNope();
		}
      return false;
   }

   private int checkNrNope() {

      int nopesInARow = 0;
      for(int i = 0; i< deckHandler.getDiscardPileSize(); i++) {
         if(deckHandler.peekDiscardPile(i, i+1).getFirst().getName().equals("Nope")) {
            nopesInARow++;

         } else {

            return nopesInARow;
         }
      }

      return nopesInARow;
   }
}
