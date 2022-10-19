package settings;

/**
 * handles which gamemodes, or expansions, that the player can choose.
 * @author langemittbacken
 *
 */
public class GamemodeSettings {
   private static boolean basegame = true;
   private static int minPlayers = 2;
   private static int maxAllowedPlayers = 5;
   


   private static boolean includeImplodingKittens = false;
   private static boolean includeStreakingKittens = false;
   private static boolean includeBarkingKittens = false;
   
   public static void setupGameDeck(int nrOfPlayers) {

      if(basegame) {
         new ExplodingKittens().setupCardsBeforeDealingHands(nrOfPlayers);

      } else { 
         new MyCustomGamemode().setupCardsBeforeDealingHands(nrOfPlayers);//placeholder, change this if you implement your own base rules
      }
     
      /*
      if (includeImplodingKittens) {//adjust the setup by including the expansion

      }
      if (includeStreakingKittens) {//adjust the setup by including the expansion

      }
      if (includeBarkingKittens) {//adjust the setup by including the expansion
         
      }*/
   }

   public static void finalizeSetup(int nrOfPlayers) {

      if(basegame) {
         new ExplodingKittens().setupCardsAfterDealingHands(nrOfPlayers);

      } else { 
         new MyCustomGamemode().setupCardsAfterDealingHands(nrOfPlayers);//placeholder, change this if you implement your own base rules
      }
     
      /*
      if (includeImplodingKittens) {//adjust the setup by including the expansion

      }
      if (includeStreakingKittens) {//adjust the setup by including the expansion

      }
      if (includeBarkingKittens) {//adjust the setup by including the expansion
         
      }*/
   }  

   public static int getMaxAllowedPlayers() {
      return maxAllowedPlayers;
   }

   public static int getMinPlayers() {
      return minPlayers;
   }
}
