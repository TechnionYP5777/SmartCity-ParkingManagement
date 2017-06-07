package logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseException;

import data.management.DatabaseManager;

public class orderTest {
	
	/**
	 * @author Inbal Matityahu
	 * @since 8/5/16 This is a testing class to Order class which represent an order for renting a parking slot
	 */
	
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
		String id="3333334" + onlyDate + "3";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", hour+2);
		keyVal.put("id", id);
		try{
			// create new order
			new Order("3333334", "123", startTime, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(3)).insertObject("Order", keyVal, fields);
		} catch (final Exception ¢) {
			¢.printStackTrace();
		}
	}
	
	@Test
	public void checkGetOrderId() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333333"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Order(s, dbm).getId(), s);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void checkGetSlotId() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333333"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Order(s, dbm).getSlotId(), "123");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void checkGetDriverId() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Order(s, dbm).getDriverId(), "3333334");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void checkGetDate() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Order(s, dbm).getDate(), onlyDate);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void checkGetHour() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Order(s, dbm).getHour(), 2);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void checkGetAmountHours() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Order(s, dbm).getHoursAmount(), 3);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfDriverNull() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			new Order(null, "123", startTime, endTime,dbm);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);

		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfSlotNull() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			new Order("3333333", null, startTime, endTime,dbm);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);

		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfStartDateNull() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			new Order("3333333", "123", null, endTime,dbm);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);

		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfEndDateNull() throws ParseException, InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "333333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			new Order("3333333", "123", startTime, null,dbm);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);

		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
