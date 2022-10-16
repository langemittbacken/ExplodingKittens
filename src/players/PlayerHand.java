package players;

import java.util.ArrayList;

import cards.Card;
import exceptions.CardNotFoundException;

/**
 * Contains the cards a player is holding in their hand.
 * @author langemittbacken
 *
 */
public class PlayerHand {
   private ArrayList<Card> hand;
   
   public PlayerHand() {
      this.hand = new ArrayList<Card>(); 
   }
   
   public Card getCard(Card card) throws CardNotFoundException {
      
      if(hand.contains(card)) {
         return hand.get(hand.indexOf(card));
         
      } else {
         throw new CardNotFoundException("Unable to find the card "+ card.getName());
      }
      
   }
   
   public void addCard(Card card) {
      hand.add(card);
   }
   
   public void deleteCard(Card card) {
      hand.remove(card);
   }

}
