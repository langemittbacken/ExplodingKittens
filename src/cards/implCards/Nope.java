package cards.implCards;

import cards.Card;
import cards.CardActions;

public class Nope extends Card {

    public Nope() {
        super("Nope", true, true, false);
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
