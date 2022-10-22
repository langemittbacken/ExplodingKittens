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
    private LinkedList<Card> theDeck;

    public Deck() {
        theDeck = new LinkedList<Card>();
    }

    public void addCard(Card card) {
        theDeck.add(card);
    }

    public void addCard(int pos, Card card) {
        theDeck.add(pos, card);
    }

    public void addAllCards(LinkedList<Card> cards) {
        theDeck.addAll(cards);
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

    /**
     * return, but does not remove, and in order, all cards from including startPos
     * upto, but excluding, stopPos in the deck
     * @param startPos inclusive
     * @param stopPos exclusive, must be less than size of deck
     * @return (stopPos-startPos) nr of cards in a LinkedList (in order)
     */
    public LinkedList<Card> peekDeck(int startPos, int stopPos) {
        LinkedList<Card> peekCards = new LinkedList<Card>();
        for(int i = startPos; i < stopPos; i++){
            peekCards.add(theDeck.get(i));
        }
        return peekCards;
    }

    /**
     * will replace cards from startPos (inclusive) to stopPos (exlusive)
     * with newOrderOnCards (length of newOrderOnCards must be stopPos-startPos)
     * @param newOrderOnCards 
     * @param startPos inclusive
     * @param stopPos exclusive, must be less than size of deck
     * @throws Exception
     */
    public void rearrangeDeck(LinkedList<Card> newOrderOnCards, int startPos, int stopPos) throws Exception {
        
        if(newOrderOnCards.size() != stopPos-startPos) {
            throw new Exception("stopPos-startPos(" + stopPos + "-" + startPos +
             ") does not match length of newOrderOnCards");
        }

        for(int i = startPos; i < stopPos; i++){
            theDeck.set(i, newOrderOnCards.removeFirst());
        }
    }

    public boolean containsCard(Card card) {
        return theDeck.contains(card);
    }

    public int deckSize() {
        return theDeck.size();
    }
}
