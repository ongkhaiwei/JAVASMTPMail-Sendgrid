package okw;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Hello world!
 */
public class App {

    // for example, smtp.mailgun.org
    private static String SMTP_SERVER = "smtp server ";
    private static String USERNAME = "";
    private static String PASSWORD = "";

    private static String EMAIL_FROM = "From@gmail.com";
    private static String EMAIL_TO = "email_1@yahoo.com, email_2@gmail.com";
    private static String EMAIL_TO_CC = "";

    private static String EMAIL_SUBJECT = "Test Send Email via SMTP";
    private static String EMAIL_TEXT = "Hello Java Mail \n ABC123";


    private App() {
    }

    String result = "";
	InputStream inputStream;

    public String getPropValues() throws IOException {
 
		try {
			Properties prop = new Properties();
			String propFileName = "/tmp/config.properties";
 
			inputStream = new FileInputStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
			// get the property value and print it out
            

            SMTP_SERVER = prop.getProperty("SMTP_SERVER");
            USERNAME = prop.getProperty("USERNAME");
            PASSWORD = prop.getProperty("PASSWORD");

            EMAIL_FROM = prop.getProperty("EMAIL_FROM");
            EMAIL_TO = prop.getProperty("EMAIL_TO");
            EMAIL_TO_CC = prop.getProperty("EMAIL_TO_CC");

            EMAIL_SUBJECT = prop.getProperty("EMAIL_SUBJECT");
            EMAIL_TEXT = prop.getProperty("EMAIL_TEXT");

			result = "SMTP_SERVER = " + SMTP_SERVER;
			System.out.println(result + "\nProgram Ran on " + time);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
    
    public static void main(String[] args) {

        
        App app = new App();
        try {
            System.out.println(app.getPropValues());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); // optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));
            // to
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));
            // cc
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(EMAIL_TO_CC, false));
            // subject
            msg.setSubject(EMAIL_SUBJECT);
            // content
            msg.setText(EMAIL_TEXT);
            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);
            // send
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + t.getLastServerResponse());
            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
    }
}
