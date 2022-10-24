package decks;

import java.util.LinkedList;

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
        return card;
    }

    public void playCard(Card card) {
        toDiscardPile(card);
        card.onPlayingCard();
    }

    public void playMultipleCards(Card card, int multiplier) {
        erg
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

    public void toDiscardPile(LinkedList<Card> cards) {
        DiscardPile.addAllCards(cards);
    }

    public void shuffleDeck() {
        deck.shuffleDeck();
    }

    public int getPlaydeckSize() {
        return deck.deckSize();
    }

    public LinkedList<Card> peekDiscardPile(int startPos, int stopPos) {
        return DiscardPile.peekDeck(startPos, stopPos);
    }

    public LinkedList<Card> peekDeck(int startPos, int stopPos) {
        return DiscardPile.peekDeck(startPos, stopPos);
    }

    public void rearrangePlayDeck(LinkedList<Card> newOrderOnCards, int startPos, int stopPos) throws Exception {
        DiscardPile.rearrangeDeck(newOrderOnCards, startPos, stopPos);
    }

    public int getDiscardPileSize() {
        return DiscardPile.deckSize();
    }
}
