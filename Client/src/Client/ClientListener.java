package Client;

import message.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientListener implements Runnable{
    private LinkedBlockingQueue linkedBlockingQueue;
    private MessageProcessorForClient messageProcessorForClient;
    private int port;
    private Socket socket;
    private ObjectInputStream inOis;
    private ObjectOutputStream outOis;
    private boolean isRunning;


    public ClientListener(int port, LinkedBlockingQueue linkedBlockingQueue, MessageProcessorForClient messageProcessorForClient) {
        this.messageProcessorForClient = messageProcessorForClient;
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.port = port;
        isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        try {
            socket = new Socket("localhost", 13579);
            outOis = new ObjectOutputStream(socket.getOutputStream());
            inOis = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connection established");
            System.out.println("ClientListener is running");
            while (isRunning) linkedBlockingQueue.put(inOis.readObject());
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("1");
        } catch (IOException e) {
            System.out.println("Socket closed");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("3");
        }
        messageProcessorForClient.stop();
    }

    public void stop() {
        isRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ClientListener stopped");
    }

    void send(Message message) {
        try {
            outOis.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getIsRunning() {
        return isRunning;
    }
}
