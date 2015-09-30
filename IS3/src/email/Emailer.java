package email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Emailer {

	public static void send(String to,String subject,String content){    
		Properties props = new Properties();

		// Nombre del host de correo, es smtp.gmail.com
		props.setProperty("mail.smtp.host", "smtp.gmail.com");

		// TLS si está disponible
		props.setProperty("mail.smtp.starttls.enable", "true");

		// Puerto de gmail para envio de correos
		props.setProperty("mail.smtp.port","587");

		// Nombre del usuario
		props.setProperty("mail.smtp.user", "farmya2015@gmail.com");

		// Si requiere o no usuario y password para conectarse.
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		session.setDebug(true);

		MimeMessage message = new MimeMessage(session);

		// Quien envia el correo
		try{
			message.setFrom(new InternetAddress("ME"));

			// A quien va dirigido
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject(subject);
			message.setText(content			
					,"ISO-8859-1",
					"html");
			
			Transport t = session.getTransport("smtp");
			
			t.connect("farmya2015@gmail.com","farmya2015UCU");
			
			t.sendMessage(message,message.getAllRecipients());
			
			t.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
