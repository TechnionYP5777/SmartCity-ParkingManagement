package manager.logic;

import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.DurationType;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

/**
 * @Author Inbal Matityahu
 */

public class manualUpdateTest {

	private static final String END_TIME = "endTime";

	@Before
	public void setUpTest() throws ParseException {
		DBManager.initialize();
		
		//create new area
		ParkingSlot slot1 = new ParkingSlot("testManual1", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("testManual2", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingSlot slot3 = new ParkingSlot("testManual3", ParkingSlotStatus.FREE, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot3);
		ParkingSlot slot4 = new ParkingSlot("testManual4", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot4);
		new ParkingArea(20, "testManual",new MapLocation(0, 0),slots, StickersColor.GREEN);
		
	}

	@Test
	public void test1() throws ParseException, java.text.ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList != null && !areaList.isEmpty()) {
				// check permanent update
				ManualUpdateArea change = new ManualUpdateArea(new ParkingArea(areaList.get(0)), 1, StickersColor.RED, DurationType.PERMANENTLY, null);
				change.updateArea();
				ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingArea");
				query2.whereEqualTo("areaId", 20);
				try {
					List<ParseObject> areaList2 = query2.find();
					if (areaList2 != null && !areaList2.isEmpty()) {
						Assert.assertEquals(StickersColor.RED.ordinal(), areaList2.get(0).getInt("color"));
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(areaList2.get(0).getDate("endDate"));
						Assert.assertEquals(31,calendar.get(Calendar.DAY_OF_MONTH));
						Assert.assertEquals(12, calendar.get(Calendar.MONTH) + 1);
						Assert.assertEquals(9999,calendar.get(Calendar.YEAR));			
						
						int counterRed=0;
						Set<ParkingSlot> s = new ParkingArea(areaList2.get(0)).convertToSlots(new ParkingArea(areaList2.get(0)).getAllSlots());
						for (ParkingSlot ¢: s)
							if (¢.getColor().equals(StickersColor.RED))
								++counterRed;
						Assert.assertEquals(1,counterRed);			
						
					}
				} catch (ParseException e) {
					Assert.fail();
				}
				
			}
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	@Test(expected=RuntimeException.class)
	public void test2(){
		//demand area does not exist
		new ManualUpdate().updateArea(100, 1, StickersColor.BLUE, DurationType.TEMPORARY, null);
	}
	
	@Test(expected=RuntimeException.class)
	public void test3(){
		//demand amount does not possible
		new ManualUpdate().updateArea(20, 100, StickersColor.BLUE, DurationType.PERMANENTLY, null);
	}
	
	@Test(expected=RuntimeException.class)
	public void test4(){
		//there is no change in colors
		new ManualUpdate().updateArea(20, 1, StickersColor.GREEN, DurationType.PERMANENTLY, null);
	}
	
	@Test(expected=RuntimeException.class)
	public void test5() throws ParseException, Exception{
		//end time is not in the future
		new ManualUpdate().updateArea(20, 1, StickersColor.BLUE, DurationType.TEMPORARY, ((new SimpleDateFormat("yyyy-MM-dd")).parse("2017-01-01")));
	}
	
	@Test(expected=RuntimeException.class)
	public void test6(){
		//fail DurationType.TEMPORARY && untilDate==null
		new ManualUpdate().updateArea(20, 1, StickersColor.RED, DurationType.TEMPORARY, null);
	}
	
	@Test
	public void test7() throws Exception{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList != null && !areaList.isEmpty()) {
				// check temporary update
				(new ManualUpdateArea(new ParkingArea(areaList.get(0)), 1, StickersColor.BLUE, DurationType.TEMPORARY,
						(new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01"))).updateArea();
				
				ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingArea");
				query2.whereEqualTo("areaId", 20);
				try {
					List<ParseObject> areaList2 = query2.find();
					if (areaList2 != null && !areaList2.isEmpty()) {
						Assert.assertEquals(StickersColor.BLUE.ordinal(), areaList2.get(0).getInt("color"));
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(areaList2.get(0).getDate("endDate"));
						Assert.assertEquals(1,calendar.get(Calendar.DAY_OF_MONTH));
						Assert.assertEquals(1, calendar.get(Calendar.MONTH) + 1);
						Assert.assertEquals(2018,calendar.get(Calendar.YEAR));
						
						int counterRed=0;
						Set<ParkingSlot> s = new ParkingArea(areaList2.get(0)).convertToSlots(new ParkingArea(areaList2.get(0)).getAllSlots());
						for (ParkingSlot ¢: s)
							if (¢.getColor().equals(StickersColor.BLUE))
								++counterRed;
						Assert.assertEquals(1,counterRed);			
						
					}
				} catch (ParseException e) {
					Assert.fail();
				}
			}
		}catch (ParseException e) {
			Assert.fail();
		}	
	}
	
	@Test
	public void test8() throws Exception{
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList != null && !areaList.isEmpty()) {
				// check temporary update
				(new ManualUpdateArea(new ParkingArea(areaList.get(0)), 2, StickersColor.BLUE, DurationType.TEMPORARY,
						(new SimpleDateFormat("yyyy-MM-dd")).parse("2018-01-01"))).updateArea();
		
				ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingArea");
				query2.whereEqualTo("areaId", 20);
				try {
					List<ParseObject> areaList2 = query2.find();
					if (areaList2 != null && !areaList2.isEmpty()) {
						Assert.assertEquals(StickersColor.BLUE.ordinal(), areaList2.get(0).getInt("color"));
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(areaList2.get(0).getDate("endDate"));
						Assert.assertEquals(1,calendar.get(Calendar.DAY_OF_MONTH));
						Assert.assertEquals(1, calendar.get(Calendar.MONTH) + 1);
						Assert.assertEquals(2018,calendar.get(Calendar.YEAR));
						
						int counterRed=0;
						Set<ParkingSlot> s = new ParkingArea(areaList2.get(0)).convertToSlots(new ParkingArea(areaList2.get(0)).getAllSlots());
						for (ParkingSlot ¢: s)
							if (¢.getColor().equals(StickersColor.BLUE))
								++counterRed;
						Assert.assertEquals(2,counterRed);			
						
					}
				} catch (ParseException e) {
					Assert.fail();
				}
			}
		}catch (ParseException e) {
			Assert.fail();
		}	
	}
	
