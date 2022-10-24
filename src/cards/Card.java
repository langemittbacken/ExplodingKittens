package cards;

/**
 * Represents a play card that is implemented by its own class.
 * connected with CardActions.java to apply its actions.
 * @author langemittbacken
 *
 */
public abstract class Card {
   protected String cardName;
   protected boolean nopeable;
   protected boolean comboCard; //can not be played by itself, this includes defuse, plainCards and nopes for example
   protected boolean defuseable;
   
   /**
    * what the card will do when played or activated in another way.
    */
   public abstract void onPlayingCard();

   /**
    * activated when a card is placed into a players hand
    */
   public abstract void onDrawingCard();

   public Card(String cardName, boolean Nopeable, boolean comboCard, boolean defuseable){
      this.cardName = cardName;
      this.nopeable = Nopeable;
      this.comboCard = comboCard;
      this.defuseable = defuseable;
   }

   public String getName() {
      return cardName;
   }
   
   public boolean isNopeable() {
      return nopeable;
   }
   
   public boolean isComboCard() {
      return comboCard;
   }

   public boolean isDefusable() {
      return defuseable;
   }

}
