package settings;

/**
 * handles which gamemodes, or expansions, that the player can choose.
 * @author langemittbacken
 *
 */
public class Settings {
   private boolean basegame = true;
   
   private boolean includeImplodingKittens = false;
   private boolean includeStreakingKittens = false;
   private boolean includeBarkingKittens = false;
   
   public Gamemode setupExplodingKittens() {
      Gamemode gamemode;
      if(basegame) {
         gamemode = new ExplodingKittens();
      } else {
         gamemode = new MyCustomGamemode();//placeholder, change this if you implement your own base rules
      }
     
      /*
      if (includeImplodingKittens) {//adjust the setup by including the expansion
         mergeExpansion(gamemode, new ImplodingKittens())
      }
      if (includeStreakingKittens) {//adjust the setup by including the expansion
         mergeExpansion(gamemode, new StreakingKittens())
      }
      if (includeBarkingKittens) {//adjust the setup by including the expansion
         mergeExpansion(gamemode, new BarkingKittens());
      }*/
      
      return gamemode;
   }
   
   private Gamemode mergeExpansion(Gamemode basegame, Gamemode expansion) {
      return null;
   }

}
