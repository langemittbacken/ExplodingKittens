package explodingKittensGame;

import server.Client;

/**
 * Based on the card game Exploding Kittens
 * https://www.explodingkittens.com/
 * @author langemittbacken
 *
 */
public class ExplodingKittensGame {

   public static void main(String[] args) {
      System.out.println("Welcome to Exploding Kittens!");
      System.out.println();
      
      if (args.length == 1) {//client
         try {
            new Client(args[1]);
         } catch (Exception e) {
            
            System.out.println("could not connect to IP: " + args[1]);
            e.printStackTrace();
         }
      } else {
         new GameHandler();//server
      }
     
      System.out.println("Bye Bye!");
   }

}
