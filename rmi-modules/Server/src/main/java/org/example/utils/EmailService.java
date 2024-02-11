package org.example.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailService {


    private static final String SENDER_EMAIL = "cypherapp6@gmail.com"; // Replace with your Gmail email address
    private static final String SENDER_PASSWORD = "vycn qglp raza aryx";

    //"CypherApp123456789"; // Replace with your Gmail password

    public static void sendEmailAsync(String recipient , String name) {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(() -> sendEmail(recipient , name));
        executor.shutdown(); // Shutdown the executor once all tasks are completed
    }

    private static void sendEmail(String recipient , String name) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Welcome to Cypher Chat App - Your Gateway to Secure Communication!");

            String bodyContent = "<div style=\"max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ccc; border-radius: 10px;\">\n" +
                    "    <p style=\"font-size: 16px;\">\n" +
                    "        Dear " + name +",<br><br>\n" +
                    "\n" +
                    "        Welcome to Cypher Chat App, your ultimate destination for secure and seamless desktop communication! &#128640; <br><br>\n" +
                    "\n" +
                    "        We are thrilled to have you on board and are excited to embark on this journey of encrypted conversations together.<br><br>\n" +
                    "\n" +
                    "        Cypher Chat App is not just another chat platform; it's a fortress of privacy where your messages remain confidential and your interactions are shielded from prying eyes.<br><br>\n" +
                    "\n" +
                    "        Should you have any questions, concerns, or feedback, our dedicated support team is here to assist you. Simply drop us a message at <a href=\"mailto:cypherapp6@gmail.com\">cypherapp6@gmail.com</a>.<br><br>\n" +
                    "\n" +
                    "        Once again, welcome to Cypher Chat App! Your privacy is our priority, and we're committed to ensuring that your communication experience is nothing short of exceptional.<br><br>\n" +
                    "\n" +
                    "        Thank you for choosing Cypher Chat App.<br><br>\n" +
                    "\n" +
                    "        Best regards,<br><br>\n" +
                    "        Cypher Chat App Team\n" +
                    "    </p>\n" +
                    "    <img src=\"https://i.postimg.cc/y8fHrs12/cypher.png\" alt=\"Cypher Chat App Logo\" style=\"width: 100%; height: auto; margin-top: 20px;\">\n" +
                    "</div>";
            message.setContent(bodyContent, "text/html");
            Transport.send(message);
            System.out.println("Mail successfully sent to " + recipient);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

}
