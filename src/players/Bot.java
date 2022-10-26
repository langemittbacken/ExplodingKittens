package players;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Bot extends Player{

    protected LinkedList<String> nextMsg;

    public Bot(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient,
            ObjectOutputStream outToClient) {
        super(playerID, isBot, connection, inFromClient, outToClient);
        nextMsg = new LinkedList<String>();
    }

    public void setMessage(String msg) {
        nextMsg.add(msg);
    }

    public String getNextMsg(){
        try {
            return nextMsg.removeFirst();
        } catch (NoSuchElementException e) {
            return "";
        }
    }


    
}
