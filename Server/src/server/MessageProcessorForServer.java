package server;

import message.*;
import java.util.concurrent.LinkedBlockingQueue;

class MessageProcessorForServer implements Runnable{
    private LinkedBlockingQueue linkedBlockingQueue;
    private UserList userList;
    private RoomList roomList;
    private  int numberOfMessages;
    private boolean isRunning;


    MessageProcessorForServer(LinkedBlockingQueue linkedBlockingQueue, UserList userList, RoomList roomList) {
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.userList = userList;
        this.roomList = roomList;
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
                message.execute(userList, roomList);
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
        roomList.deleteAll();
        userList.closeAll();
    }

    int getNumberOfMessages() {
        return numberOfMessages;
    }

    boolean isRunning() {
        return isRunning;
    }
}