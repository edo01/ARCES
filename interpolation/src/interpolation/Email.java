/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpolation;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author Edoardo Carrà
 */
public class Email {
    
    private String addressee = "edocarra012@gmail.com";
    private String subject = "ARCES SAPA SENSOR ALARM";
    private String body = "The SAPA system is warning you,<br>"
            + "your SENSOR has exceed the threshold, pay attention.";
    private Properties mailServerProperties;
    private Session getMailSession;
    private MimeMessage generateMailMessage;
    
    private String sender = "edocarra012@gmail.com";
    private String password = "******";

    public Email(String addressee, String subject, String body) {
        this.addressee = addressee;
        this.subject = subject;
        this.body = body;
    }
    
    public Email() {}

    public void sendEmail(){
        // Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");
 
		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		getMailSession = Session.getDefaultInstance(mailServerProperties, null);
		generateMailMessage = new MimeMessage(getMailSession);
		try{
                    generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(addressee));
                    generateMailMessage.setSubject(subject);
                    generateMailMessage.setContent(body, "text/html");
                    System.out.println("Mail Session has been created successfully..");

                    // Step3
                    System.out.println("\n\n 3rd ===> Get Session and Send mail");
                    Transport transport = getMailSession.getTransport("smtp");

                    // Enter your correct gmail UserID and Password
                    // if you have 2FA enabled then provide App Specific Password
                    transport.connect("smtp.gmail.com", sender, password);
                    transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
                    transport.close();
                }catch(MessagingException ex){
                    ex.printStackTrace();
                }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   
    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAddressee() {
        return addressee;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }
    
    
}
