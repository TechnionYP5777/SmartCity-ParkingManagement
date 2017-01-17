package database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingAreas;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

/**
 * @Author Inbal Matityahu
 */

public class parkingAreasTest {	
	@Test
	public void test() {
		DBManager.initialize();
		Assert.assertEquals(4, new ParkingAreas().getParkingAreas().size());
	}
	

	@Test
	public void test2() throws ParseException {
		DBManager.initialize();
		try {
		//insert new ParkingArea and ParkingSlots
		ParkingSlot slot1 = new ParkingSlot("parkingAreasTest1", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("parkingAreasTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingArea area = new ParkingArea(20,"t1", new MapLocation(0, 0), slots, StickersColor.GREEN);
		assertNotNull(area);
		
		//test function
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Assert.assertEquals(0, (new ParkingAreas().getNumOfFreeByArea(new ParkingArea(areaList.get(0)))));
		
			//remove new ParkingArea and ParkingSlots
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList2 = queryArea.find();
			if (areaList2 == null || areaList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList2.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "parkingAreasTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "parkingAreasTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
		
		} catch (ParseException e) {
			fail();
		}
	}


	@Test
	public void test3() throws ParseException {
		DBManager.initialize();
		try {
		//insert new ParkingArea and ParkingSlots
		ParkingSlot slot1 = new ParkingSlot("parkingAreasTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("parkingAreasTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingArea area = new ParkingArea(20, "t1", new MapLocation(0, 0),slots, StickersColor.GREEN);
		assertNotNull(area);
		
		//test function
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Assert.assertEquals(1, (new ParkingAreas().getNumOfFreeByArea(new ParkingArea(areaList.get(0)))));
		
			//remove new ParkingArea and ParkingSlots
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList2 = queryArea.find();
			if (areaList2 == null || areaList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList2.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "parkingAreasTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "parkingAreasTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
		
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test4() throws ParseException {
		DBManager.initialize();
		try {
		//insert new ParkingArea and ParkingSlots
		ParkingSlot slot1 = new ParkingSlot("parkingAreasTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("parkingAreasTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingArea area = new ParkingArea(20,"t1",new MapLocation(0, 0), slots, StickersColor.GREEN);
		assertNotNull(area);
		
		//test function
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Assert.assertEquals(1, (new ParkingAreas().getNumOfTakenByArea(new ParkingArea(areaList.get(0)))));
		
			//remove new ParkingArea and ParkingSlots
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList2 = queryArea.find();
			if (areaList2 == null || areaList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList2.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "parkingAreasTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "parkingAreasTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
		
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test5() throws ParseException {
		DBManager.initialize();
		try {
		//insert new ParkingArea and ParkingSlots
		ParkingSlot slot1 = new ParkingSlot("parkingAreasTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("parkingAreasTest2", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingArea area = new ParkingArea(20,"t1", new MapLocation(0, 0), slots, StickersColor.GREEN);
		assertNotNull(area);
		
		//test function
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Assert.assertEquals(0, (new ParkingAreas().getNumOfTakenByArea(new ParkingArea(areaList.get(0)))));
		
			//remove new ParkingArea and ParkingSlots
			//delete objects
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList2 = queryArea.find();
			if (areaList2 == null || areaList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList2.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "parkingAreasTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "parkingAreasTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
		
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test6() throws ParseException {
		// Arrange
		DBManager.initialize();
		ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
				new MapLocation(0, 0), new Date());
		ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
				new MapLocation(0, 0), new Date());

		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		slots.add(slot2);

		MapLocation loc =  new MapLocation(0, 0);
		ParkingArea area1 = new ParkingArea(0, "t1", loc, slots, StickersColor.RED);
		ParkingArea area2 = new ParkingArea(0, "t2", loc, slots, StickersColor.RED);
		
		Set<ParkingArea> a = new HashSet<ParkingArea>();
		a.add(area1);a.add(area2);
		ParkingAreas areas = new ParkingAreas(a);
		
		// Act
		int free = areas.getNumOfFreeSlots();
		int taken = areas.getNumOfTakenSlots();
		
		// Assert
		Assert.assertEquals(4,free);
		Assert.assertEquals(0,taken);

		
		// Cleanup
		areas.deleteParseObject();
		area1.deleteParseObject();
		area2.deleteParseObject();
		slot1.deleteParseObject();
		slot2.deleteParseObject();
	}

	@Test
	public void test7() {
		DBManager.initialize();
		try {
		//insert new ParkingArea and ParkingSlots
		ParkingSlot slot1 = new ParkingSlot("parkingAreasTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("parkingAreasTest2", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingArea area = new ParkingArea(20,"t1", new MapLocation(0, 0), slots, StickersColor.GREEN);
		assertNotNull(area);
		
		//test function
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Assert.assertEquals(2, (new ParkingAreas().getNumOfSlotsByArea(new ParkingArea(areaList.get(0)))));
		
			//remove new ParkingArea and ParkingSlots
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList2 = queryArea.find();
			if (areaList2 == null || areaList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList2.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "parkingAreasTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "parkingAreasTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
		
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test8() {
		DBManager.initialize();
		try {
		//insert new ParkingArea and ParkingSlots
		ParkingSlot slot1 = new ParkingSlot("parkingAreasTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("parkingAreasTest2", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingArea area = new ParkingArea(20,"t1", new MapLocation(0, 0), slots, StickersColor.GREEN);
		assertNotNull(area);
		
		//test function
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			if(!"parkingAreasTest1".equalsIgnoreCase(
					new ParkingAreas().getParkingslotByArea(new ParkingArea(areaList.get(0))).getName())
					&& !"parkingAreasTest1".equalsIgnoreCase(
							new ParkingAreas().getParkingslotByArea(new ParkingArea(areaList.get(0))).getName())
					&& !"parkingAreasTest2".equalsIgnoreCase(
							new ParkingAreas().getParkingslotByArea(new ParkingArea(areaList.get(0))).getName()))
				Assert.fail();
			//remove new ParkingArea and ParkingSlots
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList2 = queryArea.find();
			if (areaList2 == null || areaList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList2.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "parkingAreasTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "parkingAreasTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
		
		} catch (ParseException e) {
			fail();
		}

	}
	
	@Test
	public void test9() {
		DBManager.initialize();
		Assert.assertEquals(0, (new ParkingAreas().getNumOfTakenSlots()));
	}
	
	@Test
	public void test10() throws ParseException {
		DBManager.initialize();
		Assert.assertEquals(4, (new ParkingAreas().getParkingAreasNames()).size());
	}
	
	@Test
	public void test11() throws ParseException {
		// Arrange
		DBManager.initialize();
		ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
				new MapLocation(0, 0), new Date());
		ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
				new MapLocation(0, 0), new Date());

		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		slots.add(slot2);

		MapLocation loc =  new MapLocation(0, 0);
		ParkingArea area = new ParkingArea(0, "t1", loc, slots, StickersColor.RED);
		
		Set<ParkingArea> a = new HashSet<ParkingArea>();
		a.add(area);
		ParkingAreas areas = new ParkingAreas(a);
		
		// Act
		HashMap<String,StickersColor> colors = areas.getParkingAreasColor();
		HashMap<String,MapLocation> locations = areas.getParkingAreasLocation();
		
		// Assert
		Assert.assertEquals(1,colors.size());
		Assert.assertEquals(1,locations.size());
		Assert.assertEquals(StickersColor.RED,colors.get("t1"));
		Assert.assertEquals(loc,locations.get("t1"));
		
		// Cleanup
		areas.deleteParseObject();
		area.deleteParseObject();
		slot1.deleteParseObject();
		slot2.deleteParseObject();
	}
	
}
