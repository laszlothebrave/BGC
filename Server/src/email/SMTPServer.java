package email;

import javax.mail.*;
import java.util.Properties;

public class SMTPServer {

    private static Transport transport;
    private static Session session;

    private static Properties SMTPPropetries(){

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", "37.233.99.37");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.user", "root");
        properties.setProperty("mail.smtp.password", "elomelo123");
        return properties;
    }

    private static Authenticator getAuthenticator(){
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bgc", "elomelo123");
            }
        };
    }

    public static void connect() {
        session = Session.getInstance(SMTPPropetries(), getAuthenticator());
        try {
            transport = session.getTransport();
            transport.connect();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void send(Message message){
        if(transport.isConnected())
        try {
            transport.sendMessage(message,message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect(){
        try {
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
