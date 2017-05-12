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
			
			new Driver("333333332", "stam@gmail.com", "1234567", "1234567");
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
	

	@Test(expected = IllegalArgumentException.class)
	public void testSetId() throws InterruptedException{
		DBManager.initialize();
		try{
			new Driver("333333333").setId("333333332");
			Thread.sleep(6000);
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
//	//TODO::
//	@Test
//	public void testSetId2() throws InterruptedException{
//		DBManager.initialize();
//		try{
//			
//			new Driver("333333333").setId("333333334");
//			Thread.sleep(6000);
//			System.out.println("1");
//			String s= new Driver("333333334").getId();
//			Thread.sleep(6000);
//			Assert.assertEquals("333333334",s);
//			System.out.println("4");
//		}catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
	
	@Test
	public void testSetEmail(){
		try{
			new Driver("333333333").setEmail("stam2@gmail.com");
			Thread.sleep(6000);
			Assert.assertEquals("stam2@gmail.com",new Driver("333333333").getEmail());
			Thread.sleep(6000);
			new Driver("333333333").setEmail("stam@gmail.com");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetCarId(){
		try{
			new Driver("333333333").setCarId("1234568");
			Thread.sleep(6000);
			Assert.assertEquals("1234568",new Driver("333333333").getCarId());
			Thread.sleep(6000);
			new Driver("333333333").setCarId("1234567");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetPassword(){
		try{
			new Driver("333333333").setPassword("12345678");
			Thread.sleep(6000);
			Assert.assertEquals("12345678",new Driver("333333333").getPassword());
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIdNull() {
		DBManager.initialize();
		try {
			new Driver(null, "stam@gmail.com", "1234567", "1234567");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkEmailNull() {
		DBManager.initialize();
		try {
			new Driver("333333333", null, "1234567", "1234567");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdNull() {
		DBManager.initialize();
		try {
			new Driver("333333333", "stam@gmail.com", null, "1234567");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkPasswordNull() {
		DBManager.initialize();
		try {
			new Driver("333333333", "stam@gmail.com", "1234567", null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	

	@Test(expected = IllegalArgumentException.class)
	public void checkDuplicatedId() {
		DBManager.initialize();
		try {
			new Driver("333333333", "stam@gmail.com", "1234567", null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIdTest() {
		DBManager.initialize();
		try {
			new Driver("333333333").setId(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkIdTest2() throws InterruptedException {
		DBManager.initialize();
		try {
			new Driver("333333333").setId("333333333");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdTest() {
		DBManager.initialize();
		try {
			new Driver("333333333").setCarId(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdTest2() {
		DBManager.initialize();
		try {
			new Driver("333333333").setCarId("123");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdTest3() {
		DBManager.initialize();
		try {
			new Driver("333333333").setCarId("12345678");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkPasswordTest() {
		DBManager.initialize();
		try {
			new Driver("333333333").setPassword(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkPasswordTest2() {
		DBManager.initialize();
		try {
			new Driver("333333333").setPassword("12345678910");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest() {
		DBManager.initialize();
		try {
			new Driver("333333333").setEmail(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest2() {
		DBManager.initialize();
		try {
			new Driver("333333333").setEmail("123");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest3() {
		DBManager.initialize();
		try {
			new Driver("333333333").setEmail("123.com");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest4() {
		DBManager.initialize();
		try {
			new Driver("333333333").setEmail("123@com");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void finishTest() throws ParseException, InterruptedException {
		//delete object
		new Driver("333333333").removeDriverFromDB();
		Thread.sleep(6000);
		
		new Driver("333333332").removeDriverFromDB();
		Thread.sleep(6000);
	}
	
}
