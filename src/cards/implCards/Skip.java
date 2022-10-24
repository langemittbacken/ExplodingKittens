package cards.implCards;

import cards.Card;
import cards.CardActions;

public class Skip extends Card {

    public Skip() {
        super("Skip", true, false, false);
    }

    @Override
    public void onPlayingCard() {
        CardActions.skipTurn();
    }

    @Override
    public void onDrawingCard() {
        // TODO Auto-generated method stub
        
    }
    
    
}
