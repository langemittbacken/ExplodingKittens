package explodingKittensGame;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import players.PlayerHandler;
import server.Server;
import settings.Gamemode;
import settings.GamemodeSettings;

/**
 * puts together all game logic.
 * @author langemittbacken
 *
 */
public class GameHandler {
   
   Server server;
   PlayerHandler playerHandler;


    public GameHandler(int nrOfPlayers, int nrOfBots) throws Exception{

      if(GamemodeSettings.getMaxAllowedPlayers()<(nrOfPlayers+nrOfBots) || GamemodeSettings.getMinPlayers()>(nrOfPlayers+nrOfBots)) {
         throw new Exception("between "+ GamemodeSettings.getMinPlayers() +" and "+ GamemodeSettings.getMaxAllowedPlayers() +" players allowed for current gamemode settings");
      };

      try {
         
         server = new Server(nrOfPlayers,nrOfBots);
      } catch (Exception e1) {
         e1.printStackTrace();
      } 
      

      //this code will be removed
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
