package Client;

import game.*;
import message.*;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageProcessorForClient implements Runnable{
    private LinkedBlockingQueue linkedBlockingQueue;
    private Game game;
    private int numberOfMessages;
    private boolean isRunning;

    public MessageProcessorForClient(LinkedBlockingQueue linkedBlockingQueue, Game game) {
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.game = game;
        numberOfMessages = 0;
        isRunning = false;
    }

    public void run() {
        isRunning = true;
        System.out.println("MessageProcessorForServer started");
        while (isRunning){
            try {
                Message message = (Message) linkedBlockingQueue.take();
                numberOfMessages++;
                message.execute(game);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void stop() {
        System.out.println("MessageProcessorForServer stoped");
        isRunning = false;
        try {
            linkedBlockingQueue.put(new MsgServerAnnouncement("MessageProcessorForServer stoped"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int getNumberOfMessages() {
        return numberOfMessages;
    }

    boolean getIsRunning() {
        return isRunning;
    }
}
