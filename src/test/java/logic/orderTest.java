package logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;

import data.management.DBManager;

public class orderTest {
	
	/**
	 * @author Inbal Matityahu
	 * @since 8/5/16 This is a testing class to Order class which represent an order for renting a parking slot
	 */
	
	private String startDate;
	private String driverId;
	private int hour;
	
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
			new Order("3333334", "123", startTime, endTime);
			Thread.sleep(5000);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    String onlyDate = format1.format(cal.getTime());   
			String id="3333334" + onlyDate + "1";
			new Order(id).removeOrderFromDB();
			Thread.sleep(5000);

		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Before
	public void startTests() throws ParseException{
		// create new order
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(startTime); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime());    
		this.startDate = onlyDate;
		this.driverId= "3333333";
		this.hour=startTime.getHours();
		try {
			new Order("3333333", "123", startTime, endTime);
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void finishTests() throws ParseException {
		String s = this.driverId+this.startDate+"1";
		try {			
			new Order(s).removeOrderFromDB();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkGetOrderId() throws ParseException, InterruptedException{
		String s = this.driverId+this.startDate+"1";
		Assert.assertEquals(s , new Order(s).getId());
		Thread.sleep(6000);
	}
	
	@Test
	public void checkGetSlotId() throws ParseException, InterruptedException{
		String s = this.driverId+this.startDate+"1";
		Assert.assertEquals("123", new Order(s).getSlotId());
		Thread.sleep(6000);
	}
	
	@Test
	public void checkGetDriverId() throws ParseException, InterruptedException{
		String s = this.driverId+this.startDate+"1";
		Assert.assertEquals("3333333", new Order(s).getDriverId());
		Thread.sleep(6000);
	}
	
	@Test
	public void checkGetDate() throws ParseException, InterruptedException{
		String s = this.driverId+this.startDate+"1";      
		Assert.assertEquals(this.startDate, new Order(s).getDate().toString());
		Thread.sleep(6000);
	}
	
	@Test
	public void checkGetHour() throws ParseException, InterruptedException{
		String s = this.driverId+this.startDate+"1";
		Assert.assertEquals(hour, new Order(s).getHour());
		Thread.sleep(6000);
	}
	
	@Test
	public void checkGetAmountHours() throws ParseException, InterruptedException{
		String s = this.driverId+this.startDate+"1";
		Assert.assertEquals(3, new Order(s).getHoursAmount());
		Thread.sleep(6000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfDriverNull() throws ParseException, InterruptedException{
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		new Order(null, "123", startTime, endTime);
		Thread.sleep(6000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfSlotNull() throws ParseException, InterruptedException{
		Date startTime =new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, 2);
		Date endTime =cal.getTime();
		new Order("3333333", null, startTime, endTime);
		Thread.sleep(6000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfStartDateNull() throws ParseException, InterruptedException{
		new Order("3333333", "123", null, new Date());
		Thread.sleep(6000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIfEndDateNull() throws ParseException, InterruptedException{
		new Order("3333333", "123", new Date(), null);
		Thread.sleep(6000);
	}
	
	@Test
	public void checkODiff(){
		DBManager.initialize();
		try{
			
			Date startTime = new Date(), endTime = startTime;
			new Order("3333334", "123", startTime, endTime);
			Thread.sleep(5000);

		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void check1Diff(){
		DBManager.initialize();
		try{
			
			// create new order
			Date startTime =new Date();
			Calendar cal = Calendar.getInstance(); // creates calendar
		    cal.setTime(startTime); // sets calendar time/date
		    cal.add(Calendar.HOUR_OF_DAY, 1);
			Date endTime =cal.getTime();
			new Order("3333334", "123", startTime, endTime);
			Thread.sleep(10000);
			System.out.println("1");
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    String onlyDate = format1.format(cal.getTime());   
			String id="3333334" + onlyDate + "1";		
			new Order(id).removeOrderFromDB();
			System.out.println("2");

			Thread.sleep(6000);

		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void checkCancelOrder(){
		DBManager.initialize();
		try{
			// create new order
			Date startTime =new Date();
			Calendar cal = Calendar.getInstance(); // creates calendar
			cal.setTime(startTime); // sets calendar time/date
		    cal.add(Calendar.HOUR_OF_DAY, 1);
			Date endTime =cal.getTime();
			new Order("3333334", "123", startTime, endTime);
			Thread.sleep(10000);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		    String onlyDate = format1.format(cal.getTime());   
			String id="3333334" + onlyDate + "1";			
			Thread.sleep(6000);
			
			Order o = new Order(id);
			Assert.assertEquals(id, (o.getId()));
			Assert.assertEquals("3333334", (o.getDriverId()));
			Assert.assertEquals("123", (o.getSlotId()));
			// cancel order
			o.CancelOrder();
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
}
