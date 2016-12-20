package database;

import java.util.Date;

import org.junit.Test;
import data.management.DBManager;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.ParkingSlotStatus;
import data.members.MapLocation;

public class parkingSlotTest {

	@Test	
	public void test0(){
		DBManager.initialize();
		
		// Create a new parking slot in the DB
		// Please note that EVERY activation of this test will result in a new testSlot row in the DB
		try{
			ParkingSlot newSlot = new ParkingSlot("testSlot", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, new MapLocation(32.778153, 35.021855), new Date());
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("test0 ...... FAILED");
			return;
		}
		System.out.println("test0 ...... OK");
	}
}