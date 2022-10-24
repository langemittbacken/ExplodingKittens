package cards.implCards;

import cards.Card;
import cards.CardActions;

public class Shuffle extends Card{
    
    public Shuffle() {
        super("Shuffle", true, false, false);
    }

    @Override
    public void onPlayingCard() {
        CardActions.shuffleDeck();
        
    }

    @Override
    public void onDrawingCard() {
        CardActions.doNothing();
        
    }
    
}
