package cards.implCards;

import cards.Card;
import cards.CardActions;

public class OverweightBikiniCat extends Card {

    public OverweightBikiniCat() {
        super("Overweight Bikini Cat", true, true, false);
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
