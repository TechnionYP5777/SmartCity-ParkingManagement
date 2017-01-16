package manager.logic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

/**
 * @Author Inbal Matityahu
 */

public class selectAreaTest {
	
	@Test
	public void NumOfFreeSlotsPerAreaTest() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("selectAreaTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(32.123, 32.123), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			ParkingSlot slot2 = new ParkingSlot("selectAreaTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(0, 0), new Date());
			slots.add(slot2);
			new ParkingArea(20, "t1",slots, StickersColor.GREEN);
			Assert.assertEquals(1,new SelectAnArea().getNumOfFreeSlotsPerArea(20));
			
			//delete objects
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList = queryArea.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "selectAreaTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "selectAreaTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void NumOfTakenSlotsPerAreaTest() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("selectAreaTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(32.123, 32.123), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			ParkingSlot slot2 = new ParkingSlot("selectAreaTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(0, 0), new Date());
			slots.add(slot2);
			ParkingArea area = new ParkingArea(20,"t1", slots, StickersColor.GREEN);
			assertNotNull(area);
			
			Assert.assertEquals(1,new SelectAnArea().getNumOfTakenSlotsPerArea(20));
			
			//delete objects
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList = queryArea.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "selectAreaTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "selectAreaTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
	
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void TotalNumOfSlotsPerAreaTest() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("selectAreaTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(32.123, 32.123), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			ParkingSlot slot2 = new ParkingSlot("selectAreaTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(0, 0), new Date());
			slots.add(slot2);
			ParkingArea area = new ParkingArea(20, "t1",slots, StickersColor.GREEN);
			assertNotNull(area);
			
			Assert.assertEquals(2,new SelectAnArea().getTotalNumOfSlotsPerArea(20));

			//delete objects
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList = queryArea.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "selectAreaTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "selectAreaTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
	
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void ColorOfAreaTest() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("selectAreaTest1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(32.123, 32.123), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			ParkingSlot slot2 = new ParkingSlot("selectAreaTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
					new MapLocation(0, 0), new Date());
			slots.add(slot2);
			ParkingArea area = new ParkingArea(20,"t1", slots, StickersColor.GREEN);
			assertNotNull(area);
			
			Assert.assertEquals(StickersColor.GREEN,new SelectAnArea().getColorOfArea(20));

			//delete objects
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 20);
			List<ParseObject> areaList = queryArea.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingArea(areaList.get(0)).deleteParseObject();
			
			
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "selectAreaTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "selectAreaTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
	
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test(expected=RuntimeException.class)
	public void test5() {
		DBManager.initialize();
		System.out.println((new SelectAnArea()).getNumOfFreeSlotsPerArea(100));
	}
	
	@Test(expected=RuntimeException.class)
	public void test6() {
		DBManager.initialize();
		(new SelectAnArea()).getNumOfTakenSlotsPerArea(100);
	}
	
	@Test(expected=RuntimeException.class)
	public void test7() {
		DBManager.initialize();
		(new SelectAnArea()).getTotalNumOfSlotsPerArea(100);
	}
	
	@Test(expected=RuntimeException.class)
	public void test8() {
		DBManager.initialize();
		(new SelectAnArea()).getColorOfArea(100);
	}
	
	@Test
	public void test9() {
		DBManager.initialize();
		List<String> colors=(new SelectAnArea()).getAllPossibleColors();
		Assert.assertEquals(6,colors.size());
		//for (String s: colors)
			//System.out.println(s);
	}
}
