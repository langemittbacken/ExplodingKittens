package explodingKittensGame;

import java.util.concurrent.TimeUnit;

/**
 * puts together all game logic.
 * @author langemittbacken
 *
 */
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
