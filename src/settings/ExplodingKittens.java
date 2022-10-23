package settings;

import java.util.LinkedList;

import cards.implCards.*;
import players.Player;

/**
 * this is a gamemode representing the card game with the same name
 * without any expansions.
 * @author langemittbacken
 *
 */
public class ExplodingKittens extends Gamemode{
   public final int STARTING_CARDS_ON_HAND = 8;

      @Override
   public void setupCardsBeforeDealingHands(int nrOfPlayers) {
      

      int defuseCards = (nrOfPlayers<5) ? 2 : 1;
      int attackCards = 4;
      int favorCards = 4;
      int nopeCards = 5;

      int shuffleCards = 4;
      int skipCards = 4;
      int seeTheFutureCards = 5;
      int hairyPotatoCatCards = 4;

      int cattermelonCards = 4;
      int rainbowRalphingCatCards = 4;
      int tacoCatCards = 4;
      int overweightBikiniCatCards = 4;
      


      for(int i = 0; i<defuseCards; i++)              {deckHandler.addCardToDeck(new Defuse());} 
      for(int i = 0; i<attackCards; i++)              {deckHandler.addCardToDeck(new Attack());} 
      for(int i = 0; i<favorCards; i++)               {deckHandler.addCardToDeck(new Favor());} 
      for(int i = 0; i<nopeCards; i++)                {deckHandler.addCardToDeck(new Nope());} 

      for(int i = 0; i<shuffleCards; i++)             {deckHandler.addCardToDeck(new Shuffle());} 
      for(int i = 0; i<skipCards; i++)                {deckHandler.addCardToDeck(new Skip());}
      for(int i = 0; i<seeTheFutureCards; i++)        {deckHandler.addCardToDeck(new SeeTheFuture());} 
      for(int i = 0; i<hairyPotatoCatCards; i++)      {deckHandler.addCardToDeck(new HairyPotatoCat());} 

      for(int i = 0; i<cattermelonCards; i++)         {deckHandler.addCardToDeck(new Cattermelon());} 
      for(int i = 0; i<rainbowRalphingCatCards; i++)  {deckHandler.addCardToDeck(new RainbowRalphingCat());} 
      for(int i = 0; i<tacoCatCards; i++)             {deckHandler.addCardToDeck(new TacoCat());} 
      for(int i = 0; i<overweightBikiniCatCards; i++) {deckHandler.addCardToDeck(new OverweightBikiniCat());} 
   }

      @Override
   public void dealCards() {

      for(Player player : playerHandler.getActivePlayers()) {
         player.addCardToHand(new Defuse());
      }

      for(int i = 1; i < STARTING_CARDS_ON_HAND; i++){
         for(Player player : playerHandler.getActivePlayers()) {
            player.addCardToHand(deckHandler.drawCard());
         }
      }
      
   }

      @Override
   public void setupCardsAfterDealingHands(int nrOfPlayers) {

      for(int i = 1; i < nrOfPlayers; i++) {
         deckHandler.addCardToDeck(new ExplodingKitten());
      }
         
   }


}
