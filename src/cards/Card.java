package cards;

/**
 * Represents a play card that is implemented by its own class.
 * connected with CardActions.java to apply its actions.
 * @author langemittbacken
 *
 */
public abstract class Card {
   protected String cardName;
   protected boolean Nopeable;
   protected boolean PlainCard;
   protected boolean defuseable;
   
   /**
    * what the card will do when played or activated in another way.
    */
   public abstract void onPlayingCard();

   public abstract void onDrawingCard();

   public Card(String cardName, boolean Nopeable, boolean PlainCard, boolean defuseable){
      this.cardName = cardName;
      this.Nopeable = Nopeable;
      this.PlainCard = PlainCard;
      this.defuseable = defuseable;
   }

   public String getName() {
      return cardName;
   }
   
   public boolean isNopeable() {
      return Nopeable;
   }
   
   public boolean isPlainCard() {
      return PlainCard;
   }

   public boolean isDefusable() {
      return defuseable;
   }

}
