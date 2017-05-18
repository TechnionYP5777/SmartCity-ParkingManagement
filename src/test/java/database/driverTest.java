package database;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;

import data.management.DatabaseManager;
import data.members.Driver;
import org.mockito.*;
public class driverTest {
	/**
	 * @author Inbal Matityahu
	 * @since 5/2/16 This class will test Driver class
	 */
	
	@Test
	public void setUpTest() throws ParseException, InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keyVal = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		fields.put("email", "stam@gmail.com");
		fields.put("carId", "1234567");
		fields.put("password", "1234567");
		
		keyVal.put("id", "333333333");
		try{
			// create new driver
			 new Driver("333333333", "stam@gmail.com", "1234567", "1234567",dbm);
			 Mockito.verify(dbm, Mockito.times(1)).initialize();
			 Mockito.verify(dbm, Mockito.times(1)).insertObject("Driver", keyVal, fields);
		} catch (final Exception ¢) {
			¢.printStackTrace();
		}
				
	}
	
	@Test
	public void testGetId(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Driver("333333333", dbm).getId(), "333333333");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetEmail(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam@gmail.com");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Driver("333333333",dbm).getEmail(), "stam@gmail.com");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetCarId(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam@gmail.com");
		fields.put("carId", "1234567");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Driver("333333333",dbm).getCarId(), "1234567");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetPassword(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam@gmail.com");
		fields.put("carId", "1234567");
		fields.put("password", "1234567");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			Assert.assertEquals(new Driver("333333333",dbm).getPassword(), "1234567");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void checkObject(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam@gmail.com");
		fields.put("carId", "1234567");
		fields.put("password", "1234567");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			Driver d = new Driver("333333333",dbm);
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
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333332");
		fields.put("id", "333333332");
		fields.put("email", "stam@gmail.com");
		fields.put("carId", "1234567");
		fields.put("password", "1234567");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		
		try{
			new Driver("333333333",dbm).setId("333333332");
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetId2() throws InterruptedException{
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333334");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(new HashMap<>());
		try{
			
			new Driver("333333333",dbm).setId("333333334");
			Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(keys);
			String s= new Driver("333333334",dbm).getId();
			Assert.assertEquals("333333334",s);
			keys.put("id", "333333333");
			Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(new HashMap<>());
			new Driver("333333334",dbm).setId("333333333");
		}catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSetEmail(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam2@gmail.com");
		fields.put("carId", "1234567");
		fields.put("password", "1234567");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			new Driver("333333333",dbm).setEmail("stam2@gmail.com");
			Assert.assertEquals("stam2@gmail.com",new Driver("333333333",dbm).getEmail());
			new Driver("333333333",dbm).setEmail("stam@gmail.com");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetCarId(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam2@gmail.com");
		fields.put("carId", "1234568");
		fields.put("password", "1234567");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			new Driver("333333333",dbm).setCarId("1234568");
			Driver d =new Driver("333333333",dbm);
			Assert.assertEquals("1234568",d.getCarId());
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetPassword(){
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam2@gmail.com");
		fields.put("carId", "1234568");
		fields.put("password", "12345678");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		try{
			new Driver("333333333",dbm).setPassword("12345678");
			Assert.assertEquals("12345678",new Driver("333333333",dbm).getPassword());
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIdNull() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver(null, "stam@gmail.com", "1234567", "1234567",dbm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkEmailNull() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333", null, "1234567", "1234567",dbm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdNull() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333", "stam@gmail.com", null, "1234567",dbm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkPasswordNull() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333", "stam@gmail.com", "1234567", null,dbm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkDuplicatedId() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333", "stam@gmail.com", "1234567", null,dbm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkIdTest() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setId(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void checkIdTest2() throws InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam2@gmail.com");
		fields.put("carId", "1234568");
		fields.put("password", "12345678");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setId("333333333");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdTest() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setCarId(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdTest2() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setCarId("123");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkCarIdTest3() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setCarId("12345678");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkPasswordTest() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setPassword(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkPasswordTest2() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setPassword("12345678910");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setEmail(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest2() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setEmail("123");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest3() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setEmail("123.com");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEmailTest4() {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		dbm.initialize();
		try {
			new Driver("333333333",dbm).setEmail("123@com");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void finishTest() throws ParseException, InterruptedException {
		DatabaseManager dbm= Mockito.mock(DatabaseManager.class);
		Map<String, Object> keys = new HashMap<>();
		Map<String, Object> fields = new HashMap<>();
		keys.put("id", "333333333");
		fields.put("id", "333333333");
		fields.put("email", "stam@gmail.com");
		fields.put("carId", "1234567");
		fields.put("password", "1234567");
		Mockito.when(dbm.getObjectFieldsByKey("Driver", keys)).thenReturn(fields);
		
		new Driver("333333333",dbm).removeDriverFromDB();
		Mockito.verify(dbm, Mockito.times(2)).initialize();
		Mockito.verify(dbm, Mockito.times(1)).deleteObject("Driver", fields);
	}

}
