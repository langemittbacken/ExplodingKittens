package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

import players.Player;
import players.PlayerHandler;


/**
 * Server that host the game for players
 * @author langemittbacken
 *
 */
public class Server {
   
    private int secondsToInterruptWithNope = PlayerHandler.getSecondsToInterruptWithNope();
   
    public ServerSocket aSocket;
    public PlayerHandler playerHandler;
    Scanner in;
   
    public Server(int numberPlayers, int numberOfBots) throws Exception {
      
        this.playerHandler = PlayerHandler.getInstance();
        
        //this code will print the servers ip so the one starting the server can distribute it.
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        
        //Open for connections if there are online players
        for(int i=0; i<numberOfBots; i++) {
            playerHandler.addPlayer(i+1, true, null, null, null); //add a bot    
        }
        
        if(numberPlayers>0)
            aSocket = new ServerSocket(2048);
        for(int i=numberOfBots; i<numberPlayers+numberOfBots; i++) {
            System.out.println("waiting for " + (numberPlayers+numberOfBots -i) + " additional players");
            Socket connectionSocket = aSocket.accept();
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
            
            playerHandler.addPlayer(i, false, connectionSocket, inFromClient, outToClient); //add an online client
            
            System.out.println("Connected to player " + i);
            outToClient.writeObject("You connected to the server as player " + i + "\n");
        } 
        sendMsgToAllPlayers("All players connected!");
    } 
   
    public void sendMessage(Player player, Object message) {
        if(player.isOnline()) {
            try {player.getOutToClient().writeObject(message);} catch (Exception e) {}
        } else if(!player.isBot()){
            System.out.println(message);                
        }
    }

    public void sendMsgToAllPlayers(String message) {
        for(Player notify: playerHandler.getAllPlayers()){
            sendMessage(notify, message);
        }
        
    }

    public String readMessage(Player player, boolean interruptable) {
        String word = " "; 
      
      
        if(player.isOnline())

            try{
                word = (String) player.getInFromClient().readObject();

            } catch (Exception e){
                System.out.println("Reading from client failed: " + e.getMessage());
            }

        else
            try {
                if(interruptable) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    int millisecondsWaited = 0;
                    while(!br.ready() && millisecondsWaited<(secondsToInterruptWithNope*1000)) {
                        Thread.sleep(200);
                        millisecondsWaited += 200;
                    }
                    if(br.ready())
                        return br.readLine(); 

                } else {
                    in = new Scanner(System.in); 
                    word=in.nextLine();
                }

            } catch(Exception e){System.out.println(e.getMessage());}
      
      
      return word;
  }   
   
}
