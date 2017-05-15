package database;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;

import data.management.DBManager;
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
	
	@Test
	public void addAndRemove(){
		DBManager.initialize();
		try{
			// create new slot
			new ParkingSlot("testParkingSlot2", ParkingSlotStatus.FREE, StickersColor.GREEN,
					StickersColor.GREEN, new MapLocation(32.123, 32.123), new Date(), Area.TAUB);
			Thread.sleep(10000);
			new ParkingSlot("testParkingSlot2").removeParkingSlotFromDB();
			Thread.sleep(10000);
	
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	
	@Test
	public void check(){
		DBManager.initialize();
		try{
			new ParkingSlot("first", ParkingSlotStatus.FREE, StickersColor.BLUE, StickersColor.BLUE, new MapLocation(3.12, 3.12), new Date(), Area.TAUB);
			Thread.sleep(6000);	
			ParkingSlot p = new ParkingSlot("first");
			Thread.sleep(6000);	
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
			new ParkingSlot("first").removeParkingSlotFromDB();
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Before
	public void setUpTest() throws ParseException, InterruptedException {
		DBManager.initialize();
		try{
			// create new slot
			new ParkingSlot("testParkingSlot1", ParkingSlotStatus.FREE, StickersColor.GREEN,
					StickersColor.GREEN, new MapLocation(32.123, 32.123), new Date(), Area.TAUB);
			Thread.sleep(10000);

		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetName(){
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getName(), "testParkingSlot1");
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetStatus(){
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getStatus(), ParkingSlotStatus.FREE);
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetArea(){
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getArea(), Area.TAUB);
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetRank(){
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getRank(), StickersColor.GREEN);
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetLocation(){
		try{
			ParkingSlot p =new ParkingSlot("testParkingSlot1");
			Thread.sleep(10000);
			Assert.assertEquals(p.getLocation().getLat(), 32.123, 0);
			Assert.assertEquals(p.getLocation().getLat(), 32.123, 0);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetDefaultColor(){
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getDefaultColor(), StickersColor.GREEN);
			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testGetEndTime(){
		try{
			ParkingSlot p =new ParkingSlot("testParkingSlot1");
			Thread.sleep(10000);
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
		DBManager.initialize();
		try{
			ParkingSlot p = new ParkingSlot("testParkingSlot1");
			Thread.sleep(12000);	
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
		try{
			new ParkingSlot("testParkingSlot1").setName("testParkingSlot10");
			Thread.sleep(6000);
			Assert.assertEquals(new ParkingSlot("testParkingSlot10").getName(), "testParkingSlot10");
			Thread.sleep(6000);
			new ParkingSlot("testParkingSlot10").setName("testParkingSlot1");
			Thread.sleep(6000);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getName(), "testParkingSlot1");
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
		
	@Test
	public void testSetStatus(){
		try{
			new ParkingSlot("testParkingSlot1").setStatus(ParkingSlotStatus.TAKEN);
			Thread.sleep(6000);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getStatus(), ParkingSlotStatus.TAKEN);
			Thread.sleep(6000);
			new ParkingSlot("testParkingSlot1").setStatus(ParkingSlotStatus.FREE);
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetRank(){
		try{
			new ParkingSlot("testParkingSlot1").setRank(StickersColor.BLUE);
			Thread.sleep(6000);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getRank(), StickersColor.BLUE);
			Thread.sleep(6000);
			new ParkingSlot("testParkingSlot1").setRank(StickersColor.GREEN);
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetArea(){
		try{
			new ParkingSlot("testParkingSlot1").setArea(Area.POOL);
			Thread.sleep(6000);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getArea(), Area.POOL);
			Thread.sleep(6000);
			new ParkingSlot("testParkingSlot1").setArea(Area.TAUB);
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetLocation(){
		try{
			new ParkingSlot("testParkingSlot1").setLocation(new MapLocation(32.12345, 32.12345));
			Thread.sleep(6000);
			Assert.assertEquals(new MapLocation(32.12345, 32.12345).getLat(), new ParkingSlot("testParkingSlot1").getLocation().getLat(), 0);
			Assert.assertEquals(new MapLocation(32.12345, 32.12345).getLon(), new ParkingSlot("testParkingSlot1").getLocation().getLon(), 0);
			Thread.sleep(6000);
			new ParkingSlot("testParkingSlot1").setLocation(new MapLocation(32.123, 32.123));
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetDefaultColor(){
		try{
			new ParkingSlot("testParkingSlot1").setDefaultColor(StickersColor.BLUE);
			Thread.sleep(6000);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getDefaultColor(), StickersColor.BLUE);
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testSetEndTime(){
		try{
			new ParkingSlot("testParkingSlot1").setEndTime(new Date());
			Thread.sleep(6000);
			Calendar cal = Calendar.getInstance();
            cal.setTime(new ParkingSlot("testParkingSlot1").getEndTime());
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(new Date());
            Assert.assertEquals(cal.get(Calendar.DAY_OF_MONTH), cal2.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH));
			Assert.assertEquals(cal.get(Calendar.YEAR), cal2.get(Calendar.YEAR));
			Thread.sleep(6000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkNameNull() {
		DBManager.initialize();
		try {
			new ParkingSlot("testParkingSlot1").setName(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkColorNull() {
		DBManager.initialize();
		try {
			new ParkingSlot("testParkingSlot1").setRank(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkDefaultColorNull() {
		DBManager.initialize();
		try {
			new ParkingSlot("testParkingSlot1").setDefaultColor(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkEndTimeNull() {
		DBManager.initialize();
		try {
			new ParkingSlot("testParkingSlot1").setEndTime(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkLocationNull() {
		DBManager.initialize();
		try {
			new ParkingSlot("testParkingSlot1").setLocation(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkAreaNull() {
		DBManager.initialize();
		try {
			new ParkingSlot("testParkingSlot1").setArea(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void checkStatusNull() {
		DBManager.initialize();
		try {
			new ParkingSlot("testParkingSlot1").setStatus(null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testChangeStatus(){
		try{
			new ParkingSlot("testParkingSlot1").changeStatus(ParkingSlotStatus.TAKEN);
			Thread.sleep(6000);
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getStatus(), ParkingSlotStatus.TAKEN);	
			Thread.sleep(6000);
			new ParkingSlot("testParkingSlot1").changeStatus(ParkingSlotStatus.FREE);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@After
	public void finishTest() throws ParseException, InterruptedException {
		// delete objects
		new ParkingSlot("testParkingSlot1").removeParkingSlotFromDB();
		Thread.sleep(6000);
	}
}