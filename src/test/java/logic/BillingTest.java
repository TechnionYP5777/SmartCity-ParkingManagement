package logic;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;

import data.management.DatabaseManager;
import data.members.Area;
import data.members.MapLocation;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;
import util.Log;

public class BillingTest {
	static DatabaseManager dbm = Mockito.mock(DatabaseManager.class);

	@BeforeClass
	public static void classSetUp(){
		try {		
			Log.setup();
		} catch (IOException e) {
			System.out.println("Could not set up the logger");
		}
	}
	
	@Test
	public void UniformBilling(){
		for (StickersColor color : StickersColor.values())
			Assert.assertEquals(20, (int) new UniformBilling().calculateCost(color, Math.random()));
		
	}
	
	@Test
	public void UniformBillingBySlot(){
		// Create parkingSlot
		ParseGeoPoint gp1 =  new ParseGeoPoint(12.345, 12.345);		
		ParkingSlot p;
		try {
			p = CreateTestParkingSlot();
			
			//Calculate the price
			Assert.assertEquals(20, (int)new UniformBilling().calculateCostBySlot(p,gp1));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void BasicBilling(){
		for (StickersColor color : StickersColor.values()) {
			Assert.assertEquals(20 + 10 * color.ordinal() - 15, (int) new BasicBilling().calculateCost(color, 15));
		}
	}
	
	@Test
	public void BasicBillingBySlot(){
		ParseGeoPoint gp = new ParseGeoPoint(32.123, 32.123), gp1 = new ParseGeoPoint(12.345, 12.345);
		double d = Distance(gp,gp1);
		ParkingSlot p;
		try {
			p = new ParkingSlot("testParkingSlot2",dbm);
			for (StickersColor color : StickersColor.values())
				Assert.assertEquals(20 + 10 * color.ordinal() - d, new BasicBilling().calculateCostBySlot(p, gp1), 100);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/* PRIVATE METHODS */
	
	// Create test parkingSlot
	private static ParkingSlot CreateTestParkingSlot() throws ParseException{		
		Map<String, Object> keys = new HashMap<>(), fields = new HashMap<>();
		keys.put("name", "testParkingSlot2");	
		fields.put("status", ParkingSlotStatus.FREE.ordinal());
		fields.put("rank", StickersColor.GREEN.ordinal());
		fields.put("defaultColor", StickersColor.GREEN.ordinal());
		fields.put("location",new ParseGeoPoint(32.123, 32.123));
		fields.put("endTime", new Date());
		fields.put("area",  Area.TAUB.ordinal());
		fields.put("rating", 10);
		fields.put("numOfVoters", 2);
		fields.put("name", "testParkingSlot2");
		Mockito.when(dbm.getObjectFieldsByKey("ParkingSlot",keys)).thenReturn(fields);
		return new ParkingSlot("testParkingSlot2", ParkingSlotStatus.FREE, StickersColor.GREEN, 
					StickersColor.GREEN, new MapLocation(32.123, 32.123), new Date(), Area.TAUB,10,2,dbm);
	}
	
	// Calculate the distance between a parking slot, and the destination
		private static double Distance(ParseGeoPoint parkingSlot, ParseGeoPoint destination) {
			double lat1 = parkingSlot.getLatitude(), lat2 = destination.getLatitude();
			double lon1 = parkingSlot.getLongitude(), lon2 = destination.getLongitude();
			final int R = 6371; // Radius of the earth

		    double latDistance = Math.toRadians(lat2 - lat1), lonDistance = Math.toRadians(lon2 - lon1);
		    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1))
					* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2),
					c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    return R * c * 1000;
		}
}
