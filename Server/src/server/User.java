package server;

import Exeptions.FullServerExeption;
import message.*;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class User implements Runnable{
    private Socket socket;
    private LinkedBlockingQueue linkedBlockingQueue;
    private UserList userList;
    private UserName userName;
    private ObjectOutputStream outOis;
    private ObjectInputStream inOis;

    User(Socket socket, LinkedBlockingQueue linkedBlockingQueue, UserList userList) {
        this.socket = socket;
        this.linkedBlockingQueue = linkedBlockingQueue;
        this.userList = userList;
        userName = new UserName(Integer.toString(userList.getAndIncreaseUserCounter()));
    }
    private void createStreams() throws IOException {
        outOis = new ObjectOutputStream(socket.getOutputStream());
        inOis = new ObjectInputStream(socket.getInputStream());
    }

    private void addToServer() throws IOException, FullServerExeption {
        if (userList.getCurrentUser() >= userList.getMaxUsers()) {
            outOis.writeObject(new MsgServerAnnouncement("Can't connect. server full."));
            throw new FullServerExeption();
        }
        userList.add(userName, this);
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
            System.out.println("User disconected, check Exeptions");
        }
        closeSocket();
    }

    void closeSocket() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        userList.remove(userName);
    }
}
