package cards;

import players.Player;
import players.PlayerHandler;
import decks.DeckHandler;

/**
 * this class contains all the actions 
 * a card might do. If a custom card needs new actions
 * to do this is were to add those functionalities.
 * @author langemittbacken
 *
 */
public class CardActions {

    PlayerHandler playerHandler = PlayerHandler.getInstance();
    DeckHandler deckHandler = DeckHandler.getInstance();
   
  //attack next player with current number of turns + nrOfTurns
public static void attack(int nrOfTurns) {
    PlayerHandler playerHandler = PlayerHandler.getInstance();
    int turnsLeft = playerHandler.getTurnsLeft();

    //card description says that if an attacked player plays an attack card on ANY of their turns,
    //they will send its remaining turns plus any remaining, meaning they attack with 3 turns if they are on their last turn,
    //even though they only got attacked with 2 turns. 
    if (playerHandler.OngoingAttack()) { 
        playerHandler.attackNextPlayer(nrOfTurns + turnsLeft);

    } else {
        playerHandler.attackNextPlayer(nrOfTurns); 
    }
  }

}