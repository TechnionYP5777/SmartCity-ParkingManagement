package database;

import org.junit.Test;

import data.management.DBManager;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.ParkingSlotColor;
import data.members.ParkingSlotStatus;

public class parkingSlotTest {

	@Test	
	public void test0(){
		DBManager.initialize();
		
		// Create a new parking slot in the DB
		ParkingArea parkingArea = new ParkingArea();
		try{
			ParkingSlot newSlot = new ParkingSlot("test slot", ParkingSlotStatus.FREE, ParkingSlotColor.RED, parkingArea);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
