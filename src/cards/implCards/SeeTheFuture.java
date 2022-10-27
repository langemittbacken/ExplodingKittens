package cards.implCards;

import cards.Card;
import cards.CardActions;

public class SeeTheFuture extends Card{

    private int cardsToSee = 3;
    public SeeTheFuture() {
        super("See The Future", true, false, false);
    }

    @Override
    public void onPlayingCard() {
        CardActions.SeeTheFuture(cardsToSee);
        
    }

    @Override
    public void onDrawingCard() {
        CardActions.doNothing();
        
    }
    
}
