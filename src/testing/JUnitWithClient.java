package testing;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.util.Enumeration;
import java.util.LinkedList;

import org.junit.*;

import cards.Card;
import cards.implCards.*;
import decks.DeckHandler;
import exceptions.CardNotFoundException;
import exceptions.PlayerNotFoundException;
import explodingKittensGame.GameHandler;
import players.Bot;
import players.Player;
import players.PlayerHandler;
import players.TestPlayer;

import settings.GamemodeSettings;

public class JUnitWithClient {

    GameHandler gameHandler;
    PlayerHandler playerHandler;
    DeckHandler deckHandler;
    LinkedList<TestPlayer> testPlayers = new LinkedList<TestPlayer>();

    final int NR_OF_TEST_PLAYERS = 2;
    final int GENERATE_TACO_CATS = 10;

    @Before
    public void setup() throws Exception{
        gameHandler = new GameHandler(0, NR_OF_TEST_PLAYERS);
        playerHandler = PlayerHandler.getInstance();
        deckHandler = DeckHandler.getInstance();

        

        for(int i = 0; i < NR_OF_TEST_PLAYERS; i++){
            playerHandler.removeFromActivePlayers(playerHandler.getPlayer(i));
        }

        for(int i = 0; i<NR_OF_TEST_PLAYERS; i++) {
            testPlayers.add(new TestPlayer(i, true, null, null, null));
            playerHandler.addtoActivePlayers(testPlayers.getLast());
        }

        gameHandler.doSetup();

        // while(deckHandler.getPlaydeckSize() > 0){
        //     deckHandler.drawCard();
        // }


        emptyAllHands();

        
        
    }

    private void emptyAllHands() {
        for(Player p : playerHandler.getActivePlayers()) {
            p.emptyHand();
        }
    }

    @Test
    public void dummy(){
        testPlayers.get(0).setMessage("Pokemon");

        assertEquals("Pokemon", testPlayers.get(0).getNextMsg());
    }

    @Test
    public void passATurn(){
        Bot currentTestPlayer = (Bot)playerHandler.getCurrentPlayer();
        emptyAllHands();
        currentTestPlayer.setMessage("pass");

        gameHandler.startGame(false);

        assertNotEquals(currentTestPlayer, playerHandler.getCurrentPlayer());
    }



    /**
     * 11. Playing a card
     * @throws CardNotFoundException
     */
    @Test
    public void playAttack(){
        Bot currentTestPlayer = (Bot)playerHandler.getCurrentPlayer();
        emptyAllHands();

        currentTestPlayer.addCardToHand(new Attack());

        currentTestPlayer.setMessage("Attack");

        gameHandler.startGame(false);
        assertNotEquals(currentTestPlayer, playerHandler.getCurrentPlayer());
        assertEquals(2, playerHandler.getTurnsLeft());

        currentTestPlayer = (Bot)playerHandler.getCurrentPlayer();

        currentTestPlayer.addCardToHand(new Attack());
        currentTestPlayer.setMessage("Attack");

        gameHandler.startGame(false);
        assertEquals(4, playerHandler.getTurnsLeft());
        assertNotEquals(currentTestPlayer, playerHandler.getCurrentPlayer());

    }

    @Test
    public void playAFavor() throws CardNotFoundException{
        Bot currentTestPlayer = (Bot)playerHandler.getCurrentPlayer();
        emptyAllHands();

        currentTestPlayer.addCardToHand(new Favor());
        currentTestPlayer.setMessage("Favor");


        if(currentTestPlayer.getPlayerID() == 0) {
            currentTestPlayer.setMessage("1");
            testPlayers.get(1).setMessage("Taco Cat");
            testPlayers.get(1).addCardToHand(new TacoCat());
        } else {
            currentTestPlayer.setMessage("0");
            testPlayers.get(0).setMessage("Taco Cat");
            testPlayers.get(0).addCardToHand(new TacoCat());
        }

        gameHandler.startGame(false);
        assertEquals(1, currentTestPlayer.countCardsOf("Taco Cat"));

        emptyAllHands();
        
    }

    @Test
    public void play2Cards(){
        Bot currentTestPlayer = (Bot)playerHandler.getCurrentPlayer();
        emptyAllHands();

        currentTestPlayer.addCardToHand(new TacoCat());
        currentTestPlayer.addCardToHand(new TacoCat());

        

        currentTestPlayer.setMessage("2x Taco cat");

        int theOtherPlayersID = (currentTestPlayer.getPlayerID() == 0) ? 1 : 0;

        playerHandler.getActivePlayers().get(theOtherPlayersID).addCardToHand(new Shuffle());

        currentTestPlayer.setMessage(Integer.toString(theOtherPlayersID));

        gameHandler.startGame(false);

        assertTrue(currentTestPlayer.hasCard("Shuffle"));

    }

    
    
}
