package database;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.ParkingSlotStatus;
import data.members.MapLocation;
import data.members.ParkingArea;

public class parkingSlotTest {

	@Test
	public void check(){
		DBManager.initialize();
		try{
			new ParkingSlot("first", ParkingSlotStatus.FREE, StickersColor.BLUE, StickersColor.BLUE, new MapLocation(3.12, 3.12), new Date());
			Thread.sleep(6000);	
			System.out.println("finish");
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void check2(){
		DBManager.initialize();
		try{
			ParkingSlot p = new ParkingSlot("first");
			Thread.sleep(12000);	
			Assert.assertEquals(p.getName(), "first");
			Assert.assertEquals(p.getStatus(), ParkingSlotStatus.FREE);
			Assert.assertEquals(p.getColor(), StickersColor.BLUE);
			Assert.assertEquals(p.getDefaultColor(), StickersColor.BLUE);
			Assert.assertEquals(p.getEndTime().getDay(), new Date().getDay());
			Assert.assertEquals(p.getEndTime().getMonth(), new Date().getMonth());
			Assert.assertEquals(p.getEndTime().getYear(), new Date().getYear());
			Assert.assertEquals(p.getLocation().getLat(), 3.12, 0);
			Assert.assertEquals(p.getLocation().getLon(), 3.12, 0);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void check3(){
		DBManager.initialize();
		try{
			new ParkingSlot("first").removeParkingSlotFromDB();
			Thread.sleep(12000);	
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
			final ParkingSlot slot1 = new ParkingSlot("testParkingSlot1", ParkingSlotStatus.FREE, StickersColor.GREEN,
					StickersColor.GREEN, new MapLocation(32.123, 32.123), new Date());
			Thread.sleep(10000);
			//TODO: remove remark after ParkingArea class will complete.
//			final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
//			slots.add(slot1);
		
//			new ParkingArea(20, "testParkingSlot", new MapLocation(0, 0), slots, StickersColor.GREEN);
//			Thread.sleep(10000);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
	//TODO: remove remark after ParkingArea class will complete.
//	@Test
//	public void testGetContainingArea() {
//		DBManager.initialize();
//		try {
//			final ParkingSlot slot1 = new ParkingSlot("testS", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
//					new MapLocation(0, 0), new Date());
//			Thread.sleep(10000);
//			final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
//			slots.add(slot1);
//			final ParkingArea area = new ParkingArea(0, "t1", new MapLocation(0, 0), slots, StickersColor.RED);
//			Thread.sleep(10000);
//			Assert.assertEquals(area.getName(), slot1.findContainingParkingArea());
//
//			area.deleteParseObject();
//			Thread.sleep(10000);
//			slot1.deleteParseObject();
//			Thread.sleep(10000);
//
//		} catch (final Exception ¢) {
//			¢.printStackTrace();
//			Assert.fail();
//		}
//	}

	//TODO: remove remark after ParkingArea class will complete.
//	@Test
//	public void testRemoveSlotFromArea() {
//		DBManager.initialize();
//		try {
//			// Arrange
//			final ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
//					new MapLocation(0, 0), new Date());
//			Thread.sleep(10000);
//			final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
//			slots.add(slot1);
//			final ParkingArea area = new ParkingArea(12, "t1", new MapLocation(0, 0), slots, StickersColor.RED);
//			Thread.sleep(10000);
//
//			// Act + assert
//			slot1.removeParkingSlotFromAreaAndDB();
//			Thread.sleep(10000);
//
//			area.deleteParseObject();
//			Thread.sleep(10000);
//		} catch (final Exception ¢) {
//			¢.printStackTrace();
//			Assert.fail();
//		}
//	}

	
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
	public void testGetColor(){
		try{
			Assert.assertEquals(new ParkingSlot("testParkingSlot1").getColor(), StickersColor.GREEN);
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
			Assert.assertEquals(p.getEndTime().getDay(), new Date().getDay());
			Assert.assertEquals(p.getEndTime().getMonth(), new Date().getMonth());
			Assert.assertEquals(p.getEndTime().getYear(), new Date().getYear());
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
			Assert.assertEquals(p.getColor(), StickersColor.GREEN);
			Assert.assertEquals(p.getDefaultColor(), StickersColor.GREEN);
			Assert.assertEquals(p.getEndTime().getDay(), new Date().getDay());
			Assert.assertEquals(p.getEndTime().getMonth(), new Date().getMonth());
			Assert.assertEquals(p.getEndTime().getYear(), new Date().getYear());
			Assert.assertEquals(p.getLocation().getLat(), 32.123, 0);
			Assert.assertEquals(p.getLocation().getLon(), 32.123, 0);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
	
		
	@After
	public void finishTest() throws ParseException, InterruptedException {
		// delete objects
		//TODO: remove remark after ParkingArea class will complete.
//		final ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
//		queryArea.whereEqualTo("areaId", 20);
//		final List<ParseObject> areaList = queryArea.find();
//		if (areaList == null || areaList.isEmpty())
//			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
//		new ParkingArea(areaList.get(0)).deleteParseObject();
//		Thread.sleep(10000);

		new ParkingSlot("testParkingSlot1").removeParkingSlotFromDB();
		Thread.sleep(10000);
	}
}