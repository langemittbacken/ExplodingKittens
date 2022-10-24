package cards.implCards;

import cards.Card;
import cards.CardActions;

public class HairyPotatoCat extends Card{

    public HairyPotatoCat() {
        super("Hairy Potato Cat", true, true, false);
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
