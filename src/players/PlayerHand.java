package players;

import java.util.ArrayList;
import java.util.Collections;

import cards.Card;
import exceptions.CardNotFoundException;

/**
 * Contains the cards a player is holding in their hand.
 * @author langemittbacken
 *
 */
public class PlayerHand {
   private ArrayList<Card> hand;
   protected boolean blind;
   
   public PlayerHand() {
      this.hand = new ArrayList<Card>(); 
      blind = false;
   }
   
   public Card getCard(Card card) throws CardNotFoundException {
      
      if(hand.contains(card)) {
         return hand.get(hand.indexOf(card));
         
      } else {
         throw new CardNotFoundException("Unable to find the card "+ card.getName());
      } 
   }
   
   public String handToString() {
      String strHand = "";

      if(blind){
         strHand ="(*Blind*) ";
         for(int i = 0; i < hand.size(); i++){
            strHand = strHand + "[*" + i + "*]\n";
         }
         return strHand ;
      }

      int i = 0;
      for(Card card : hand) {
         strHand = strHand + ("[" + i + "] [" + card.getName() + "]\n");
         i++;
      }
      return strHand;
   }

   public void shuffleHand() {
      Collections.shuffle(hand);
   }
   
   public void addCard(Card card) {
      hand.add(card);
   }
   
   public void deleteCard(Card card) {
      hand.remove(card);
   }
   public void setBlind(boolean value) {
      blind = value;
   }

}
