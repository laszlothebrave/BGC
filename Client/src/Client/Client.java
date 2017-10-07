package Client;

import game.*;
import message.*;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Client{
    static private int port;
    static private LinkedBlockingQueue linkedBlockingQueue;
    static private ClientListener clientListener;
    static private MessageProcessorForClient messageProcessorForClient;
    static private Game game;
    static private BufferedReader keyboardIn;
    static private String command;
    private static UserName userName;

    public static void main (String[] args) throws InterruptedException {
        port = 13579;
        reset();
        keyboardIn = new BufferedReader(new InputStreamReader(System.in));
        command = "null";
        userName = new UserName("default");
        console();
    }

    private static void console() {
        System.out.println("Client started");
        System.out.println("Enter help for help");
        while ( !command.equals("quit") ) {
            try {
                command = keyboardIn.readLine();
            } catch (IOException e) {
                e.printStackTrace();
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
                case "status":
                    status();
                    break;
                case "help":
                    help();
                    break;
                case "port":
                    port();
                    break;
                case "username":
                    userName();
                    break;
                case "private message":
                    privateMessage();
                    break;
                case "quit":
                    break;
                default:
                    System.out.println("Unknow command");
                    break;
            }
        }
    }

    private static void start() {
        new Thread(clientListener).start();
        new Thread(messageProcessorForClient).start();

    }

    private static void stop() {
        try {
            clientListener.stop();
            messageProcessorForClient.stop();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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
        game = new Game();
        linkedBlockingQueue = new LinkedBlockingQueue();
        messageProcessorForClient = new MessageProcessorForClient(linkedBlockingQueue, game);
        clientListener = new ClientListener(port, linkedBlockingQueue, messageProcessorForClient);
    }

    private static void status() {
        try {
            if (clientListener.getIsRunning()) {
                System.out.println("Port:   " + port);
            } else System.out.println("ClientListener is not running");
            if (!messageProcessorForClient.getIsRunning()) {
                System.out.println("MessageProcessorForClient is not running");
            } else {
                System.out.println("MessageProcessorForClient is running");
                System.out.println("Number of messages since last restart:   " + messageProcessorForClient.getNumberOfMessages());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    private static void help() {
        System.out.println("Enter start to start connection");
        System.out.println("Enter stop to terminate connection");
        System.out.println("Enter status to check status");
        System.out.println("Enter quit to stop program");
        System.out.println("Enter username to set userName");
        System.out.println("Enter msg to send private message");
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

    private static void userName() {
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
}
