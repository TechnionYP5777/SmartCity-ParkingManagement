package database;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.parse4j.ParseException;

import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

public class populateDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Start Populating Taub");
		addTaubParkings();
		System.out.println("Done Populating Taub");
	}
	// TODO: read data from file
	public static void addTaubParkings(){
		try {
			// Taub Slots
			ParkingSlot slot1 = new ParkingSlot("taub1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(32.777783, 35.021928), new Date());
			ParkingSlot slot2 = new ParkingSlot("taub2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(32.777794, 35.021928), new Date());
			ParkingSlot slot3 = new ParkingSlot("taub3", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(32.777853, 35.021853), new Date());
			ParkingSlot slot4 = new ParkingSlot("taub4", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(32.777863, 35.021836), new Date());
			ParkingSlot slot5 = new ParkingSlot("taub5", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(32.777928, 35.021753), new Date());
			
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);slots.add(slot2);slots.add(slot3);slots.add(slot4);slots.add(slot5);
			
			ParkingArea taubArea = new ParkingArea(0, "Taub", slots, StickersColor.RED);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
