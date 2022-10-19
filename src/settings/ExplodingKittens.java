package settings;

import cards.Card;
import cards.implCards.*;
import decks.DeckHandler;
import players.PlayerHandler;

/**
 * this is a gamemode representing the card game with the same name
 * without any expansions.
 * @author langemittbacken
 *
 */
public class ExplodingKittens extends Gamemode{

   DeckHandler deckHandler = DeckHandler.getInstance();

   public void setupCardsBeforeDealingHands(int nrOfPlayers) {
      int defuseCards = (nrOfPlayers<5) ? 2 : 1;
      
      Card[] cards = {new Defuse(),

                      new TacoCat()};

      int[] multiplier = {defuseCards,
                          4};

      deckHandler.initializeDeck(cards, multiplier);
   }

   @Override
   public void setupCardsAfterDealingHands(int nrOfPlayers) {
      // TODO Auto-generated method stub
      
   }
}
