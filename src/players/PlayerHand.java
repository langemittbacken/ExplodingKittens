package players;

import java.util.LinkedList;

import cards.Card;
import exceptions.CardNotFoundException;

public class PlayerHand {
   LinkedList<Card> hand;
   
   public PlayerHand() {
      this.hand = new LinkedList<Card>(); 
   }
   
   public Card getCard(Card card) throws CardNotFoundException {
      
      if(hand.contains(card)) {
         return hand.get(hand.indexOf(card));
         
      } else {
         throw new CardNotFoundException("Unable to find the card "+ card.cardName);
      }
      
   }
   
   public void addCard(Card card) {
      
   }
   
   public void deleteCard() {
      
   }

}
