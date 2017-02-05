package manager.logic;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;
import data.members.User;

/**
 * @Author Inbal Matityahu
 */

public class QueriesTest {

	@Test
	public void test1() throws ParseException {
		DBManager.initialize();
		try {
			// insert new User
			new User("query1", "123456", "0541234567", "60606060", "test@gmail.com", StickersColor.BLUE, null);

			// test function

			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "60606060");

			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be an area with carNumber=606060");
			Assert.assertEquals(StickersColor.BLUE, new Queries().getColorByUser(new User(userList.get(0))));

			// remove new ParkingArea and ParkingSlots
			new User(userList.get(0)).deleteParseObject();

		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test2() throws ParseException {
		DBManager.initialize();
		try {
			// insert new User
			new User("test", "123456", "0541234567", "60606060", "test@gmail.com", StickersColor.BLUE, null);

			// test function

			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "60606060");

			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be an area with carNumber=606060");
			Assert.assertEquals(null, new Queries().getParkingslotByUser(new User(userList.get(0))));

			// remove new ParkingArea and ParkingSlots
			new User(userList.get(0)).deleteParseObject();

		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test3() throws ParseException {
		DBManager.initialize();
		ParkingSlot slot1 = new ParkingSlot("testS", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
				new MapLocation(0, 0), new Date());

		try {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
			query.whereEqualTo("name", "testS");
			List<ParseObject> slotsList = query.find();
			if (slotsList == null || slotsList.isEmpty())
				throw new RuntimeException("There should be parkingSlot named taub3");

			Assert.assertEquals(null, new Queries().getUserByParkingslot(new ParkingSlot(slotsList.get(0))));
		} catch (ParseException e) {
			fail();
		}
		slot1.deleteParseObject();
	}

	@Test
	public void test4() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date()),
					slot2 = new ParkingSlot("testS2", ParkingSlotStatus.TAKEN, StickersColor.RED, StickersColor.RED,
							new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			slots.add(slot2);

			ParkingArea area = new ParkingArea(20, "t1", new MapLocation(0, 0), slots, StickersColor.RED);

			DBManager.initialize();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 20);
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(1, new Queries().getNumOfTakenByArea(new ParkingArea(areaList.get(0))));
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test5() {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date()),
					slot2 = new ParkingSlot("testS2", ParkingSlotStatus.TAKEN, StickersColor.RED, StickersColor.RED,
							new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			slots.add(slot2);

			ParkingArea area = new ParkingArea(20, "t1", new MapLocation(0, 0), slots, StickersColor.RED);

			DBManager.initialize();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 20);
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(1, new Queries().getNumOfFreeByArea(new ParkingArea(areaList.get(0))));
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test6() {
		DBManager.initialize();
		Assert.assertEquals(29, new Queries().getNumOfFreeSlots());
	}

	@Test
	public void test7() {
		DBManager.initialize();
		Assert.assertEquals(11, new Queries().getNumOfTakenSlots());
	}

	@Test
	public void test8() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 1);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(10, new Queries().getNumOfSlotsByArea(new ParkingArea(areaList.get(0))));
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test9() throws ParseException {
		DBManager.initialize();
		try {
			ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date()),
					slot2 = new ParkingSlot("testS2", ParkingSlotStatus.TAKEN, StickersColor.RED, StickersColor.RED,
							new MapLocation(0, 0), new Date());
			Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			slots.add(slot2);

			ParkingArea area = new ParkingArea(20, "t1", new MapLocation(0, 0), slots, StickersColor.RED);

			DBManager.initialize();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 20);
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals("testS1",
					(new Queries().getParkingslotByArea(new ParkingArea(areaList.get(0)))).getName());
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test10() throws ParseException {
		try {
			DBManager.initialize();
			// insert new User
			new User("query1", "123456", "0541234567", "60606060", "test@gmail.com", StickersColor.BLUE, null);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "60606060");

			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be an area with carNumber=606060");

			Assert.assertEquals("query1", new Queries().returnUser("60606060").getName());
			// remove new ParkingArea and ParkingSlots
			new User(userList.get(0)).deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test11() {
		try {
			DBManager.initialize();
			// insert new User
			new User("query1", "123456", "0541234567", "60606060", "test@gmail.com", StickersColor.BLUE, null);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "60606060");

			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be an area with carNumber=606060");
			Assert.assertEquals("query1", new Queries().returnUserName("60606060"));

			// remove new ParkingArea and ParkingSlots
			new User(userList.get(0)).deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test12() {
		try {
			DBManager.initialize();
			// insert new User
			new User("query1", "123456", "0541234567", "60606060", "test@gmail.com", StickersColor.BLUE, null);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "60606060");

			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be an area with carNumber=60606060");
			Assert.assertEquals("123456", new Queries().returnUserPassword("60606060"));

			// remove new ParkingArea and ParkingSlots
			new User(userList.get(0)).deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test13() {
		try {
			DBManager.initialize();
			// insert new User
			new User("query1", "123456", "0541234567", "60606060", "test@gmail.com", StickersColor.BLUE, null);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "60606060");

			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be an area with carNumber=60606060");
			Assert.assertEquals("0541234567", new Queries().returnUserPhoneNum("60606060"));

			// remove new ParkingArea and ParkingSlots
			new User(userList.get(0)).deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test14() {
		try {
			DBManager.initialize();
			// insert new User
			new User("query1", "123456", "0541234567", "60606060", "test@gmail.com", StickersColor.BLUE, null);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "60606060");

			List<ParseObject> userList = query.find();
			if (userList == null || userList.isEmpty())
				throw new RuntimeException("There should be an area with carNumber=60606060");
			Assert.assertEquals(StickersColor.BLUE, new Queries().returnUserSticker("60606060"));

			// remove new ParkingArea and ParkingSlots
			new User(userList.get(0)).deleteParseObject();
		} catch (ParseException e) {
			fail();
		}
	}

	@Test
	public void test15() {
		DBManager.initialize();
		try {
			ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
			query.whereEqualTo("name", "taub3");
			List<ParseObject> slotsList = query.find();
			if (slotsList == null || slotsList.isEmpty())
				throw new RuntimeException("There should be parkingSlot named taub3");
			Assert.assertNull(new Queries().returnUserCurrentParking("6060"));

		} catch (ParseException e) {
			fail();
		}

	}

	@Test
	public void test16() throws ParseException {
		DBManager.initialize();
		Assert.assertEquals("taub3", new Queries().returnParkingSlot(new MapLocation(32.777825, 35.021849)).getName());
	}

	@Test
	public void test17() {
		DBManager.initialize();
		Assert.assertEquals(ParkingSlotStatus.FREE,
				new Queries().returnParkingSlotStatus(new MapLocation(32.777825, 35.021849)));
	}

	@Test
	public void test18() {
		DBManager.initialize();
		Assert.assertEquals(StickersColor.RED,
				new Queries().returnParkingSlotColor(new MapLocation(32.777825, 35.021849)));
	}

	@Test
	public void test19() {
		DBManager.initialize();
		Assert.assertEquals(null, new Queries().returnParkingSlotCurrentUser(new MapLocation(32.777825, 35.021849)));
	}

	@Test
	public void test20() {
		DBManager.initialize();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 1);
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(1, new Queries().returnArea(1).getAreaId());
		} catch (ParseException e) {
			fail();
		}
	}
}
