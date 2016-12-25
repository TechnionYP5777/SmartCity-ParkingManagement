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

		Assert.assertEquals(new ParkingSlot(user.getCurrentParking().getParseObject()).getColor(),
				StickersColor.BORDEAUX);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		ParseObject park = null;
		try {
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

}
