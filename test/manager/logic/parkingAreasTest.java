package manager.logic;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.ParkingArea;

public class parkingAreasTest {
	@Test
	public void test1() {
		DBManager.initialize();
		Assert.assertEquals(27, new ParkingAreas().getNumOfFreeSlots());
	}
	
	@Test
	public void test2() {
		DBManager.initialize();
		Assert.assertEquals(3, new ParkingAreas().getNumOfTakenSlots());
	}
	
	@Test
	public void test() {
		DBManager.initialize();
		Assert.assertEquals(19, new ParkingAreas().getParkingAreas().size());
	}
	
	@Test
	public void test3(){
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 3);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(2,(new ParkingAreas().getNumOfFreeByArea(new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test4(){
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 3);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(0,(new ParkingAreas().getNumOfTakenByArea(new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test5(){
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(1,(new ParkingAreas().getNumOfTakenByArea(new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test6(){
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(1,(new ParkingAreas().getNumOfFreeByArea(new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test7(){
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(2,(new ParkingAreas().getNumOfSlotsByArea(new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test8(){
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals("slot7",(new ParkingAreas().getParkingslotByArea(new ParkingArea(areaList.get(0)))).getName());
		} catch (ParseException e) {
			fail();
		}
	}
}
