package logic;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;

import data.management.DBManager;

public class orderTest {

	@Test
	public void setUpTest() throws ParseException, InterruptedException {
		DBManager.initialize();
		try{
			// create new order
			Date startTime =new Date();
			Calendar cal = Calendar.getInstance(); // creates calendar
		    cal.setTime(startTime); // sets calendar time/date
		    cal.add(Calendar.HOUR_OF_DAY, 2);
			Date endTime =cal.getTime();
			System.out.println(startTime);
			System.out.println(endTime);
			new Order("3333333", "123", startTime, endTime);
			Thread.sleep(6000);

		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
}
