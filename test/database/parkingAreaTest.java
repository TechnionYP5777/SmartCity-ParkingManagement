package database;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import data.management.DBManager;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.ParkingSlotStatus;
import data.members.MapLocation;

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
	
}
