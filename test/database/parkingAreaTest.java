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

			assertNotNull((new ParkingArea(0, slots, StickersColor.RED)));
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
		// Please note that EVERY activation of this test will result in a new
		// testSlot row in the DB and a 0 areaId in the DB
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);

			ParkingArea area = new ParkingArea(0, slots, StickersColor.RED);
			assertNotNull(area);

			area.addParkingSlot((new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date())));
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testRetrieveSlots() {
		System.out.println("before init: " + System.currentTimeMillis());
		DBManager.initialize();
		System.out.println("after init: " + System.currentTimeMillis());
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 3);
		try {
			System.out.println("before 1st query: " + System.currentTimeMillis());
			List<ParseObject> areaList = query.find();
			System.out.println("after 1st query: " + System.currentTimeMillis());
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			System.out.println("before 2nd query: " + System.currentTimeMillis());
			// List<ParseObject> pList =
			// areaList.get(0).getList("parkingSlots");
			ParkingArea area = new ParkingArea(areaList.get(0));
			System.out.println("after 2nd query: " + System.currentTimeMillis());
			System.out.println("free slots for id: " + area.getObjectId() + " is: " + area.getNumOfFreeSlots());
			System.out.println("adfter getting slots: " + System.currentTimeMillis());
		} catch (ParseException e) {
			fail();
		}

	}

	// @Test
	// public void testAllSlots(){
	// DBManager.initialize();
	// ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
	// query.whereEqualTo("areaId", 11);
	// try {
	// List<ParseObject> areaList = query.find();
	// if (areaList == null || areaList.isEmpty())
	// throw new RuntimeException("There should be an area with areaId="+ 0);
	// ParseObject parkingAreaObj = areaList.get(0);
	// int size = parkingAreaObj.get;
	// Assert.assertEquals(2,2);
	// } catch (ParseException e) {
	// fail();
	// }
	// }

	@Test
	public void testGetAllSlots() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 3);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			List<ParseObject> allSlots = new ParkingArea(areaList.get(0)).getAllSlots();
			if (allSlots == null)
				throw new RuntimeException("There should be a slots in area with areaId=" + 0);
			Assert.assertEquals(2, allSlots.size());
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void testConvertSlots() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 3);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			List<ParseObject> allSlots = new ParkingArea(areaList.get(0)).getAllSlots();
			if (allSlots == null)
				throw new RuntimeException("There should be a slots in area with areaId=" + 0);
			Assert.assertEquals(2, (new ParkingArea(areaList.get(0)).convertToSlots(allSlots)).size());
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void testGetSlotsByStatus() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 3);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Set<ParkingSlot> slotsByStatus = new ParkingArea(areaList.get(0)).getSlotsByStatus(ParkingSlotStatus.FREE);
			if (slotsByStatus == null)
				throw new RuntimeException("There should slots in area with areaId=" + 0);
			Assert.assertEquals(2, slotsByStatus.size());
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void testGetSlotsByStatus2() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 3);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			Set<ParkingSlot> slotsByStatus = new ParkingArea(areaList.get(0)).getSlotsByStatus(ParkingSlotStatus.TAKEN);
			if (slotsByStatus == null)
				throw new RuntimeException("There should slots in area with areaId=" + 0);
			Assert.assertEquals(0, slotsByStatus.size());
		} catch (ParseException e) {
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
			ParkingArea area = new ParkingArea(0, slots, StickersColor.RED);

			// Act
			ParkingArea returnedArea = new ParkingArea(area.getObjectId());

			// Assert
			Assert.assertEquals(area.getAreaId(), returnedArea.getAreaId());
			Assert.assertEquals(area.getObjectId(), returnedArea.getObjectId());
			Assert.assertEquals(area.getColor(), returnedArea.getColor());

		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

}
