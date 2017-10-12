package Client;

import message.*;

public class MessageProcessorForClient implements Runnable{
    int numberOfMessages;

    MessageProcessorForClient() {
        numberOfMessages = 0;
    }

    public void run() {
        while (Client.isRunning){
            try {
                Message message = (Message) Client.linkedBlockingQueue.take();
                numberOfMessages++;
                message.execute();
            } catch (InterruptedException e) {
                System.out.println("Message processor crashed. Check for Error.");
            }
        }
    }

    void stop() {
        try {
            Client.linkedBlockingQueue.put(new MsgAnnouncement("Message Processor stoped correctly"));
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Message processor crashed. Check for Error.");
        }
        Client.linkedBlockingQueue.clear();
    }

}