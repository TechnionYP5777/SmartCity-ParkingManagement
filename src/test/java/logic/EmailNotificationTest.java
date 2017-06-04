package logic;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import util.Log;

/**
 * @author Toma
 *
 */

public class EmailNotificationTest {
	@BeforeClass
	public static void classSetUp(){
		try {		
			Log.setup();
		} catch (IOException e) {
			System.out.println("Could not set up the logger");
		}
	}
	
	@Test
	public void IlligalMes(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.IlligalParking(to);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			Assert.fail();
		}
	}
	
	@Test
	public void ParkingApproval(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.ParkingConfirmation(to, "Slot", LocalDateTime.now().toString(), LocalDateTime.now().toString() );
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			Assert.fail();
		}
	}
	
	@Test
	public void ReviewReminder(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.ReviewReminder(to, "Slot");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			Assert.fail();
		}
	}
}
