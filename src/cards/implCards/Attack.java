package cards.implCards;

import cards.Card;
import cards.CardActions;

public class Attack extends Card{

    public Attack() {
        super("Attack", true, false, false);
    }

    @Override
    /**
     * the description for this card is really not trivial to implement.
     * if someone plays it, they will attack the next player with 2 turns, 
     * and they are done with the turn, but the next player can 
     * choose to play an attack on any of its turn, and then send
     * 3 or 4 extra turns to the next player, 
     * depending if it attack on the first or second turn.
     */
    public void onPlayingCard() {
        CardActions.attack(2);
        
    }

    @Override
    public void onDrawingCard() {   
        CardActions.doNothing();  
    }
    
}
