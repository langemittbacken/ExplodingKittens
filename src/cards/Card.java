package cards;

/**
 * Represents a play card that is implemented by its own class.
 * @author langemittbacken
 *
 */
public abstract class Card {
   private String cardName;
   private boolean isNopeable;
   private boolean isPlainCard;
   
   /**
    * what the card will do when played or activated in another way.
    */
   public abstract void performAction();
   
   public String getName() {
      return cardName;
   }
   
   public boolean isNopeable() {
      return isNopeable;
   }
   
   public boolean isPlainCard() {
      return isPlainCard;
   }

}
