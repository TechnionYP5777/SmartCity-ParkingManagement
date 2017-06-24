package logic;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
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
	public void setUpTest() {
		DatabaseManager dbm = Mockito.mock(DatabaseManager.class);
		Date startTime = new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	    @SuppressWarnings("deprecation")
		int hour = startTime.getHours();
	    System.out.println(hour);
		String id="123" + onlyDate + "3";
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 3);
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", hour+2);
		keys.put("id", id);
		try{
			// create new Stats
			new Stats( "123", startTime, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(3)).insertObject("Stats", keys, fields);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetters(){
		DatabaseManager dbm = Mockito.mock(DatabaseManager.class);
		Date startTime = new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	    @SuppressWarnings("deprecation")
		int hour = startTime.getHours();
	    System.out.println(hour);
	    String slotId = "123";
		String id = slotId + onlyDate + "0";
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 3);
		fields.put("slotId", slotId);
		fields.put("date", onlyDate);
		fields.put("hour", hour+2);
		fields.put("id", id);
		keys.put("id", id);
		Mockito.when(dbm.getObjectFieldsByKey("Stats", keys)).thenReturn(fields);
		Stats s;
		try{
			// create new Stats
			s = new Stats(id, dbm);
			
			// test getId
			Assert.assertEquals(id, s.getId());
			
			// test getSlotId
			Assert.assertEquals(slotId, s.getSlotId());
			
			// test getDate
			Assert.assertEquals(onlyDate, s.getDate());
			
			// test getHour
			Assert.assertEquals(hour+2, s.getHour());
			
			// test getHoursAmount
			Assert.assertEquals(3, s.getHoursAmount());
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}	
	}
}
