package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

import cards.Card;
import exceptions.CardNotFoundException;

/**
 * represent a player playing the game.
 * @author langemittbacken
 *
 */
public class Player{
   private PlayerHand hand;
   private int playerID;
   private Socket connection;
   private ObjectInputStream inFromClient;
   private ObjectOutputStream outToClient;
   private boolean isBot;
   private boolean online;

   public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
      this.playerID = playerID; this.connection = connection; this.inFromClient = inFromClient; this.outToClient = outToClient; this.isBot = isBot;
      if(connection == null) {
         this.online = false;
      } else {
         this.online = true;
      }
      this.hand = new PlayerHand();
      
   }

   public void addCardToHand(Card card) {
      hand.addCard(card);
      card.onDrawingCard();
   }

   public Card takeCardFromHand(String cardNameOrIndex) throws CardNotFoundException {

      try {
         return hand.takeCard(Integer.parseInt(cardNameOrIndex));
      } catch (Exception e) {
         return hand.takeCard(cardNameOrIndex);
      }
      
   }

   public Card takeCardFromHand(Card card) throws CardNotFoundException {
      return hand.takeCard(card);
   }
   
   /**
    * take multiple of a single card
    * @param nrOfCards
    * @param cardNameOrIndex
    * @return
    * @throws CardNotFoundException
    */
   public LinkedList<Card> takeCardsFromHand(int nrOfCards, String cardName) throws CardNotFoundException {

      if(!cardMeetsMinimumOccurance(nrOfCards, cardName)){
         throw new CardNotFoundException(cardName);
      }

      LinkedList<Card> returnList = new LinkedList<Card>();

      for(int i = 0; i < nrOfCards; i++){
         returnList.add(takeCardFromHand(cardName));
      }
      return returnList;
   }

   public Card takeRandomCardFromHand() throws CardNotFoundException {

      if(hand.nrOfCards() > 0){
         return hand.takeCard((int)Math.random() * hand.nrOfCards());
      }
      throw new CardNotFoundException(" could not take random card from opponent");
      
  }
   
   public boolean cardMeetsMinimumOccurance(int minimum, String cardNameOrIndex) {

      if(countCardsOf(cardNameOrIndex) < minimum){
         return false;
      }
      return true;
   }

   public String printHand() {
      return hand.handToString();
   }

   public void setBlind(boolean value) {
      hand.setBlind(value);
   }

   public int getPlayerID(){
      return playerID;
   }

   public Socket getConnection(){
      return connection;
   }
   
   public ObjectInputStream getInFromClient(){
      return inFromClient;
   }

   public ObjectOutputStream getOutToClient(){
      return outToClient;
   }

   public boolean isBot(){
      return isBot;
   }

   public boolean isOnline(){
      return online;
   }

   public boolean hasDefuse() {
      return hand.hasCard("Defuse");
   }

   public boolean hasCard(String cardName) {
      return hand.hasCard(cardName);
   }

   public Card takeDefuse() throws CardNotFoundException {
         return hand.takeCard("Defuse");
   }

   public LinkedList<Card> emptyHand() {
      return hand.emptyHand();
   }

   public int nrOfCardsInHand() {
      return hand.nrOfCards();
   }

   public boolean hasNope() {
      return hand.hasCard("Nope");
   }

   public Card takeNope() throws CardNotFoundException {
      return hand.takeCard("Nope");
   }

   public int countCardsOf(String cardNameOrIndex) {

      String nameOfCard;
      try {
         nameOfCard = hand.getCard(Integer.parseInt(cardNameOrIndex)).getName();

         return hand.countCardsOf(nameOfCard);

      } catch (Exception e) {
         return hand.countCardsOf(cardNameOrIndex);
      }
      
   }

   public String getCardName(String index) {
      return hand.getCard(Integer.parseInt(index)).getName();

   }

}
