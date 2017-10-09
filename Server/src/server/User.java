package server;

import Exeptions.FullServerExeption;
import message.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class User implements Runnable{
    private Socket clientSocket;
    private LinkedBlockingQueue linkedBlockingQueue;
    private UserName userName;
    private ObjectOutputStream outOis;
    private ObjectInputStream inOis;

    User(Socket clientSocket, LinkedBlockingQueue linkedBlockingQueue) {
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.clientSocket = clientSocket;
        userName = new UserName(Integer.toString(Server.userList.getAndIncreaseUserCounter()));
    }

    private void createStreams() throws IOException {
        outOis = new ObjectOutputStream(clientSocket.getOutputStream());
        inOis = new ObjectInputStream(clientSocket.getInputStream());
    }

    private void addToServer() throws IOException, FullServerExeption {
        if (Server.userList.getCurrentUser() >= Server.userList.getMaxUsers()) {
            outOis.writeObject(new MsgServerAnnouncement("Can't connect. server full."));
            throw new FullServerExeption();
        }
        Server.userList.add(userName, this);
    }

    private void listen() throws IOException, ClassNotFoundException, InterruptedException{
        while (true) linkedBlockingQueue.put(inOis.readObject());
    }

    public void send(Message message) throws IOException {
        outOis.writeObject(message);
    }

    public void run() {
        try {
            createStreams();
            addToServer();
            listen();
        } catch (InterruptedException | IOException | ClassNotFoundException | FullServerExeption e) {
            System.out.println("User:   " + userName.getUserName() + "   disconnected");
        }
        disconnect();
    }

    void disconnect() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Server.userList.remove(userName);
    }
}
