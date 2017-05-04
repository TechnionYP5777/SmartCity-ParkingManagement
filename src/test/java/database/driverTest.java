package database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;

import data.management.DBManager;
import data.members.Driver;


public class driverTest {
	/**
	 * @author Inbal Matityahu
	 * @since 5/2/16 This class will test Driver class
	 */
	
	@Before
	public void setUpTest() throws ParseException, InterruptedException {
		DBManager.initialize();
		try{
			// create new driver
			 new Driver("333333333", "stam@gmail.com", "1234567", "1234567");
			Thread.sleep(6000);

		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetId(){
		try{
			Assert.assertEquals(new Driver("333333333").getId(), "333333333");
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	

	@Test
	public void testGetEmail(){
		try{
			Assert.assertEquals(new Driver("333333333").getEmail(), "stam@gmail.com");
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	

	@Test
	public void testGetCarId(){
		try{
			Assert.assertEquals(new Driver("333333333").getCarId(), "1234567");
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	

	@Test
	public void testGetPassword(){
		try{
			Assert.assertEquals(new Driver("333333333").getPassword(), "1234567");
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	

	@Test
	public void checkObject(){
		DBManager.initialize();
		try{
			Driver d = new Driver("333333333");
			Thread.sleep(6000);	
			Assert.assertEquals(d.getId(), "333333333");
			Assert.assertEquals(d.getEmail(), "stam@gmail.com");
			Assert.assertEquals(d.getCarId(), "1234567");
			Assert.assertEquals(d.getPassword(), "1234567");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@After
	public void finishTest() throws ParseException, InterruptedException {
		//delete object
		new Driver("333333333").removeDriverFromDB();
		Thread.sleep(6000);
	}
	
}
