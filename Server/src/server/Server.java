package server;

import database.AccountManager;
import database.Mysql;
import database.PasswordHash;
import database.UserExceptions.CreateUserExcaptions.EmailAdressOccupiedException;
import database.UserExceptions.CreateUserExcaptions.InvalidEmailAdressException;
import database.UserExceptions.CreateUserExcaptions.UserExistException;
import email.SMTPServer;
import message.*;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    static int port;
    static boolean isRunning;
    static LinkedBlockingQueue linkedBlockingQueue;
    public static UserList userList;
    public static RoomList roomList;
    public static AccountManager accountManager;
    private static ServerListener serverListener;
    private static MessageProcessorForServer messageProcessorForServer;
    private static BufferedReader keyboardIn;
    private static String command;

    public static void main(String[] args) {
       initializeVariables();
       startConsole();
       stop();
    }

    public static void initializeVariables() {
        port = 13579;
        isRunning = false;
        linkedBlockingQueue = new LinkedBlockingQueue();
        userList = new UserList();
        roomList = new RoomList();
        serverListener = new ServerListener();
        messageProcessorForServer = new MessageProcessorForServer();
        keyboardIn = new BufferedReader(new InputStreamReader(System.in));
        accountManager = new AccountManager();
        command = "null";
    }

    static private void startConsole() {
        System.out.println("Server started");
        System.out.println("Enter help for help");
        do {
            try {
                command = keyboardIn.readLine();
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
                    case "help":
                        help();
                        break;
                    case "status":
                        status();
                        break;
                    case "port":
                        port();
                        break;
                    case "listLoggedUsers":
                        listLoggedUsers();
                        break;
                    case "quit":
                        break;
                    default:
                        System.out.println("Unknown command");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Can't read from keyboard");
                break;
            }
        } while (!command.equals("quit"));
    }

    private static void listLoggedUsers() {
        userList.print();
    }

    private static void start() {
        if (!isRunning) {
            isRunning = true;
            new Thread(serverListener).start();
            new Thread(messageProcessorForServer).start();
            Mysql.connect();
            SMTPServer.connect();
        }
    }

    private static void stop() {
            isRunning = false;
            serverListener.stop();
            messageProcessorForServer.stop();
            userList.removeAll();
            roomList.removeAll();
            Mysql.disconnect();
            SMTPServer.disconnect();
    }

    private static void restart() {
        stop();
        linkedBlockingQueue = new LinkedBlockingQueue();
        userList = new UserList();
        roomList = new RoomList();
        serverListener = new ServerListener();
        messageProcessorForServer = new MessageProcessorForServer();
        start();
    }

    private static void status() {
        if (isRunning) {
                System.out.println("Listening on port:   " + port);
                System.out.println("Users currently:   " + userList.getCurrentUser());
                System.out.println("Users since last restart:    " + userList.getUserCounter());
                System.out.println("MessageProcessorForServer is running");
                System.out.println("Number of messages since last restart:   " + messageProcessorForServer.numberOfMessages);
            } else {
                System.out.println("Listener is not running");
                System.out.println("Message processor is not running");
             }
    }

    private static void port() {
        try {
            System.out.println("Port number is:   " + port);
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

    private static void help() {
        System.out.println("Enter start to start server");
        System.out.println("Enter stop to stop server");
        System.out.println("Enter restart to restart");
        System.out.println("Enter status to see status");
        System.out.println("Enter port to change port number");
        System.out.println("Enter listLoggedUsers to see logged users");
        System.out.println("Enter quit to quit");
    }

    public static void send (UserName userName, Message message){
        try {
            userList.getUser(userName).send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}