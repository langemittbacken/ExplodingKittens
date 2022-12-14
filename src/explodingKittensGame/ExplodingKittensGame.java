package explodingKittensGame;

import java.util.LinkedList;

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
            new Client(args[0]);
         } catch (Exception e) {
            
            System.out.println("could not connect to IP: " + args[0]);
            e.printStackTrace();
         }
      } else {
         new MainMenu();//server
      }
   }

}
