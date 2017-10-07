package server;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerListener implements Runnable{
    private LinkedBlockingQueue linkedBlockingQueue;
    private UserList userList;
    private int port;
    private boolean isRunning;
    private ServerSocket serverSocket;

    public ServerListener(LinkedBlockingQueue linkedBlockingQueue, UserList userList, int port) {
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.userList = userList;
        this.port = port;
        isRunning = false;
    }

    public void run() {
        isRunning = true;
        System.out.println("ServerListener started");
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("ServerSocket created");
            while (isRunning) {
                try {
                    clientSocket = serverSocket.accept();
                    new Thread(new User(clientSocket, linkedBlockingQueue, userList)).start();
                } catch (IOException e) {
                    System.out.println("Server socket closed");
                }
            }
        } catch (IOException e) {
            isRunning = false;
            System.out.println("Creating serverSocket failed");
            e.printStackTrace();
        }
    }

    public synchronized void stop() {
        try {
            isRunning = false;
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ServerListener stoped");
    }

    void setPort(int port) {
        this.port = port;
    }

    int getPort() {
        return port;
    }

    boolean getIsRunning() {
        return isRunning;
    }
}

