package server;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.net.*;

public class ServerListener implements Runnable{
    private static ServerSocket serverSocket;

    public void run() {
        System.setProperty("javax.net.ssl.keyStore", "lib/server.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        System.setProperty("javax.net.ssl.trustStore", "lib/truststore3");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        System.out.println("ServerListener started");
        Socket clientSocket;

        try {
            ServerSocketFactory serverSocketFactory = SSLServerSocketFactory.getDefault();
            serverSocket = serverSocketFactory.createServerSocket(Server.port);
            while (Server.isRunning) {
                try {
                    clientSocket = serverSocket.accept();

                    new Thread(new User(clientSocket, Server.linkedBlockingQueue)).start();
                } catch (IOException e) {
                    System.out.println("ServerSocket closed");
                }
            }
        } catch (IOException e) {
            System.out.println("Creating serverSocket crashed. Check for Error");
            e.printStackTrace();
        }
    }

    void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ServerListener stoped correctly");
    }

    void printServerSocketInfo () {
        String[] tab1 = ((SSLServerSocket) serverSocket).getEnabledCipherSuites();
        String[] tab2 = ((SSLServerSocket) serverSocket).getSupportedCipherSuites();
        String[] tab3 = ((SSLServerSocket) serverSocket).getEnabledProtocols();
        String[] tab4 = ((SSLServerSocket) serverSocket).getSupportedProtocols();
        System.out.println("\nEnabled ciphers");
        for (int i = 0; i < tab1.length; i++) {
            System.out.println((tab1[i]));
        }
        System.out.println("\nSup ciphers");
        for (int i = 0; i < tab2.length; i++) {
            System.out.println((tab2[i]));
        }
        System.out.println("\nEnabled protocols");
        for (int i = 0; i < tab3.length; i++) {
            System.out.println((tab3[i]));
        }
        System.out.println("\nSup protocols");
        for (int i = 0; i < tab4.length; i++) {
            System.out.println((tab4[i]));
        }
        System.out.println(((SSLServerSocket) serverSocket).getEnableSessionCreation());
        System.out.println(((SSLServerSocket) serverSocket).getNeedClientAuth());
        System.out.println(((SSLServerSocket) serverSocket).getWantClientAuth());
    }
}

