package email;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmationEmail extends MimeMessage{

    public ConfirmationEmail(Session session,String konfirmationKey, String recipientEmailAdress) {
        super(session);
        try {
            setFrom(new InternetAddress("villamrozek"));
            setRecipient(Message.RecipientType.TO,new InternetAddress(recipientEmailAdress));
            setSubject("Potwierdzenie");
            setContent("<h1>Klucz: "+konfirmationKey+"</h1>", "text/html; charset=utf-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
