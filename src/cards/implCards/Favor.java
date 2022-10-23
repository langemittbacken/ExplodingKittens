package cards.implCards;

import cards.Card;
import cards.CardActions;

public class Favor extends Card{

    public Favor() {
        super("Favor", true, false, false);
    }

    @Override
    public void onPlayingCard() {
        CardActions.askForFavor();
    }

    @Override
    public void onDrawingCard() { 
    }
    
}
