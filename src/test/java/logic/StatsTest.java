package logic;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseException;

import data.management.DatabaseManager;
import util.Log;

public class StatsTest {
	/**
	 * @author Toma
	 * @since 11/06/17 This is a testing class to Stats class 
	 */
	@BeforeClass
	public static void classSetUp(){
		try {		
			Log.setup();
		} catch (IOException e) {
			System.out.println("Could not set up the logger");
		}
	}
	
	@Test
	public void setUpTest() throws ParseException, InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	    @SuppressWarnings("deprecation")
		int hour = startTime.getHours();
	    System.out.println(hour);
		String id="123" + onlyDate + "3";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 3);
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", hour+2);
		keyVal.put("id", id);
		try{
			// create new Stats
			new Stats( "123", startTime, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(3)).insertObject("Stats", keyVal, fields);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
