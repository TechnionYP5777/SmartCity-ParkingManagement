package logic;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;

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
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,30);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date startTime = cal.getTime();
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	   
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 8);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 6);
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
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,15);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date startTime = cal.getTime();
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
	    
	    cal.add(Calendar.HOUR_OF_DAY, 2);
	    cal.add(Calendar.MINUTE, 15);
		Date endTime =cal.getTime();
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 9);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 5);
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
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,15);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		Date endTime =cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 0);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 5);
		keyVal.put("id", id);
		try{
			// create new order
			new Order("3333334", "123", date, endTime,dbm);
			Mockito.verify(dbm, Mockito.times(1)).initialize();
			Mockito.verify(dbm, Mockito.times(0)).insertObject("Order", keyVal, fields);
		} catch (final Exception ¢) {
			¢.printStackTrace();
		}
	}
	
	@Test
	public void checkQuarter3() throws ParseException, InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,45);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		String id="3333334" + onlyDate + "1";
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("hoursAmount", 8);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 7);
		keyVal.put("id", id);
		try{
			// create new order
			new Order("3333334", "123", date, endTime,dbm);
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
	
	@Test
	public void checkSetHour(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY,11);
		cal2.set(Calendar.MINUTE,0);
		cal2.set(Calendar.SECOND,0);
		cal2.set(Calendar.MILLISECOND,0);
		Date date = cal2.getTime();
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		cal2.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal2.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 44);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Calendar cal3 = Calendar.getInstance(); // creates calendar
		    cal3.setTime(date); // sets calendar time/date
		    cal3.add(Calendar.HOUR_OF_DAY, 2);
		    new Order("1",dbm).setStartTime(cal3.getTime());
		    fields.put("hour", 52);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkSetHour2(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY,11);
		cal2.set(Calendar.MINUTE,0);
		cal2.set(Calendar.SECOND,0);
		cal2.set(Calendar.MILLISECOND,0);
		Date date = cal2.getTime();
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal2.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 44);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Calendar cal3 = Calendar.getInstance(); // creates calendar
		    cal3.setTime(date); // sets calendar time/date
		    cal3.add(Calendar.MINUTE, 15);
		    new Order("1",dbm).setStartTime(cal3.getTime());
		    fields.put("hour", 45);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkSetHour3(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY,11);
		cal2.set(Calendar.MINUTE,0);
		cal2.set(Calendar.SECOND,0);
		cal2.set(Calendar.MILLISECOND,0);
		Date date = cal2.getTime();
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal2.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 44);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Calendar cal3 = Calendar.getInstance(); // creates calendar
		    cal3.setTime(date); // sets calendar time/date
		    cal3.add(Calendar.MINUTE, 30);
		    new Order("1",dbm).setStartTime(cal3.getTime());
		    fields.put("hour", 46);
			Mockito.verify(dbm,Mockito.times(1)).update("Order", keys, fields);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void checkSetHour4(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.HOUR_OF_DAY,11);
		cal2.set(Calendar.MINUTE,0);
		cal2.set(Calendar.SECOND,0);
		cal2.set(Calendar.MILLISECOND,0);
		Date date = cal2.getTime();
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal2.getTime()); 
		keys.put("id", "1");
		fields.put("hoursAmount", 3);
		fields.put("driverId", "3333334");
		fields.put("slotId", "123");
		fields.put("date", onlyDate);
		fields.put("hour", 44);
		fields.put("id","1");
		Mockito.when(dbm.getObjectFieldsByKey("Order", keys)).thenReturn(fields);
		try{
			Calendar cal3 = Calendar.getInstance(); // creates calendar
		    cal3.setTime(date); // sets calendar time/date
		    cal3.add(Calendar.MINUTE, 45);
		    new Order("1",dbm).setStartTime(cal3.getTime());
		    fields.put("hour", 47);
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
	
	@Test
	public void checkAvaliability() throws InterruptedException{
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		List<ParseObject> orders = new ArrayList<>();
		ParseObject p = new ParseObject("Order");
		Date startTime =new Date();		
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(startTime); 
	    @SuppressWarnings("deprecation")
		int curHour= cal.getTime().getHours();
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		p.put("hoursAmount", 3);
		p.put("driverId", "3333334");
		p.put("slotId", "123");
		p.put("date", onlyDate);
		p.put("hour", curHour);
		p.put("id","1");
		orders.add(p);
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
		Assert.assertFalse((new Order(d).checkAvaliablity("123", startTime, 1)));
	}
	
	@Test
	public void checkAvaliability2() throws InterruptedException{
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		List<ParseObject> orders = new ArrayList<>();
		ParseObject p = new ParseObject("Order");
		Date startTime =new Date();		
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(startTime); 
	    @SuppressWarnings("deprecation")
		int curHour= cal.getTime().getHours();
	    cal.add(Calendar.HOUR_OF_DAY, 3);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		p.put("hoursAmount", 3);
		p.put("driverId", "3333334");
		p.put("slotId", "123");
		p.put("date", onlyDate);
		p.put("hour", curHour);
		p.put("id","1");
		orders.add(p);
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
		Assert.assertFalse((new Order(d).checkAvaliablity("123", startTime, 1)));
	}
	
	@Test
	public void checkAvaliability3() throws InterruptedException{
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		List<ParseObject> orders = new ArrayList<>();
		ParseObject p = new ParseObject("Order");
		Date startTime =new Date();		
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(startTime); 
	    @SuppressWarnings("deprecation")
		int curHour= cal.getTime().getHours();
	    cal.add(Calendar.HOUR_OF_DAY, 1);
	    System.out.println(curHour);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		p.put("hoursAmount", 3);
		p.put("driverId", "3333334");
		p.put("slotId", "123");
		p.put("date", onlyDate);
		p.put("hour", curHour);
		p.put("id","1");
		orders.add(p);
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
		Assert.assertTrue((new Order(d).checkAvaliablity("123", cal.getTime(), 1)));
	}
	
	@Test
	public void checkAvaliability4() throws InterruptedException{
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		List<ParseObject> orders = new ArrayList<>();
		ParseObject p = new ParseObject("Order");
		Date startTime =new Date();		
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(startTime); 
	    @SuppressWarnings("deprecation")
		int curHour= cal.getTime().getHours();
	    cal.add(Calendar.HOUR_OF_DAY, 1);
	    System.out.println(curHour);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime()); 
		p.put("hoursAmount", 3);
		p.put("driverId", "3333334");
		p.put("slotId", "123");
		p.put("date", onlyDate);
		p.put("hour", curHour);
		p.put("id","1");
		orders.add(p);
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
		Assert.assertTrue((new Order(d).checkAvaliablity("124", cal.getTime(), 1)));
	}
	
	@Test
	public void checkGetHourInQuarter(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		Assert.assertTrue(4==new Order(d).getHourInQuarter(date));
	}
	
	@Test
	public void checkGetHourInQuarter2(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,15);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		Assert.assertTrue(5==new Order(d).getHourInQuarter(date));
	}
	
	@Test
	public void checkGetHourInQuarter3(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,30);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		Assert.assertTrue(6==new Order(d).getHourInQuarter(date));
	}
	
	@Test
	public void checkGetHourInQuarter4(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,1);
		cal.set(Calendar.MINUTE,45);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		Assert.assertTrue(7==new Order(d).getHourInQuarter(date));
	}
	
	@Test
	public void checkGetHourInQuarter5(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,11);
		cal.set(Calendar.MINUTE,45);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		Assert.assertTrue(47==new Order(d).getHourInQuarter(date));
	}
	
	@Test
	public void checkGetHourInQuarter6(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,11);
		cal.set(Calendar.MINUTE,45);
		cal.set(Calendar.SECOND,1);
		cal.set(Calendar.MILLISECOND,0);
		Date date = cal.getTime();
		Assert.assertTrue(47==new Order(d).getHourInQuarter(date));
	}
	
	@Test
	public void checkGetHourInQuarter7(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,11);
		cal.set(Calendar.MINUTE,45);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND,1);
		Date date = cal.getTime();
		Assert.assertTrue(47==new Order(d).getHourInQuarter(date));
	}
}
