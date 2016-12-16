package database;

import org.junit.Test;
import org.parse4j.ParseGeoPoint;

import data.management.DBManager;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.ParkingSlotStatus;
import data.members.MapLocation;

public class parkingSlotTest {

	@Test	
	public void test0(){
		DBManager.initialize();
		
		// Create a new parking slot in the DB
		ParkingArea parkingArea = new ParkingArea();
		try{
			MapLocation location = new MapLocation(32.778153, 35.021855);
			
			ParkingSlot newSlot = new ParkingSlot("test slot", ParkingSlotStatus.FREE, StickersColor.RED, location, parkingArea);
		} catch(Exception e){
			//e.printStackTrace();
		}
	}
}