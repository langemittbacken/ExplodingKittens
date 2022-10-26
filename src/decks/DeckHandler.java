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
    private Deck discardPile;
    private static DeckHandler instance = new DeckHandler();

    private DeckHandler() {
        deck = new Deck();
        discardPile = new Deck();
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

    public void addCardToDeck(Card card) {
        deck.addCard(card);
    }

    public void insertCardInDeck(int pos, Card card) {
        deck.addCard(pos, card);
    }

    public void toDiscardPile(Card card) {
        discardPile.addCard(card);
    }

    public void toDiscardPile(LinkedList<Card> cards) {
        discardPile.addAllCards(cards);
    }

    public Card takeFromDiscardPile(){
        return discardPile.drawCard();
    }

    public void shuffleDeck() {
        deck.shuffleDeck();
    }

    public int getPlaydeckSize() {
        return deck.deckSize();
    }

    public LinkedList<Card> peekDiscardPile(int startPos, int stopPos) {
        return discardPile.peekDeck(startPos, stopPos);
    }

    public LinkedList<Card> peekDeck(int startPos, int stopPos) {
        return deck.peekDeck(startPos, stopPos);
    }

    public void rearrangePlayDeck(LinkedList<Card> newOrderOnCards, int startPos, int stopPos) throws Exception {
        discardPile.rearrangeDeck(newOrderOnCards, startPos, stopPos);
    }

    public int getDiscardPileSize() {
        return discardPile.deckSize();
    }

    public void emptyDiscardPile(){
        discardPile.emptyDeck();
    }
}
