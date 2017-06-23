package database;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.internal.verification.Times;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;

import data.management.DBManager;
import data.management.DatabaseManager;
import data.members.ParkingSlot;
import data.members.StickersColor;
import util.Log;
import data.members.ParkingSlotStatus;
import data.members.Area;
import data.members.MapLocation;

public class parkingSlotTest {
	
	@BeforeClass
	public static void classSetUp(){
		try {		
			Log.setup();
		} catch (IOException e) {
			System.out.println("Could not set up the logger");
		}
	}
	
	private DatabaseManager setMock(){
		DatabaseManager dbm = Mockito.mock(DatabaseManager.class);
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("name", "testParkingSlot1");
		
		Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
		return dbm;
	}
	
	@Test
	public void addAndRemove(){
		DatabaseManager dbm = Mockito.mock(DatabaseManager.class);
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot2");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		fields.put("name", "testParkingSlot2");
		
		Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
		try{
			// create new slot
			new ParkingSlot("testParkingSlot2", ParkingSlotStatus.FREE, StickersColor.GREEN,
					StickersColor.GREEN, new MapLocation(32.123, 32.123), new Date(), Area.TAUB,10,2,dbm);
			new ParkingSlot("testParkingSlot2",dbm).removeParkingSlotFromDB();
	
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void check(){
		DatabaseManager dbm = Mockito.mock(DatabaseManager.class);
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "first");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.BLUE.ordinal());
		fields.put("defaultColor", StickersColor.BLUE.ordinal());
		fields.put("location", new ParseGeoPoint(3.12, 3.12));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("name", "first");
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
		
		try{
			new ParkingSlot("first", ParkingSlotStatus.FREE, StickersColor.BLUE, StickersColor.BLUE,
					new MapLocation(3.12, 3.12),new Date(), Area.TAUB,10,2,dbm);
			ParkingSlot p = new ParkingSlot("first",dbm);	
			Assert.assertEquals(p.getName(), "first");
			Assert.assertEquals(p.getStatus(), ParkingSlotStatus.FREE);
			Assert.assertEquals(p.getRank(), StickersColor.BLUE);
			Assert.assertEquals(p.getDefaultColor(), StickersColor.BLUE);
			Assert.assertEquals(p.getArea(), Area.TAUB);
			Calendar cal = Calendar.getInstance();
            cal.setTime(p.getEndTime());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
			Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
			Assert.assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
			Assert.assertEquals(p.getLocation().getLat(), 3.12, 0);
			Assert.assertEquals(p.getLocation().getLon(), 3.12, 0);
			new ParkingSlot("first",dbm).removeParkingSlotFromDB();
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	/*TODO : litle problem with the test because the parsegeopoint 
		created in the class and cannot be passed in the mock process*/
	@Test
	public void setUpTest() throws ParseException, InterruptedException {
		DatabaseManager dbm = Mockito.mock(DatabaseManager.class);
		
		try{
			// create new slot
			new ParkingSlot("testParkingSlot1", ParkingSlotStatus.FREE, StickersColor.GREEN,
					StickersColor.GREEN, new MapLocation(32.123, 32.123), new Date(), Area.TAUB,10,2,dbm);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetName(){
		DatabaseManager dbm = setMock();
		
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getName(), "testParkingSlot1");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetStatus(){
		DatabaseManager dbm = setMock();
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getStatus(), ParkingSlotStatus.FREE);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetArea(){
		DatabaseManager dbm = setMock();
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getArea(), Area.TAUB);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetRank(){
		DatabaseManager dbm = setMock();
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getRank(), StickersColor.GREEN);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetLocation(){
		DatabaseManager dbm = setMock();
		try{
			ParkingSlot p =new ParkingSlot("testParkingSlot1",dbm);
			Assert.assertEquals(p.getLocation().getLat(), 32.123, 0);
			Assert.assertEquals(p.getLocation().getLat(), 32.123, 0);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetDefaultColor(){
		DatabaseManager dbm = setMock();
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getDefaultColor(), StickersColor.GREEN);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetEndTime(){
		DatabaseManager dbm = setMock();
		try{
			ParkingSlot p =new ParkingSlot("testParkingSlot1",dbm);
			Calendar cal = Calendar.getInstance();
            cal.setTime(p.getEndTime());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
            Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
			Assert.assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetObject(){
		DatabaseManager dbm = setMock();
		try{
			ParkingSlot p = new ParkingSlot("testParkingSlot1",dbm);
			Assert.assertEquals(p.getName(), "testParkingSlot1");
			Assert.assertEquals(p.getStatus(), ParkingSlotStatus.FREE);
			Assert.assertEquals(p.getRank(), StickersColor.GREEN);
			Assert.assertEquals(p.getArea(), Area.TAUB);
			Assert.assertEquals(p.getDefaultColor(), StickersColor.GREEN);
			Calendar cal = Calendar.getInstance();
            cal.setTime(p.getEndTime());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
            Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
			Assert.assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
			Assert.assertEquals(p.getLocation().getLat(), 32.123, 0);
			Assert.assertEquals(p.getLocation().getLon(), 32.123, 0);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetName(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("name", "testParkingSlot1");
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).setName("testParkingSlot10");
			
			fields.put("name", "testParkingSlot10");
			keys.put("name", "testParkingSlot10");
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			
			Assert.assertEquals(new ParkingSlot("testParkingSlot10",dbm).getName(), "testParkingSlot10");
			
			new ParkingSlot("testParkingSlot10",dbm).setName("testParkingSlot1");
			fields.put("name", "testParkingSlot1");
			keys.put("name", "testParkingSlot1");
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getName(), "testParkingSlot1");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetStatus(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("name", "testParkingSlot1");
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).setStatus(ParkingSlotStatus.TAKEN);
			fields.put("status", ParkingSlotStatus.TAKEN.ordinal());
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getStatus(), ParkingSlotStatus.TAKEN);
			
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetRank(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		fields.put("name", "testParkingSlot1");
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).setRank(StickersColor.BLUE);
			
			fields.put("rank", StickersColor.BLUE.ordinal());
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);

			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getRank(), StickersColor.BLUE);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetArea(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("name", "testParkingSlot1");
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).setArea(Area.POOL);
			
			fields.put("area",  Area.POOL.ordinal());
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getArea(), Area.POOL);

		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testSetLocation(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("name", "testParkingSlot1");
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).setLocation(new MapLocation(32.12345, 32.12345));
			
			fields.put("location", new ParseGeoPoint(32.12345, 32.12345));
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			
			Assert.assertEquals(new MapLocation(32.12345, 32.12345).getLat(), new ParkingSlot("testParkingSlot1",dbm).getLocation().getLat(), 0);
			Assert.assertEquals(new MapLocation(32.12345, 32.12345).getLon(), new ParkingSlot("testParkingSlot1",dbm).getLocation().getLon(), 0);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetDefaultColor(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		fields.put("name", "testParkingSlot1");
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).setDefaultColor(StickersColor.BLUE);
			
			fields.put("defaultColor", StickersColor.BLUE.ordinal());
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getDefaultColor(), StickersColor.BLUE);
			
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetEndTime(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		fields.put("name", "testParkingSlot1");
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).setEndTime(new Date());
			Calendar cal = Calendar.getInstance();
			
			fields.put("endTime", new Date());
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			
            cal.setTime(new ParkingSlot("testParkingSlot1",dbm).getEndTime());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
            Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
			Assert.assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
			
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
		
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkNameNull() {
		DatabaseManager dbm = setMock();
		try {
			new ParkingSlot("testParkingSlot1",dbm).setName(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkColorNull() {
		DatabaseManager dbm = setMock();
		try {
			new ParkingSlot("testParkingSlot1",dbm).setRank(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkDefaultColorNull() {
		DatabaseManager dbm = setMock();
		try {
			new ParkingSlot("testParkingSlot1",dbm).setDefaultColor(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEndTimeNull() {
		DatabaseManager dbm = setMock();
		try {
			new ParkingSlot("testParkingSlot1",dbm).setEndTime(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkLocationNull() {
		DatabaseManager dbm = setMock();
		try {
			new ParkingSlot("testParkingSlot1",dbm).setLocation(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkAreaNull() {
		DatabaseManager dbm = setMock();
		try {
			new ParkingSlot("testParkingSlot1",dbm).setArea(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkStatusNull() {
		DatabaseManager dbm = setMock();
		try {
			new ParkingSlot("testParkingSlot1",dbm).setStatus(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testChangeStatus(){
		DatabaseManager dbm = setMock();
		Map <String, Object> keys = new HashMap<>();
		Map <String, Object> fields = new HashMap<>();
		keys.put("name", "testParkingSlot1");
		
		fields.put("status", ParkingSlotStatus.TAKEN.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location", new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("name", "testParkingSlot1");
		fields.put("rating", 10);
		fields.put("numOfVoting", 2);
		
		try{
			new ParkingSlot("testParkingSlot1",dbm).changeStatus(ParkingSlotStatus.TAKEN);
			Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1",dbm).getStatus(), ParkingSlotStatus.TAKEN);	
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void finishTest() throws ParseException, InterruptedException {
		// delete objects
		DatabaseManager dbm = setMock();
		new ParkingSlot("testParkingSlot1",dbm).removeParkingSlotFromDB();
	}
	
}