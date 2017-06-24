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
	public void IlligalMesTest(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.IlligalParking(to);
		} catch (MessagingException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void ParkingApprovalTest(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.ParkingConfirmation(to, "Slot", LocalDateTime.now().toString(), LocalDateTime.now().toString() );
		} catch (MessagingException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void ReviewReminderTest(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.ReviewReminder(to, "Slot");
		} catch (MessagingException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void SuccessfulRegistrationTest(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.SuccessfulRegistration(to);
		} catch (MessagingException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void PasswordResetTest(){
		String to = "parkingmang@gmail.com";
		try {
			EmailNotification.PasswordReset(to, "123456789");
		} catch (MessagingException e) {
			Assert.fail();
		}
	}
}
