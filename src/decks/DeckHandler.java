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
        Card card = deck.drawCard();
        card.onDrawingCard();
        return card;
    }

    public void playCard(Card card) {
        card.onPlayingCard();
        toDiscardPile(card);
    }

    public void addCardToDeck(Card card) {
        deck.addCard(card);
    }

    public void insertCardInDeck(int pos, Card card) {
        deck.addCard(pos, card);
    }

    public void toDiscardPile(Card card) {
        DiscardPile.addCard(card);
    }

    public void shuffleDeck() {
        deck.shuffleDeck();
    }
}
