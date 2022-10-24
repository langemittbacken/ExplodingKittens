package cards.implCards;

import cards.Card;
import cards.CardActions;

public class TacoCat extends Card {

    public TacoCat() {
        super("Taco Cat", true, true, false);
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
