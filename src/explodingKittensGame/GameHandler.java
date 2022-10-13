package explodingKittensGame;

import java.util.concurrent.TimeUnit;

import cards.Card;
import players.PlayerHand;

public class GameHandler {
    public GameHandler(){
      
      
      int i = 0;
        while(true){
           System.out.println(i++);
            try {
               TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    }
    
}
