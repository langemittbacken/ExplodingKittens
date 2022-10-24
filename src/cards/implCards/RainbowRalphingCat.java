package cards.implCards;

import cards.Card;
import cards.CardActions;

public class RainbowRalphingCat extends Card {

    public RainbowRalphingCat() {
        super("Rainbow Ralphing Cat", true, true, false);
    }

    @Override
    public void onPlayingCard() {
        CardActions.doNothing();
        
    }

    @Override
    public void onDrawingCard() {
        CardActions.doNothing();
        
    }
    
}
