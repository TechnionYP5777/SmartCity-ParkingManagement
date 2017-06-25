/**
 * 
 */
package logic;

import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.*;  
import javax.mail.internet.*;

import javax.mail.Message.RecipientType;

/**
 * @author Toma
 *
 */
public class EmailNotification {
	private static String from = "parkingmang@gmail.com";
	private static String password = "Park1234";
	private static String host = "smtp.gmail.com";
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static void Send(String to, String subject, String messageText) throws MessagingException{
		LOGGER.info("Create a session object");
		
		//Get the session object  
	      Properties properties = System.getProperties();  
	      properties.setProperty("mail.smtp.host", host);  
	      Session session = Session.getDefaultInstance(properties); 
	      
	     //compose the message  
	      try{  
	    	 LOGGER.info("Create message...");
	         MimeMessage message = new MimeMessage(session);  
	         message.setFrom(new InternetAddress(from));  
	         message.addRecipient(RecipientType.TO ,new InternetAddress(to));  
	         message.setSubject(subject);  
	         message.setText(messageText);  
	  
	         // Send message  
	         LOGGER.info("send message...");
	         Transport transport = session.getTransport("smtps");
	         transport.connect(host, from, password);
	         transport.sendMessage(message, message.getAllRecipients());
	         LOGGER.info("Message sent successfully...");
	      } catch (MessagingException mex) {
	    	  LOGGER.severe("Message cannot be sent: " + mex.getMessage());
	    	  throw mex;
	    	  }  
	}
	
	public static void IlligalParking(String to) throws MessagingException {
		String messageText = "Hello, \n This is to inform you that your parking in an illigal spot. \n Please move your car ASAP";
		EmailNotification.Send(to, "Illigal parking noticiation", messageText);
	}
	
	public static void ParkingConfirmation(String to, String slotName, String startTime, String endTime, double price) throws MessagingException {
		String messageText = "Hello, \n This is to inform you that your parking in slot "+slotName+" from " +startTime + " to "+endTime+" has been approved.\n"
				+ "The price for the parking slot was " + price + " NIS. \n Regars";
		EmailNotification.Send(to, "Parking approval", messageText);
	}
	
	public static void ReviewReminder(String to, String slotName) throws MessagingException{
		String messageText = "Hello, \n This is to remind you to fill out a review on last prking experience on slot "+slotName+". \n Please loging to the system and fill the short review. \n Regards";
		EmailNotification.Send(to, "Parking review reminder", messageText);
	}
	
	public static void SuccessfulRegistration(String to) throws MessagingException{
		String messageText = "Hello, \n Thank you for registering to the Parking Experience system! \n Hope to see you soon \n Regards";
		EmailNotification.Send(to, "Successful Registration to the Parking System", messageText);
	}
	
	public static void PasswordReset(String to, String pwd) throws MessagingException{
		String messageText = "Hello, \n Your password is: " + pwd + " . \n Regards";
		EmailNotification.Send(to, "Password reset", messageText);
	}

}
