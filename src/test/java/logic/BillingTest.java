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
import util.Distance;
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
			Assert.assertEquals((20 + color.ordinal() - 15/100)/16, new BasicBilling().calculateCost(color, 15), 10);
		}
	}
	
	@Test
	public void BasicBillingBySlot(){
		ParseGeoPoint gp = new ParseGeoPoint(32.123, 32.123);
		double d = Distance.AirDistance(gp,gp);
		ParkingSlot p;
		try {
			p = new ParkingSlot("testParkingSlot2",dbm);
			Assert.assertEquals((20 + p.getRank().ordinal() - d/100)/16, new BasicBilling().calculateCostBySlot(p, gp), 10);
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
}
