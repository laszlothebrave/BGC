package server;

import message.*;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private static LinkedBlockingQueue linkedBlockingQueue;
    private static UserList userList;
    private static RoomList roomList;
    private static ServerListener serverListener;
    private static MessageProcessorForServer messageProcessorForServer;
    private static BufferedReader keyboardIn;
    private static String command;
    private static int port;

    public static void main(String[] args) {
        keyboardIn = new BufferedReader(new InputStreamReader(System.in));
        command = "null";
        port = 13579;
        linkedBlockingQueue = new LinkedBlockingQueue();
        userList = new UserList();
        roomList = new RoomList();
        serverListener = new ServerListener(linkedBlockingQueue, userList, port);
        messageProcessorForServer = new MessageProcessorForServer(linkedBlockingQueue, userList, roomList);
        startConsole();
    }

    static private void startConsole() {
        System.out.println("Server started");
        System.out.println("Enter help for help");
        do {
            try {
                command = keyboardIn.readLine();
            } catch (IOException e) {
                System.out.println("Can't read from keyboard");;
            }
            switch (command) {
                case "start":
                    start();
                    break;
                case "stop":
                    stop();
                    break;
                case "restart":
                    restart();
                    break;
                case "reset":
                    reset();
                    break;
                case "help":
                    help();
                    break;
                case "status":
                    status();
                    break;
                case "port":
                    port();
                    break;
                case "quit":
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        } while (!command.equals("quit"));
        stop();
    }

    private static void start() {
        new Thread(serverListener).start();
        new Thread(messageProcessorForServer).start();
    }

    private static void stop() {
            serverListener.stop();
            messageProcessorForServer.stop();
    }

    private static void restart() {
            stop();
            System.out.println("Restart in progres");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            start();
    }

    private static void reset() {
        linkedBlockingQueue = new LinkedBlockingQueue();
        userList = new UserList();
        roomList = new RoomList();
        serverListener = new ServerListener(linkedBlockingQueue, userList, port);
        messageProcessorForServer = new MessageProcessorForServer(linkedBlockingQueue, userList, roomList);
    }

    private static void help() {
        System.out.println("Enter start to start server");
        System.out.println("Enter stop to stop server");
        System.out.println("Enter restart to restart");
        System.out.println("Enter reset to applay changes like new port number");
        System.out.println("Enter status to see status");
        System.out.println("Enter port to change port number");
        System.out.println("Enter quit to quit");
    }

    private static void status() {
            if (serverListener.getIsRunning()) {
                System.out.println("Listener is running");
                System.out.println("Listening on port:   " + serverListener.getPort());
                System.out.println("Users currently:   " + userList.getCurrentUser());
                System.out.println("Users since last restart:    " + userList.getUserCounter());
            } else System.out.println("Listener is not running");
            if (!messageProcessorForServer.isRunning()) {
                System.out.println("MessageProcessorForServer is not running");
            } else {
                System.out.println("MessageProcessorForServer is running");
                System.out.println("Number of messages since last restart:   " + messageProcessorForServer.getNumberOfMessages());
            }
    }

    private static void port() {
        try {
            System.out.println("Port number is:   " + serverListener.getPort());
            System.out.println("Enter new port number:");
            try {
                command = keyboardIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            port = Integer.parseInt(command);
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}