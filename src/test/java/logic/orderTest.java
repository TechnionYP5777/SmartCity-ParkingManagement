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
import org.parse4j.ParseException;

import data.management.DatabaseManager;
import util.Log;

public class orderTest {
	
	/**
	 * @author Inbal Matityahu
	 * @since 8/5/16 This is a testing class to Order class which represent an order for renting a parking slot
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
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 8);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", hour);
		keyVal.put("id", id);
		try{
			// create new order
			new Order("3333334", "123", startTime, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(1)).insertObject("Order", keyVal, fields);
		} catch (final Exception ¢) {
			¢.printStackTrace();
		}
	}
	
	@Test
	public void checkQuarter() throws ParseException, InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(startTime); 
	    cal.add(Calendar.HOUR_OF_DAY, 2);
	    cal.add(Calendar.MINUTE, 15);
		Date endTime =cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	    @SuppressWarnings("deprecation")
		int hour = startTime.getHours();
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 9);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", hour);
		keyVal.put("id", id);
		try{
			// create new order
			new Order("3333334", "123", startTime, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(1)).insertObject("Order", keyVal, fields);
		} catch (final Exception ¢) {
			¢.printStackTrace();
		}
	}
	
	@Test
	public void checkQuarter2() throws ParseException, InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(startTime); 
	    cal.add(Calendar.MINUTE, 15);
		Date endTime =cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	    @SuppressWarnings("deprecation")
		int hour = startTime.getHours();
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 1);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", hour);
		keyVal.put("id", id);
		try{
			// create new order
			new Order("3333334", "123", startTime, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(1)).insertObject("Order", keyVal, fields);
		} catch (final Exception ¢) {
			¢.printStackTrace();
		}
	}
	
	@Test
	public void checkQuarter3() throws ParseException, InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(startTime); 
	    cal.add(Calendar.MINUTE, 45);
		Date endTime =cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	    @SuppressWarnings("deprecation")
		int hour = startTime.getHours();
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", hour);
		keyVal.put("id", id);
		try{
			// create new order
			new Order("3333334", "123", startTime, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(1)).insertObject("Order", keyVal, fields);
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
	
	@Test
	public void checkCancelOrder(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String s = "3333334"+onlyDate+"1";
		keys.put("id", s);
		fields.put("hoursAmount", 1);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 14);
		fields.put("id",s);
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			new Order(s,dbm).CancelOrder();;
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			s = "3333334"+onlyDate+"1";
			System.out.println(s);
			//keys.put("id", s);

			//fields.put("id",s);
			//fields.put("hour", 15);
//			fields.put("hoursAmount", 2);
//			fields.put("driverId", "3333334");
//			fields.put("slotId", "123");
//			fields.put("date", onlyDate);
			Mockito.verify(dbm, Mockito.times(1)).deleteObject("Order", fields);

		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkSetDriverId(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			new Order("1",dbm).setDriverId("3333335");
			fields.put("driverId", "3333335");
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkSetSlotId(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			new Order("1",dbm).setSlotId("122");;
			fields.put("slotId", "122");
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkSetDate(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("id", 1);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Date newDate=new Date();
			new Order("1",dbm).setDate(newDate);
			Calendar cal2 = Calendar.getInstance(); // creates calendar
		    cal2.setTime(newDate); // sets calendar time/date
		    cal2.add(Calendar.HOUR_OF_DAY, 2);
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
		    String onlyDate2 = format2.format(cal.getTime()); 
			fields.put("date", onlyDate2);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void checkSetHour(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", new Date().getHours());
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Calendar cal2 = Calendar.getInstance(); // creates calendar
		    cal.setTime(startTime); // sets calendar time/date
		    cal.add(Calendar.HOUR_OF_DAY, 2);
		    new Order("1",dbm).setStartTime(cal2.getTime());
		    fields.put("hoursAmount", 3);
		    fields.put("hour", cal2.getTime().getHours());
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkSetHoursAmount(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 2);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
		    new Order("1",dbm).setHoursAmount(4);
		    fields.put("hoursAmount", 4);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
