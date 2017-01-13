package database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
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
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

/*
 *  @Author Tom Nof
 *  @Inbal Matityauh
 *  @Author David Cohen
 */

public class parkingAreaTest {

	@Test
	public void test0() {
		DBManager.initialize();

		// Create a new parking area in the DB
		// Please note that EVERY activation of this test will result in a new
		// testSlot row in the DB and a 0 areaId in the DB
		try {
			ParkingSlot slot1 = new ParkingSlot("testS", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			ParkingArea area = new ParkingArea(0,"t1", slots, StickersColor.RED);
			assertNotNull(area);

			area.deleteParseObject();
			slot1.deleteParseObject();

		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	// Add a slot to an existing parkingArea
	@Test
	public void test1() {
		DBManager.initialize();

		// Create a new parking area in the DB
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);

			ParkingArea area = new ParkingArea(0,"t1",  slots, StickersColor.RED);
			assertNotNull(area);
			ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			area.addParkingSlot(slot2);
			Assert.assertEquals(2, area.getNumOfParkingSlots());

			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAddSlots() {
		DBManager.initialize();	
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			ParkingArea area = new ParkingArea(0,"t1",  slots, StickersColor.RED);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 0);

			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Assert.assertEquals(1,  (new ParkingArea(areaList.get(0))).getNumOfFreeSlots());
			
			ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			area.addParkingSlot(slot2);
			
			
			Assert.assertEquals(2,  area.getNumOfFreeSlots());

			// Clean up DB
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
			
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}

	}

	@Test
	public void testGetAllSlots() {
		DBManager.initialize();

		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);slots.add(slot2);
			
			ParkingArea area = new ParkingArea(0,"t1",  slots, StickersColor.RED);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 0);
			
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			List<ParseObject> allSlots = new ParkingArea(areaList.get(0)).getAllSlots();
			if (allSlots == null)
				throw new RuntimeException("There should be a slots in area with areaId=" + 0);
			Assert.assertEquals(2, allSlots.size());
			
			// Clean up DB
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
			
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testConvertSlots() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);slots.add(slot2);
			
			ParkingArea area = new ParkingArea(0,"t1",  slots, StickersColor.RED);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 0);
			
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			List<ParseObject> allSlots = new ParkingArea(areaList.get(0)).getAllSlots();
			if (allSlots == null)
				throw new RuntimeException("There should be a slots in area with areaId=" + 0);
			Assert.assertEquals(2, (new ParkingArea(areaList.get(0)).convertToSlots(allSlots)).size());
			
			// Clean up DB
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
			
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetSlotsByStatus() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);slots.add(slot2);
			
			ParkingArea area = new ParkingArea(0,"t1",  slots, StickersColor.RED);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 0);
			
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Set<ParkingSlot> slotsByStatus = new ParkingArea(areaList.get(0)).getSlotsByStatus(ParkingSlotStatus.FREE);
			if (slotsByStatus == null)
				throw new RuntimeException("There should slots in area with areaId=" + 0);
			Assert.assertEquals(2, slotsByStatus.size());
			
			// Clean up DB
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
			
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testGetSlotsByStatus2() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);slots.add(slot2);
			
			ParkingArea area = new ParkingArea(0,"t1",  slots, StickersColor.RED);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 0);
			
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Set<ParkingSlot> slotsByTakenStatus = new ParkingArea(areaList.get(0)).getSlotsByStatus(ParkingSlotStatus.TAKEN);
			if (slotsByTakenStatus == null)
				throw new RuntimeException("There should slots in area with areaId=" + 0);
			Assert.assertEquals(0, slotsByTakenStatus.size());
			
			// Clean up DB
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
			
		} catch (ParseException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void getAreaByObjectId() {
		DBManager.initialize();
		try {
			// Arrange
			ParkingSlot slot1 = new ParkingSlot("testS", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			ParkingArea area = new ParkingArea(0,"t1", slots, StickersColor.RED);

			// Act
			ParkingArea returnedArea = new ParkingArea(area.getObjectId());

			// Assert
			Assert.assertEquals(area.getAreaId(), returnedArea.getAreaId());
			Assert.assertEquals(area.getObjectId(), returnedArea.getObjectId());
			Assert.assertEquals(area.getColor(), returnedArea.getColor());

			area.deleteParseObject();
			slot1.deleteParseObject();
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
