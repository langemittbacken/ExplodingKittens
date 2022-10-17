package explodingKittensGame;

import java.util.concurrent.TimeUnit;

import players.PlayerHandler;
import server.Server;
import settings.Gamemode;

/**
 * puts together all game logic.
 * @author langemittbacken
 *
 */
public class GameHandler {
   
   Server server;
   PlayerHandler playerHandler;
   Gamemode gamemode;

    public GameHandler(){
      
      try {
         System.out.println("How many players?");
         server = new Server(2,0);
      } catch (Exception e1) {
         e1.printStackTrace();
      } 
      
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
