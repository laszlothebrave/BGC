package client;

import message.*;

public class MessageProcessorForClient implements Runnable{
    int numberOfMessages;

    MessageProcessorForClient() {
        numberOfMessages = 0;
    }

    public void run() {
        while (client.isRunning){
            try {
                Message message = (Message) client.linkedBlockingQueue.take();
                numberOfMessages++;
                message.execute();
            } catch (InterruptedException e) {
                System.out.println("Message processor crashed. Check for Error.");
            }
        }
    }

    void stop() {
        try {
            client.linkedBlockingQueue.put(new MsgAnnouncement("Message Processor stoped correctly"));
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Message processor crashed. Check for Error.");
        }
        client.linkedBlockingQueue.clear();
    }

}