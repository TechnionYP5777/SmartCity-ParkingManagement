package database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	public void test0(){
		DBManager.initialize();
		
		// Create a new parking area in the DB
		// Please note that EVERY activation of this test will result in a new testSlot row in the DB and a 0 areaId in the DB
		try{
			ParkingSlot slot1 = new ParkingSlot("testS", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			
			assertNotNull((new ParkingArea(0, slots, StickersColor.RED)));
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	// Add a  slot to an existing parkingArea
	@Test	
	public void test1(){
		DBManager.initialize();
		
		// Create a new parking area in the DB
		// Please note that EVERY activation of this test will result in a new testSlot row in the DB and a 0 areaId in the DB
		try{
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			
			ParkingArea area = new ParkingArea(0, slots, StickersColor.RED);
			assertNotNull(area);
			
			area.addParkingSlot((new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date())));
		} catch(Exception e){
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testRetrieveSlots(){
		System.out.println("before init: " + System.currentTimeMillis());
		DBManager.initialize();
		System.out.println("after init: " + System.currentTimeMillis());
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 11);
		try {
			System.out.println("before 1st query: " + System.currentTimeMillis());
			List<ParseObject> areaList = query.find();
			System.out.println("after 1st query: " + System.currentTimeMillis());
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			System.out.println("before 2nd query: " + System.currentTimeMillis());
			//List<ParseObject> pList = areaList.get(0).getList("parkingSlots");
			ParkingArea area = new ParkingArea(areaList.get(0));
			System.out.println("after 2nd query: " + System.currentTimeMillis());
			System.out.println("free slots for id: " + area.getobjectId() + " is: " + area.getNumOfFreeSlots());
			System.out.println("adfter getting slots: " + System.currentTimeMillis());
		} catch (ParseException e) {
			fail();
		}

	}
	
}
