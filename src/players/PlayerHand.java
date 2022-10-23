package players;

import java.util.Collections;
import java.util.LinkedList;

import cards.Card;
import exceptions.CardNotFoundException;

/**
 * Contains the cards a player is holding in their hand.
 * @author langemittbacken
 *
 */
public class PlayerHand {
   private LinkedList<Card> hand;
   protected boolean blind;
   
   public PlayerHand() {
      this.hand = new LinkedList<Card>(); 
      blind = false;
   }
   
   public Card takeCard(Card card) throws CardNotFoundException {
      
      if(hand.contains(card)) {
         return hand.remove(hand.indexOf(card));
         
      } else {
         throw new CardNotFoundException("Unable to find the card "+ card.getName());
      } 
   }

   public boolean hasCard(String cardName) {
      for(Card card : hand){
         if (cardName.equalsIgnoreCase(card.getName())){
            return true;
         }
      }
      return false;
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

   public Card takeCard(int index) throws IndexOutOfBoundsException {
      if(hand.size() <= index) {
         throw new IndexOutOfBoundsException();
      }
      return hand.remove(index);
   }

   public Card takeCard(String cardNameOrIndex) throws CardNotFoundException {
      
      for (Card card : hand) {
         if (card.getName().equalsIgnoreCase(cardNameOrIndex)){
            hand.remove(card);
            return card;
         }
      }
      throw new CardNotFoundException(cardNameOrIndex);
   }

   public LinkedList<Card> emptyHand() {
      LinkedList<Card> temporary = hand;
      hand = new LinkedList<Card>();
      return temporary;
      }

   
}
