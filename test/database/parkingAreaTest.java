package database;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	public void test0(){
		DBManager.initialize();
		
		// Create a new parking area in the DB
		// Please note that EVERY activation of this test will result in a new testSlot row in the DB and a 0 areaId in the DB
		try{
			ParkingSlot slot1 = new ParkingSlot("testS", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			
			Assert.assertNotNull((new ParkingArea(0, slots, StickersColor.RED)));
		} catch(Exception e){
			e.printStackTrace();
			Assert.fail();
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
			Assert.assertNotNull(area);
			
			ParkingSlot slot2 = new ParkingSlot("testS2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, new MapLocation(0, 0), new Date());
			area.addParkingSlot(slot2);
		} catch(Exception e){
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	@Test
	public void testRetrieveSlots(){
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 0);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			List<ParseObject> pList = areaList.get(0).getList("parkingSlots");
			System.out.println(areaList.get(0).getObjectId());
			List<Object> ids = pList.stream().map(p -> p.getObjectId()).collect(Collectors.toList());
			ParseQuery<ParseObject> slotQuery = ParseQuery.getQuery("ParkingSlot");
			slotQuery.whereContainedIn("objectId", ids);
			slotQuery.whereEqualTo("status", ParkingSlotStatus.FREE.ordinal());
			List<ParseObject> vacantSpots = slotQuery.find();
			if(vacantSpots != null)
				System.out.println(vacantSpots.size());
			else{
				System.out.println("no spots exist");
			}
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}

	}
	
}
