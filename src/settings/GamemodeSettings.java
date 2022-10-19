package settings;

/**
 * handles which gamemodes, or expansions, that the player can choose.
 * @author langemittbacken
 *
 */
public class GamemodeSettings {
   private boolean basegame = true;
   
   private boolean includeImplodingKittens = false;
   private boolean includeStreakingKittens = false;
   private boolean includeBarkingKittens = false;
   
   public Gamemode setupGameDeck(int nrOfPlayers) {
      Gamemode gamemode;
      if(basegame) {
         gamemode = new ExplodingKittens();
         gamemode.deckSetup(nrOfPlayers);

      } else {
         gamemode = new MyCustomGamemode();//placeholder, change this if you implement your own base rules
         gamemode.deckSetup(nrOfPlayers);
      }
     
      /*
      if (includeImplodingKittens) {//adjust the setup by including the expansion

      }
      if (includeStreakingKittens) {//adjust the setup by including the expansion

      }
      if (includeBarkingKittens) {//adjust the setup by including the expansion
         
      }*/
      
      return gamemode;
   }
}