	@Test
	public void checkUpdateSlot() throws ParseException{
		ParseQuery<ParseObject> querySlot = ParseQuery.getQuery("ParkingSlot");
		querySlot.whereEqualTo("name", "testManual1");
		List<ParseObject> slotList = querySlot.find();
		if (slotList == null || slotList.isEmpty())
			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
		//System.out.println(slotList.get(0).getDate(END_TIME));
		//System.out.println(slotList.get(0).getInt("color"));
		new ManualUpdateArea().updateParkingSlot(new ParkingSlot(slotList.get(0)), StickersColor.RED, null, DurationType.PERMANENTLY);
		
		ParseQuery<ParseObject> querySlot2 = ParseQuery.getQuery("ParkingSlot");
		querySlot2.whereEqualTo("name", "testManual1");
		List<ParseObject> slotList2 = querySlot2.find();
		if (slotList2 == null || slotList2.isEmpty())
			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
		Date date = slotList2.get(0).getDate(END_TIME);
		//System.out.println(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);//System.out.println(slotList2.get(0).getInt("color"));
		Assert.assertEquals(StickersColor.RED.ordinal(), slotList2.get(0).getInt("color"));
		Assert.assertEquals(31,calendar.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(12, calendar.get(Calendar.MONTH) + 1);
		Assert.assertEquals(9999,calendar.get(Calendar.YEAR));

	}
	
	@Test
	public void test10() throws ParseException, java.text.ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 20);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList != null && !areaList.isEmpty()) {
				// check permanent update
				ManualUpdateArea change = new ManualUpdateArea(new ParkingArea(areaList.get(0)), 2, StickersColor.RED, DurationType.PERMANENTLY, null);
				change.updateArea();
				ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingArea");
				query2.whereEqualTo("areaId", 20);
				try {
					List<ParseObject> areaList2 = query2.find();
					if (areaList2 != null && !areaList2.isEmpty()) {
						Assert.assertEquals(StickersColor.RED.ordinal(), areaList2.get(0).getInt("color"));
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(areaList2.get(0).getDate("endDate"));
						Assert.assertEquals(31,calendar.get(Calendar.DAY_OF_MONTH));
						Assert.assertEquals(12, calendar.get(Calendar.MONTH) + 1);
						Assert.assertEquals(9999,calendar.get(Calendar.YEAR));			
						
						int counterRed=0;
						Set<ParkingSlot> s = new ParkingArea(areaList2.get(0)).convertToSlots(new ParkingArea(areaList2.get(0)).getAllSlots());
						for (ParkingSlot ¢: s)
							if (¢.getColor().equals(StickersColor.RED))
								++counterRed;
						Assert.assertEquals(2,counterRed);			
						
					}
				} catch (ParseException e) {
					Assert.fail();
				}
				
			}
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	
	@Test
	public void checkUpdateAreaDetails(){
		try {
		//insert new ParkingArea and ParkingSlots
		ParkingSlot slot1 = new ParkingSlot("checkTest1", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(32.123, 32.123), new Date());
		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		slots.add(slot1);
		ParkingSlot slot2 = new ParkingSlot("checkTest2", ParkingSlotStatus.TAKEN, StickersColor.GREEN, StickersColor.GREEN,
				new MapLocation(0, 0), new Date());
		slots.add(slot2);
		ParkingArea area = new ParkingArea(30,"t1", new MapLocation(0, 0), slots, StickersColor.GREEN);
		assertNotNull(area);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 30);
		
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=" + 0);
			(new ManualUpdateArea(area, 1, StickersColor.WHITE, DurationType.PERMANENTLY, null)).updateAreaDetails();

			ParseQuery<ParseObject> queryArea2 = ParseQuery.getQuery("ParkingArea");
			queryArea2.whereEqualTo("areaId", 30);
			List<ParseObject> areaList3 = queryArea2.find();
			if (areaList3 == null || areaList3.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			Assert.assertEquals(StickersColor.WHITE.ordinal(), areaList3.get(0).getInt("color"));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(areaList3.get(0).getDate("endDate"));
			Assert.assertEquals(31,calendar.get(Calendar.DAY_OF_MONTH));
			Assert.assertEquals(12, calendar.get(Calendar.MONTH) + 1);
			Assert.assertEquals(9999,calendar.get(Calendar.YEAR));			
			
			//delete area
			ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
			queryArea.whereEqualTo("areaId", 30);
			List<ParseObject> areaList2 = queryArea.find();
			if (areaList2 == null || areaList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingArea table doesnt found");
			new ParkingArea(areaList2.get(0)).deleteParseObject();
			
			//delete slots
			ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
			query2.whereEqualTo("name", "checkTest1");
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found - checkTest2");
			new ParkingSlot(slotList.get(0)).deleteParseObject();
			
			ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
			query3.whereEqualTo("name", "checkTest2");
			List<ParseObject> slotList2 = query3.find();
			if (slotList2 == null || slotList2.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found - checkTest2");
			new ParkingSlot(slotList2.get(0)).deleteParseObject();
		} catch (Exception e) {
			Assert.fail();
		}

	}
	
	@After
	public void finishTest() throws ParseException {
		//delete objects
		ParseQuery<ParseObject> queryArea = ParseQuery.getQuery("ParkingArea");
		queryArea.whereEqualTo("areaId", 20);
		List<ParseObject> areaList = queryArea.find();
		if (areaList == null || areaList.isEmpty())
			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
		new ParkingArea(areaList.get(0)).deleteParseObject();
		
		
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
		query2.whereEqualTo("name", "testManual1");
		List<ParseObject> slotList = query2.find();
		if (slotList == null || slotList.isEmpty())
			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
		new ParkingSlot(slotList.get(0)).deleteParseObject();
		
		ParseQuery<ParseObject> query3 = ParseQuery.getQuery("ParkingSlot");
		query3.whereEqualTo("name", "testManual2");
		List<ParseObject> slotList2 = query3.find();
		if (slotList2 == null || slotList2.isEmpty())
			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
		new ParkingSlot(slotList2.get(0)).deleteParseObject();
	
		ParseQuery<ParseObject> query4 = ParseQuery.getQuery("ParkingSlot");
		query4.whereEqualTo("name", "testManual3");
		List<ParseObject> slotList3 = query4.find();
		if (slotList3 == null || slotList3.isEmpty())
			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
		new ParkingSlot(slotList3.get(0)).deleteParseObject();
		
		ParseQuery<ParseObject> query5 = ParseQuery.getQuery("ParkingSlot");
		query5.whereEqualTo("name", "testManual4");
		List<ParseObject> slotList4 = query5.find();
		if (slotList4 == null || slotList4.isEmpty())
			throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
		new ParkingSlot(slotList4.get(0)).deleteParseObject();
		
	}
}

