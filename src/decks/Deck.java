package decks;

import java.util.Collections;
import java.util.LinkedList;

import cards.Card;

/**
 * Represents a deck with playing cards.
 * @author langemittbacken
 *
 */
public class Deck {
    LinkedList<Card> theDeck;

    public Deck() {
        theDeck = new LinkedList<Card>();
    }

    public void addCard(Card card) {
        theDeck.add(card);
    }

    public Card drawCard() {
        return theDeck.removeFirst();
    }

    public Card drawCardFromBottom() {
        return theDeck.removeLast();
    }

    public void shuffleDeck() {
        Collections.shuffle(theDeck);
    }

    public LinkedList<Card> peekDeck(int startPos, int stopPos) {

    }

    public void rearrangeDeck(LinkedList<Card> newOrderOnCards, int startPos, int stopPos) {

    }

    public boolean containsCard(Card card) {
        return theDeck.contains(card);
    }

    public int deckSize() {
        return theDeck.size();
    }
}
