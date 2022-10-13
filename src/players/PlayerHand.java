package players;

import java.util.LinkedList;

import javax.smartcardio.CardNotPresentException;

import cards.Card;

public class PlayerHand {
   LinkedList<Card> hand;
   
   public PlayerHand() {
      this.hand = new LinkedList<Card>(); 
   }
   
   public Card getCard(Card card) throws CardNotPresentException {
      
      if(hand.contains(card)) {
         return hand.get(hand.indexOf(card));
         
      } else {
         throw new CardNotPresentException("");
      }
      
   }
   
   public void addCard(Card card) {
      
   }
   
   public void deleteCard() {
      
   }

}
