package cards;

import players.Player;
import players.PlayerHandler;
import server.Server;
import decks.DeckHandler;
import exceptions.*;

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

    public static void askForFavor() {
        Player currentPlayer = playerHandler.getCurrentPlayer();
        Player attackedPlayer = currentPlayer;

        printAllOthersPlayersHandSize(currentPlayer);
        
        while(attackedPlayer == currentPlayer){
            server.sendMessage(currentPlayer, "*options* which player do you want a favor from?");
            String answer = server.readMessage(currentPlayer, true);

            try {
                attackedPlayer = playerHandler.getActivePlayerFromString(answer); } catch (PlayerNotFoundException e) {e.printStackTrace();
            }
            if(attackedPlayer == currentPlayer){
                server.sendMessage(currentPlayer, "cannot get a Favor from yourself");
                continue;
            }
        }
        
        Card cardFromAttackedPlayer = cardFromAttackedPlayer(attackedPlayer, currentPlayer);

        currentPlayer.addCardToHand(cardFromAttackedPlayer);
        server.sendMessage(currentPlayer, "recieved [" + cardFromAttackedPlayer.getName() + "] from Player " + attackedPlayer.getPlayerID());
    }

    private static void printAllOthersPlayersHandSize(Player currentPlayer) {
        for(Player player : playerHandler.getActivePlayers()){
            if(player != currentPlayer){
                server.sendMessage(playerHandler.getCurrentPlayer(), "Player " + player.getPlayerID() + " has " + player.nrOfCardsInHand() + " cards");
            } 
        }
    }

    private static Card cardFromAttackedPlayer(Player attackedPlayer, Player currentPlayer) {
        while(true){
            server.sendMessage(attackedPlayer, attackedPlayer.printHand());
            server.sendMessage(attackedPlayer, "Player " + currentPlayer.getPlayerID() + " asked you for a favor");
            server.sendMessage(attackedPlayer, "*options* pick card to give to Player " + currentPlayer.getPlayerID());
            String favorCard = server.readMessage(attackedPlayer, true);
        
            try {
                Card favor = attackedPlayer.takeCardFromHand(favorCard);
                server.sendMessage(attackedPlayer, "sent [" + favor.getName() + "] to Player " + currentPlayer.getPlayerID());
                return favor;

            } catch (Exception e) {
                server.sendMessage(attackedPlayer, "invalid input");
                continue;
            }   
        }
    }

    public static void doNothing(){
    }

    public static void SeeTheFuture(int cardsToPeek) {
        Player currentPlayer = playerHandler.getCurrentPlayer();
        server.sendMessage(currentPlayer, "_______________________________________________");
        server.sendMessage(currentPlayer, "the top " + cardsToPeek + " card(s) out of " + deckHandler.getPlaydeckSize() + " cards in playdeck");

        int pos = 0;
        for(Card c : deckHandler.peekDeck(0, cardsToPeek)){
            server.sendMessage(currentPlayer, "[" + pos +"] [" + c.getName() + "]");
            pos++;
        }
        server.sendMessage(currentPlayer, "_______________________________________________");
    }

    public static void shuffleDeck() {
        deckHandler.shuffleDeck();
    }

    public static void skipTurn() {
        playerHandler.nextTurn();
    }

    public static void stealCardFromPlayer() {
        Player currentPlayer = playerHandler.getCurrentPlayer();
        Player attackedPlayer = currentPlayer;

        printAllOthersPlayersHandSize(currentPlayer);

        while(attackedPlayer == currentPlayer){
            server.sendMessage(currentPlayer, "*options* which player do you want to steal a card from?");
            String answer = server.readMessage(currentPlayer, true);

            try {
                attackedPlayer = playerHandler.getActivePlayerFromString(answer); } catch (PlayerNotFoundException e) {e.printStackTrace();
            }
            if(attackedPlayer == currentPlayer){
                server.sendMessage(currentPlayer, "cannot steal a card from yourself");
                continue;
            }
        }

        //GET THE CARD HERE MAYBE LOOK AT FAVOR SINCE THEY ARE SIMILIAR___________________________________________________________________________________

    }

    public static void askForCardFromPlayer() {
    }
}