package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;
import util.Log;

public class CombineGraphTest {
	
	private static DatabaseManager dbm = Mockito.mock(DatabaseManager.class);
	private static List<ParseObject> parkingSlots = new ArrayList<>();
	
	@BeforeClass
	public static void classSetUp(){
		try {		
			Log.setup();
		} catch (IOException e) {
			System.out.println("Could not set up the logger");
		}
	}
	
	public static void PopulateMokito(){		
		ParseObject p1 = new ParseObject("ParkingSlot");
		p1.put("name", "testSlot1");
		p1.put("location",new ParseGeoPoint(32.777745,35.024));
		p1.put("rank", 1);
		p1.put("rating", 1);
		
		ParseObject p2 = new ParseObject("ParkingSlot");
		p2.put("name", "testSlot2");
		p2.put("location",new ParseGeoPoint(32.777907,35.023828));
		p2.put("rank", 2);
		p2.put("rating", 2);
		
		ParseObject p3 = new ParseObject("ParkingSlot");
		p3.put("name", "testSlot3");
		p3.put("location",new ParseGeoPoint(32.778015,35.021982));
		p3.put("rank", 3);
		p3.put("rating", 3);
		
		// insert all slots to array
    	parkingSlots.add(p1);
    	parkingSlots.add(p2);
    	parkingSlots.add(p3);
		
   		Mockito.when(dbm.getAllObjects("ParkingSlot", 600)).thenReturn(parkingSlots);
	}
	
	@Test
	public void CalculateMap(){
		PopulateMokito();
		Assert.assertEquals(3,
				(new Graph(dbm)).CreatePriceDistanceData(new ParseGeoPoint(32.777533, 35.024231)).size());
		Assert.assertEquals(3,
						(new Graph(dbm)).CreatePriceRanttingData(new ParseGeoPoint(32.777533, 35.024231)).size());
	}
	
	@Test
	public void SameDistanceTwice(){
		// populate twice to make doubles
		PopulateMokito();
		PopulateMokito();
		Assert.assertEquals(3,
				(new Graph(dbm)).CreatePriceDistanceData(new ParseGeoPoint(32.777533, 35.024231)).size());
		Assert.assertEquals(3,
				(new Graph(dbm)).CreatePriceRanttingData(new ParseGeoPoint(32.777533, 35.024231)).size());
	}
}
