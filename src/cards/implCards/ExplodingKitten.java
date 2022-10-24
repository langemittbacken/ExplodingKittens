package cards.implCards;

import cards.Card;
import cards.CardActions;

public class ExplodingKitten extends Card{

    public ExplodingKitten() {
        super("Exploding Kitten", false, true, true);
    }

    @Override
    public void onPlayingCard() {
        CardActions.doNothing();
    }

    @Override
    public void onDrawingCard() {
        CardActions.explodeCurrentPlayer(defuseable, this);
    }
    
}
