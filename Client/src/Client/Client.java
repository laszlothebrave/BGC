package Client;

import message.*;
import message.toServer.MsgCreateAcount;
import message.toServer.MsgDataRequest;
import message.toServer.MsgPassword;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Client{
    static int port;
    static boolean isRunning;
    static LinkedBlockingQueue linkedBlockingQueue;
    static UserName userName;
    private static ClientListener clientListener;
    private static MessageProcessorForClient messageProcessorForClient;
    private static BufferedReader keyboardIn;
    private static String command;

    public static void main (String[] args) throws InterruptedException {
        initializeVariables();
        console();
        stop();
    }

    private static void initializeVariables() {
        userName = new UserName("default username");
        port = 13579;
        isRunning = false;
        linkedBlockingQueue = new LinkedBlockingQueue();
        clientListener = new ClientListener();
        messageProcessorForClient = new MessageProcessorForClient();
        keyboardIn = new BufferedReader(new InputStreamReader(System.in));
        command = "null";
    }

    private static void console() {
        System.out.println("Client started");
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
                    case "status":
                        status();
                        break;
                    case "port":
                        port();
                        break;
                    case "in":
                        logIn();
                        break;
                    case "out":
                        logOut();
                        break;
                    case "msg":
                        privateMessage();
                        break;
                    case "quit":
                        break;
                    case "help":
                        help();
                        break;
                    case "newUser":
                        newUser();
                        break;
                    default:
                        System.out.println("Unknow command");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Can't read from keyboard");
                break;
            }
        } while ( !command.equals("quit") ) ;
    }

    private static void start() {
        if (!isRunning) {
            new Thread(clientListener).start();
            new Thread(messageProcessorForClient).start();
            isRunning = true;
        }
    }

    private static void stop() {
        isRunning = false;
        clientListener.stop();
        messageProcessorForClient.stop();
    }

    private static void restart() {
        stop();
        clientListener = new ClientListener();
        messageProcessorForClient = new MessageProcessorForClient();
        start();
    }

    private static void status() {
            if (isRunning) {
                System.out.println("Client is running");
                System.out.println("Port:   " + port);
                System.out.println("Number of messages since last restart:   " + messageProcessorForClient.numberOfMessages);
            } else System.out.println("Client is not running");
    }
    private static void help() {
        System.out.println("Enter start to start connection");
        System.out.println("Enter stop to stop connection");
        System.out.println("Enter restart to restart program");
        System.out.println("Enter status to check status");
        System.out.println("Enter port to change port number (must restart after)");
        System.out.println("Enter in to log in");
        System.out.println("Enter out to log out");
        System.out.println("Enter msg to send private message");
        System.out.println("Enter quit to stop program");

    }

    private static void port() {
        try {
            System.out.println("Port number is:   " + port);
            System.out.println("Enter new port number:");
            command = keyboardIn.readLine();
            port = Integer.parseInt(command);
        } catch (NullPointerException | NumberFormatException | IOException e) {
            System.out.println("Can't change port number");
        }
    }

    private static void logIn() {
        System.out.println("Old userName is:   " + userName);
        System.out.println("Enter new userName:");
        try {
            UserName userName = new UserName(keyboardIn.readLine());
            clientListener.send(new MsgPassword(userName, "password"));
            clientListener.send(new MsgDataRequest());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logOut() {

    }

    private static void privateMessage() {
        try {
            System.out.println("Enter friends userName:   ");
            String userName = keyboardIn.readLine();
            System.out.println("Enter message:   ");
            String message = keyboardIn.readLine();
            clientListener.send(new MsgPrivateMessage(userName, message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(Message message){
        clientListener.send(message);
    }

    public static UserName getUserName() {
        return userName;
    }

    public static void newUser(){
        try {
            System.out.println("podaj login,haslo,email");
            send(new MsgCreateAcount(keyboardIn.readLine(),keyboardIn.readLine(),keyboardIn.readLine()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
