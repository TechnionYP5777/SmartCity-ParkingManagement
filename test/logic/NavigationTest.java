package logic;
import data.members.*;
import org.junit.Assert;
import org.junit.Test;
import java.util.Date;
import org.parse4j.ParseException;
import java.util.Set;
import java.util.HashSet;
import Exceptions.*;
import logic.Navigation;
import manager.logic.*;

public class NavigationTest {

	@Test
	public void getDistanceTest() {
		MapLocation source = new MapLocation(32.777552, 35.020578);
		MapLocation target = new MapLocation(32.778761, 35.016469);
		Assert.assertEquals(532, Navigation.getDistance(source, target, false));
	}
	@Test
	public void getDurationTest() {
		MapLocation source = new MapLocation(32.777552, 35.020578);
		MapLocation target = new MapLocation(32.778761, 35.016469);
		Assert.assertEquals(82, Navigation.getDuration(source, target, false));
	}
	@Test
	public void  parkingSlotAtParkingAreaTest(){
		
		try{
			MapLocation location = new MapLocation(32.777408, 35.020332); // farest
			ParkingSlot slot1 = new ParkingSlot("upperTaub-slot1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, location, new Date());
			location = new MapLocation(32.777223, 35.020890); // middle
			ParkingSlot slot2 = new ParkingSlot("upperTaub-slot2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, location, new Date());
			location = new MapLocation(32.777195, 35.021281); // closest
			ParkingSlot slot3 = new ParkingSlot("upperTaub-slot3", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, location, new Date());
			
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			slots.add(slot2);
			slots.add(slot3);
			
			location = new MapLocation(32.777466, 35.021094);
			Destination destination = new Destination("Taub", location);
			
			ParkingArea area = new ParkingArea(100,slots, StickersColor.RED);
			try {
				
				User user = new User("3209654"); 
				ParkingSlot result = Navigation.parkingSlotAtParkingArea(user, area, destination);
				Assert.assertEquals(slot3.getName(), result.getName());
				
				slot3.changeStatus(ParkingSlotStatus.TAKEN);
				
				// now result 2 is the closest
			
				result = Navigation.parkingSlotAtParkingArea(user, area, destination);
				Assert.assertEquals(slot2.getName(), result.getName());
				
				area.deleteParseObject();
				slot1.deleteParseObject();
				slot2.deleteParseObject();
				slot3.deleteParseObject();
				
			} catch(LoginException e){
				System.out.println("login exception");
			}
			
		} catch(ParseException e){
			System.out.print("parse exception");
		}
	}
	
	@Test
	public void closestParkingSlotTest(){
		
		try{
			
			// upper taub area + slots
			
			MapLocation location = new MapLocation(32.777408, 35.020332); // farest
			ParkingSlot taubSlot1 = new ParkingSlot("upperTaub-slot1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, location, new Date());
			location = new MapLocation(32.777223, 35.020890); // middle
			ParkingSlot taubSlot2 = new ParkingSlot("upperTaub-slot2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, location, new Date());
			location = new MapLocation(32.777195, 35.021281); // closest
			ParkingSlot taubSlot3 = new ParkingSlot("upperTaub-slot3", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED, location, new Date());
			
			Set<ParkingSlot> taubSlots = new HashSet<ParkingSlot>();
			taubSlots.add(taubSlot1);
			taubSlots.add(taubSlot2);
			taubSlots.add(taubSlot3);
			
			ParkingArea upperTaubArea = new ParkingArea(100,taubSlots, StickersColor.RED);
			
			
			// nesher area + slots
			
			location = new MapLocation(32.774596, 35.029031);
			ParkingSlot nesherSlot1 = new ParkingSlot("nesher-slot1", ParkingSlotStatus.FREE, StickersColor.WHITE, StickersColor.WHITE, location, new Date());
			Set<ParkingSlot> nesherSlots = new HashSet<ParkingSlot>();
			nesherSlots.add(nesherSlot1);
			
			ParkingArea nesherArea = new ParkingArea(101,nesherSlots, StickersColor.WHITE);
			
			// pool area + slots
			
			location = new MapLocation(32.778782, 35.016993); // farest
			ParkingSlot poolSlot1 = new ParkingSlot("pool-slot1", ParkingSlotStatus.FREE, StickersColor.BLUE, StickersColor.BLUE, location, new Date());
			location = new MapLocation(32.778818, 35.019418); // closest
			ParkingSlot poolSlot2 = new ParkingSlot("pool-slot2", ParkingSlotStatus.FREE, StickersColor.BLUE, StickersColor.BLUE, location, new Date());

			
			Set<ParkingSlot> poolSlots = new HashSet<ParkingSlot>();
			poolSlots.add(poolSlot1);
			poolSlots.add(poolSlot2);
			
			ParkingArea poolArea = new ParkingArea(102,poolSlots, StickersColor.BLUE);
			
			Set<ParkingArea> areas = new HashSet<ParkingArea>();
			areas.add(upperTaubArea);
			areas.add(nesherArea);
			areas.add(poolArea);
			
			ParkingAreas parkingAreas = new ParkingAreas(areas); 
			
			location = new MapLocation(32.777466, 35.021094);
			Destination destination = new Destination("Taub", location);
			
			try {
				
				User user = new User("3209654"); 
				// David's sticker type is red, lets change it to blue and will return to original value at end
				user.setSticker(StickersColor.BLUE);
				
				ParkingSlot result = Navigation.closestParkingSlot(user, location, parkingAreas, destination);
				
				user.setSticker(StickersColor.RED);
				
				// taub slots are the closest but since the area is RED and david's sticker is BLUE taub slots won't be checked
				Assert.assertEquals(poolSlot2.getName(), result.getName());
				
				
				
				upperTaubArea.deleteParseObject();
				taubSlot1.deleteParseObject();
				taubSlot2.deleteParseObject();
				taubSlot3.deleteParseObject();
				
				nesherArea.deleteParseObject();
				nesherSlot1.deleteParseObject();

				poolArea.deleteParseObject();
				poolSlot1.deleteParseObject();
				poolSlot2.deleteParseObject();
				
			} catch(LoginException e){
				System.out.println("login exception");
			}
			
		} catch(ParseException e){
			System.out.print("parse exception");
		}
		
	}
	
	@Test
	public void test8() {
		MapLocation location = new MapLocation(32.775041, 35.027084);
		Assert.assertEquals(2, Navigation.getClosestParkingArea(location, false));
	}
	@Test
	public void test9() {
		MapLocation location = new MapLocation(32.776395, 35.020756);
		Assert.assertEquals(4, Navigation.getClosestParkingArea(location, false));
	}
	@Test
	public void test10() {
		
		MapLocation location = new MapLocation(32.778857, 35.018535);
		Assert.assertEquals(1, Navigation.getClosestParkingArea(location, false));
	}
	@Test
	public void test11() {
		
		MapLocation location = new MapLocation(32.780571, 35.023073);
		Assert.assertEquals(3, Navigation.getClosestParkingArea(location, false));
	}
	
	
	
	
	
}
