/*
 * install two libraries: JavaServer Faces 1.1.01 and JavaMail API 1.4.1 from:
 * https://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.1-oth-JPR
 * 
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
    private String sensorKey = "sensor";
    private String body = "The SAPA alarm system is warning you,<br>"+ sensorKey
            + " has exceed the limit threshold, pay attention.";
    private Properties mailServerProperties;
    private Session getMailSession;
    private MimeMessage generateMailMessage;
    
    private String sender = "edocarra012@gmail.com";
    private String password = "******";
    
    public static final int gravityHIGHKEY =3;
    public static final int gravityMIDDLEKEY =2;
    public static final int gravityLOWKEY =1;
    
    private final String gravityHIGH = sensorKey
            + " has exceeded the limit threshold, pay attention.";
    private final String gravityMIDDLE = sensorKey
            + " will exceed the limit threshold, pay attention.";
    private final String gravityLOW = sensorKey
            + " there something strange, it was noticed a variation of the datas"
            + ", pay attention.";
    
    
    public Email(String addressee, String subject, String body,
            String sensorKey) {
        this.addressee = addressee;
        this.subject = subject;
        this.body = body;
    }
    
    public Email(String sensorKey) {
        this.sensorKey = sensorKey;
    }
    
    
    public Email() {}

    public void sendEmail(int gravityKey){
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
                    switch(gravityKey){
                        case gravityHIGHKEY:
                            generateMailMessage.setContent(gravityHIGH, "text/html");
                            break;
                        case gravityMIDDLEKEY:
                            generateMailMessage.setContent(gravityMIDDLE, "text/html");
                            break;
                        case gravityLOWKEY:
                            generateMailMessage.setContent(gravityLOW, "text/html");
                            break;
                        default:
                            generateMailMessage.setContent(body, "text/html");
                    }
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

    public String getSensorKey() {
        return sensorKey;
    }

    public void setSensorKey(String sensorKey) {
        this.sensorKey = sensorKey;
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
