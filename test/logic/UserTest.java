package logic;

import java.util.Date;
import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import Exceptions.LoginException;
import data.members.*;

/**
 * @Author DavidCohen55
 */
public class UserTest {

	/*
	 * test that checks that if you update a User parking slot from the user
	 * itself it will change it also in the parse server
	 */
	@Test
	public void test01() {
		User user = null;
		try {
			user = new User("3209654");
		} catch (LoginException e) {
			Assert.fail();
		}
		try {
			if (user != null)
				user.setCurrentParking(new ParkingSlot("DavidSlot", ParkingSlotStatus.FREE, StickersColor.RED,
						StickersColor.RED, new MapLocation(32.778153, 35.021855), new Date()));
		} catch (ParseException e) {
			Assert.fail();
		}

		Assert.assertEquals(user.getCurrentParking().getName(), "DavidSlot");
		try {
			user.getCurrentParking().setColor(StickersColor.BORDEAUX);
		} catch (ParseException e) {
			Assert.fail();
		}

		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		ParseObject park = null;
		try {
			Assert.assertEquals(new ParkingSlot(user.getCurrentParking().getParseObject()).getColor(),
					StickersColor.BORDEAUX);
			park = query.get((user.getCurrentParking().getParseObject()).getObjectId());
		} catch (ParseException e1) {
			Assert.fail();
		}

		Assert.assertEquals(StickersColor.values()[park.getInt("color")], StickersColor.BORDEAUX);
		try {
			user.setCurrentParking(null);
		} catch (ParseException e) {
			Assert.fail();
		}

		try {
			park.delete();
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	/*
	 * test that checks that if you delete the parking slot from the parse
	 * server it will change it also in the user parking slot to null
	 */
	@Test
	public void test02() {
		User user = null;
		try {
			user = new User("3209654");
		} catch (LoginException e) {
			Assert.fail();
		}
		ParkingSlot ps = null;
		try {
			ps = new ParkingSlot("DavidSlot2", ParkingSlotStatus.FREE, StickersColor.RED, StickersColor.RED,
					new MapLocation(32.778153, 35.021855), new Date());
		} catch (ParseException e1) {
			Assert.fail();
		}
		try {
			if (user != null)
				user.setCurrentParking(ps);
		} catch (ParseException e) {
			Assert.fail();
		}

		Assert.assertEquals(user.getCurrentParking().getName(), "DavidSlot2");

		try {
			ps.deleteParseObject();
		} catch (ParseException e) {
			Assert.fail();
		}

		Assert.assertEquals(user.getCurrentParking(), null);
	}

	/*
	 * test that checks that if you update a User parking slot from parse server
	 * it will change it also in the user itself
	 */
	@Test
	public void test03() {
		User user = null;
		try {
			user = new User("3209654");
		} catch (LoginException e) {
			Assert.fail();
		}
		try {
			if (user != null)
				user.setCurrentParking(new ParkingSlot("DavidSlot3", ParkingSlotStatus.FREE, StickersColor.RED,
						StickersColor.RED, new MapLocation(32.778153, 35.021855), new Date()));
		} catch (ParseException e) {
			Assert.fail();
		}
		Assert.assertEquals(user.getCurrentParking().getName(), "DavidSlot3");

		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		ParseObject park = null;
		try {
			park = query.get((user.getCurrentParking().getParseObject()).getObjectId());
		} catch (ParseException e1) {
			Assert.fail();
		}

		Assert.assertEquals(StickersColor.values()[park.getInt("color")], StickersColor.RED);
		Assert.assertEquals(park.getString("name"), "DavidSlot3");
		try {
			park.put("name", "DavidSlot4");
			park.save();
		} catch (ParseException e) {
			Assert.fail();
		}
		Assert.assertEquals(park.getString("name"), "DavidSlot4");

		Assert.assertEquals(user.getCurrentParking().getName(), "DavidSlot4");

		try {
			Assert.assertEquals(new ParkingSlot(user.getCurrentParking().getParseObject()).getName(), "DavidSlot4");
			user.setCurrentParking(null);
			park.delete();
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	/*
	 * test that checks that if you give a User a different parking slot it will
	 * change it
	 */
	@Test
	public void test04() {
		User user = null;
		try {
			user = new User("3209654");
		} catch (LoginException e) {
			Assert.fail();
		}
		try {
			if (user != null)
				user.setCurrentParking(new ParkingSlot("DavidSlot3", ParkingSlotStatus.FREE, StickersColor.RED,
						StickersColor.RED, new MapLocation(32.778153, 35.021855), new Date()));
		} catch (ParseException e) {
			Assert.fail();
		}

		ParseObject park1 = user.getCurrentParking().getParseObject();
		Assert.assertEquals(user.getCurrentParking().getName(), "DavidSlot3");
		Assert.assertEquals(user.getCurrentParking().getColor(), StickersColor.RED);
		Assert.assertEquals(user.getCurrentParking().getStatus(), ParkingSlotStatus.FREE);

		try {
			if (user != null)
				user.setCurrentParking(new ParkingSlot("DavidSlot4", ParkingSlotStatus.TAKEN, StickersColor.GREEN,
						StickersColor.GREEN, new MapLocation(32.778153, 35.021855), new Date()));
		} catch (ParseException e) {
			Assert.fail();
		}
		ParseObject park2 = user.getCurrentParking().getParseObject();
		Assert.assertEquals(user.getCurrentParking().getName(), "DavidSlot4");
		Assert.assertEquals(user.getCurrentParking().getColor(), StickersColor.GREEN);
		Assert.assertEquals(user.getCurrentParking().getStatus(), ParkingSlotStatus.TAKEN);

		try {
			user.setCurrentParking(null);
			park1.delete();
			park2.delete();
		} catch (ParseException e) {
			Assert.fail();
		}
	}

}
