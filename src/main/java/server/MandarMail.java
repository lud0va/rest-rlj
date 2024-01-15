package server;

import common.ConstantsServer;
import jakarta.inject.Inject;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import common.Configuration;

import java.util.Properties;


@Log4j2
public class MandarMail {

    private Configuration config;

    @Inject
    public MandarMail(Configuration config) {
        this.config = config;
    }


    public void generateAndSendEmail(String to, String msg, String subject) throws MessagingException {

        Properties mailServerProperties;
        Session getMailSession;
        MimeMessage generateMailMessage;


        mailServerProperties = System.getProperties();
        mailServerProperties.put(ConstantsServer.MAIL_SMTP_PORT, Integer.parseInt(ConstantsServer.NUMBERMAIL));
        mailServerProperties.put(ConstantsServer.MAIL_SMTP_AUTH, ConstantsServer.TRUE);
        mailServerProperties.put(ConstantsServer.MAIL_SMTP_SSL_TRUST, ConstantsServer.SMTP_GMAIL_COM);

        mailServerProperties.put(ConstantsServer.MAIL_SMTP_STARTTLS_ENABLE, ConstantsServer.TRUE);


        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        generateMailMessage.setSubject(subject);
        String emailBody = msg;
        generateMailMessage.setContent(emailBody, ConstantsServer.CONTENT_TYPE);


        Transport transport = getMailSession.getTransport(ConstantsServer.SMTP);


        transport.connect(config.getHost(),
                config.getUser(),
                config.getPassword());
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
}
