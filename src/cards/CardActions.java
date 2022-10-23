package cards;

import players.Player;
import players.PlayerHandler;
import server.Server;
import decks.DeckHandler;
import exceptions.CardNotFoundException;

/**
 * this class contains all the actions 
 * a card might do. If a custom card needs new actions
 * to do this is were to add those functionalities.
 * @author langemittbacken
 *
 */
public class CardActions {

    private static PlayerHandler playerHandler = PlayerHandler.getInstance();
    private static DeckHandler deckHandler = DeckHandler.getInstance();
    private static Server server = Server.getInstance();



    /**
     * attack next player with current number of turns + nrOfTurns
     */
    public static void attack(int nrOfTurns) {
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

    public static void explodeCurrentPlayer(boolean defuseable, Card kitten) {
        Player player = PlayerHandler.getInstance().getCurrentPlayer();
        server.sendMsgToAllPlayers("A Kitten was activated! by Player " + player.getPlayerID());

        if(!defuseable || !player.hasDefuse()) {
          deckHandler.toDiscardPile(playerHandler.explodePlayer(player));
          server.sendMsgToAllPlayers("The Kitten has exploded Player " + player.getPlayerID());
          return;
        }

        try {
            deckHandler.toDiscardPile(player.takeDefuse()); } catch (CardNotFoundException e1) {e1.printStackTrace();
        } 

        int deckSize = deckHandler.getPlaydeckSize();

        server.sendMessage(player, "The Kitten has been defused");

        String readMsg = "";
        int pos = -1;
        while (pos<0 || pos>deckSize) {
            server.sendMessage(player, "*options* playdeck has " + deckSize + " cards, choose where to send the Kitten (0-" + (deckSize) + ")\n" 
            +"0 is before first card and " + deckSize + " is after last card\n");

            readMsg = server.readMessage(player, false);
            try {
                pos = Integer.parseInt(readMsg);

            } catch (Exception e) {
                server.sendMessage(player, "invalid input");
            }
        }
        
        try {
            deckHandler.insertCardInDeck(pos, player.takeCardFromHand(kitten)); } catch (CardNotFoundException e) {e.printStackTrace();
        }
    }

}