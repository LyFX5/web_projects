import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {
    public void mail(String message, String recipient) throws javax.mail.MessagingException{
        final Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.yandex.ru");
        properties.put("mail.smtp.socketFactory.port",465);
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.port",465);

        Session mailSession = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("topjavaprogrammer@yandex.ru","1234java5678");
            }
        });
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setFrom(new InternetAddress("topjavaprogrammer@yandex.ru"));
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        mimeMessage.setSubject("Password");
        mimeMessage.setText("Your password from the application Animals: "+message);
        Transport.send(mimeMessage);
    }
}
