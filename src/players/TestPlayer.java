package players;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestPlayer extends Bot {

    public TestPlayer(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient,
            ObjectOutputStream outToClient) {
        super(playerID, isBot, connection, inFromClient, outToClient);

    }
    
}