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
import data.members.ParkingSlot;

import data.members.StickersColor;
import data.members.User;


public class QueriesTest {

	@Test
	public void test1() throws ParseException {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		query.whereEqualTo("carNumber", "1234567");
		try {
			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be a user with carNumber="+ 1234567);
			Assert.assertEquals(StickersColor.WHITE ,new Queries().getColorByUser(new User(userList.get(0))));
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test2() throws ParseException {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		query.whereEqualTo("carNumber", "1234567");
		try {
			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be a user with carNumber="+ 1234567);
			Assert.assertEquals(null ,new Queries().getParkingslotByUser(new User(userList.get(0))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test3() throws ParseException {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", "slot7");
		try {
			List<ParseObject> slotsList = query.find();
			if (slotsList == null || slotsList.isEmpty())
				throw new RuntimeException("There should be a user with carNumber="+ 1234567);
			Assert.assertEquals(null ,new Queries().getUserByParkingslot(new ParkingSlot(slotsList.get(0))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test4() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(1,new Queries().getNumOfTakenByArea((new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test5() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(1,(new Queries().getNumOfFreeByArea(new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test6() {
		DBManager.initialize();
		Assert.assertEquals(27, new Queries().getNumOfFreeSlots());
	}
	
	@Test
	public void test7() {
		DBManager.initialize();
		Assert.assertEquals(3, new Queries().getNumOfTakenSlots());
	}
	
	@Test
	public void test8() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals(2,(new Queries().getNumOfSlotsByArea(new ParkingArea(areaList.get(0)))));
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test9() throws ParseException {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 16);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId="+ 0);
			Assert.assertEquals("slot7",(new Queries().getParkingslotByArea(new ParkingArea(areaList.get(0)))).getName());
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test10() throws ParseException {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		query.whereEqualTo("carNumber", "1234567");
		try {
			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be a user with carNumber="+ 1234567);
			Assert.assertEquals("Shahar" ,new Queries().returnUser(new User(userList.get(0)).getCarNumber()).getName());
		} catch (ParseException e) {
			fail();
		}
	}
	
	@Test
	public void test11() {
		DBManager.initialize();
		Assert.assertEquals("Shahar",(new Queries().returnUserName("1234567")));
	}
}
