package decks;

import cards.Card;

/**
 * handles distribution of the deck and discard pile.
 * Singleton design pattern.
 * @author langemittbacken
 *
 */
public class DeckHandler {

    private Deck deck;
    private Deck DiscardPile;
    private static DeckHandler instance = new DeckHandler();

    private DeckHandler() {
        deck = new Deck();
        DiscardPile = new Deck();
    }

    public static DeckHandler getInstance() {
        return instance;
    } 

    public Card drawCard() {
        return deck.drawCard();
    }
    
    /**
     * 
     * @param cardName must match the name of a card
     * @param nrOfCards 
     */
    public void initializeDeck(Card[] cards, int[] nrOfCards) {
        
    }

    public void insertCardInDeck(int pos, Card card) {

    }

    public void toDiscardPile(Card card) {
        DiscardPile.addCard(card);
    }

    public void shuffleDeck() {
        deck.shuffleDeck();
    }
}
