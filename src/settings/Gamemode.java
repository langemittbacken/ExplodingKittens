package settings;

/**
 * by implementing a gamemode a player can get access to different 
 * expansions and new ways to play!
 * @author langemittbacken
 *
 */
public abstract class Gamemode {



   public abstract void setupCardsBeforeDealingHands(int nrOfPlayers);
   public abstract void setupCardsAfterDealingHands(int nrOfPlayers);
}
