package Database;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

class ConfirmationMail {
    private Properties properties = new Properties();

    public ConfirmationMail() {
        System.setProperty("javax.net.ssl.trustStore", "C:/Users/Zin/Desktop/pro/java/BGC/lib/truststore3");
        System.setProperty("javax.net.ssl.trustStorePassword", "password");

        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.setProperty("mail.smtp.host", "37.233.99.37");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.port","587");
        properties.setProperty("mail.transport.protocol","smtp");
        properties.setProperty("mail.smtp.user","root");
        properties.setProperty("mail.smtp.password","elomelo123");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("root","elomelo123");
            }
        };

        Session session = Session.getInstance(properties,auth);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("root"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("znajer@gmail.com"));
            message.setSubject("elo");
            message.setText("potwierdz emaila");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

public class run{

    public static void main(String[] args) {
        ConfirmationMail elo = new ConfirmationMail();
    }
}
