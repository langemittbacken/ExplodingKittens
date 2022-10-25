package settings;

import decks.DeckHandler;
import players.PlayerHandler;

/**
 * by implementing a gamemode a player can get access to different 
 * expansions and new ways to play!
 * @author langemittbacken
 *
 */
public abstract class Gamemode {

   protected static DeckHandler deckHandler = DeckHandler.getInstance();
   protected static PlayerHandler playerHandler = PlayerHandler.getInstance();

   public abstract void setupCardsBeforeDealingHands(int nrOfPlayers);
   public abstract void dealCards();
   public abstract void setupCardsAfterDealingHands(int nrOfPlayers);
}
