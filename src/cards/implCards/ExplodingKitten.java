package cards.implCards;

import cards.Card;
import cards.CardActions;

public class ExplodingKitten extends Card{

    public ExplodingKitten() {
        super("Exploding Kitten", false, false, true);
    }

    @Override
    public void onPlayingCard() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onDrawingCard() {
        CardActions.explodeCurrentPlayer(defuseable, this);
    }
    
}
