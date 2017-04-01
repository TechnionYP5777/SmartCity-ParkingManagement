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

/**
 * @Author Inbal Matityahu
 */

public class QueriesTest {

	@Test
	public void test3() throws ParseException {
		DBManager.initialize();
		final ParkingSlot slot1 = new ParkingSlot("testS", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
				new MapLocation(0, 0), new Date());

		try {
			final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
			query.whereEqualTo("name", "testS");
			final List<ParseObject> slotsList = query.find();
			if (slotsList == null || slotsList.isEmpty())
				throw new RuntimeException("There should be parkingSlot named taub3");

		} catch (final ParseException e) {
			fail();
		}
		slot1.deleteParseObject();
	}

	@Test
	public void test4() {
		DBManager.initialize();
		try {
			final ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date()),
					slot2 = new ParkingSlot("testS2", ParkingSlotStatus.TAKEN, StickersColor.RED, StickersColor.RED,
							new MapLocation(0, 0), new Date());
			final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			slots.add(slot2);

			final ParkingArea area = new ParkingArea(20, "t1", new MapLocation(0, 0), slots, StickersColor.RED);

			DBManager.initialize();
			final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 20);
			final List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(1, new Queries().getNumOfTakenByArea(new ParkingArea(areaList.get(0))));
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
		} catch (final ParseException e) {
			fail();
		}
	}

	@Test
	public void test5() {
		DBManager.initialize();
		try {
			final ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date()),
					slot2 = new ParkingSlot("testS2", ParkingSlotStatus.TAKEN, StickersColor.RED, StickersColor.RED,
							new MapLocation(0, 0), new Date());
			final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			slots.add(slot2);

			final ParkingArea area = new ParkingArea(20, "t1", new MapLocation(0, 0), slots, StickersColor.RED);

			DBManager.initialize();
			final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 20);
			final List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(1, new Queries().getNumOfFreeByArea(new ParkingArea(areaList.get(0))));
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
		} catch (final ParseException e) {
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
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 1);
		try {
			final List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(10, new Queries().getNumOfSlotsByArea(new ParkingArea(areaList.get(0))));
		} catch (final ParseException e) {
			fail();
		}
	}

	@Test
	public void test9() throws ParseException {
		DBManager.initialize();
		try {
			final ParkingSlot slot1 = new ParkingSlot("testS1", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(0, 0), new Date()),
					slot2 = new ParkingSlot("testS2", ParkingSlotStatus.TAKEN, StickersColor.RED, StickersColor.RED,
							new MapLocation(0, 0), new Date());
			final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(slot1);
			slots.add(slot2);

			final ParkingArea area = new ParkingArea(20, "t1", new MapLocation(0, 0), slots, StickersColor.RED);

			DBManager.initialize();
			final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
			query.whereEqualTo("areaId", 20);
			final List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals("testS1",
					new Queries().getParkingslotByArea(new ParkingArea(areaList.get(0))).getName());
			area.deleteParseObject();
			slot1.deleteParseObject();
			slot2.deleteParseObject();
		} catch (final ParseException e) {
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
	public void test20() {
		DBManager.initialize();
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", 1);
		try {
			final List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There should be an area with areaId=1");
			Assert.assertEquals(1, new Queries().returnArea(1).getAreaId());
		} catch (final ParseException e) {
			fail();
		}
	}
}
