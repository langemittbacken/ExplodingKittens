package cards.implCards;

import cards.Card;
import cards.CardActions;

public class Defuse extends Card{

    public Defuse() {
        super("Defuse", false, true, false);
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
