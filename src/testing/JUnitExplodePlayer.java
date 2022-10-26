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

public class JUnitExplodePlayer {
    
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
    public void getWinner(){

        gameHandler.doSetup();
        emptyAllHands();
        while(deckHandler.getPlaydeckSize() > 0){
            deckHandler.drawCard();
        }

        deckHandler.addCardToDeck(new TacoCat());
        deckHandler.addCardToDeck(new ExplodingKitten());
        for(int i = 0; i<10;i++){
            
            ((Bot)playerHandler.getActivePlayers().get(0)).setMessage("pass");
            ((Bot)playerHandler.getActivePlayers().get(1)).setMessage("pass");
            
        }

        gameHandler.startGame(false);
        gameHandler.startGame(false);

        assertEquals(1, playerHandler.getActivePlayers().size());


    }
}
