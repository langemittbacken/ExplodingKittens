package explodingKittensGame;

import java.util.InputMismatchException;
import java.util.Scanner;

import settings.GamemodeSettings;

public class MainMenu {
    
    public MainMenu(){

        

        while(true){
            
            startGame();
            break;
            
        }
    }

    private void startGame() {

        int maxAllowedPlayers = GamemodeSettings.getMaxAllowedPlayers();
        int minPlayers = GamemodeSettings.getMinPlayers();

        int players;
        int bots = 0;
        Scanner myScan;

        System.out.println("starting Game...");

        myScan = new Scanner(System.in);
        
        while(true){
            System.out.println("How many players? (" + minPlayers + "-" + maxAllowedPlayers+ ")");
            try {
                players = myScan.nextInt();
                
                System.out.println("How many bots?");

                bots = myScan.nextInt();

                if(bots+players < minPlayers || bots+players > maxAllowedPlayers){
                    System.out.println("you added a total of " + (bots+players) + " players but have to be between(" 
                    + minPlayers + "-" + maxAllowedPlayers+ ") for current gamemode");
                    System.out.println();
                    continue;
                }

                break;
                
            } catch (InputMismatchException e) {
                System.out.println("wrong input, integer only accepted");
                System.out.println();
                myScan.nextLine();
            } 
        }
        myScan.close();
        try {
            new GameHandler(players, bots);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
