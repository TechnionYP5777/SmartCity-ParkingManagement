package logic;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import Exceptions.LoginException;
import data.management.DBManager;
import data.members.MapLocation;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;
import data.members.User;

/**
 * @Author DavidCohen55
 */

public class LoginTest {

	@Test
	public void test01() {
		Assert.assertTrue((new LoginManager()).userLogin("3209654", "David123"));
	}

	@Test
	public void test02() {
		Assert.assertFalse((new LoginManager()).userLogin("3209654", "David1"));
	}

	@Test
	public void test03() {
		Assert.assertFalse((new LoginManager()).userLogin("1111111", "David1"));
	}

	@Test
	public void test04() {
		DBManager.initialize();
		ParseObject testUserObject = new ParseObject("PMUser");
		testUserObject.put("username", "Shay");
		testUserObject.put("password", "shayS");
		testUserObject.put("carNumber", "1234567");
		try {
			testUserObject.save();
		} catch (ParseException e) {
			Assert.fail();
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String id = testUserObject.getObjectId();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		ParseObject ret;
		try {
			ret = query.get(id);
		} catch (ParseException e) {
			Assert.fail();
			return;
		}
		Assert.assertEquals("Shay", ret.getString("username"));
		Assert.assertEquals("shayS", ret.getString("password"));
		Assert.assertEquals("1234567", ret.getString("carNumber"));
		try {
			testUserObject.delete();
			ret = query.get(id);
			Assert.assertEquals(ret, null);
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	@Test
	public void test05() {
		LoginManager lg = new LoginManager();
		String uID = "";
		try {
			uID = lg.userSignUp("Sefi Albo", "sefi987", "0507788999", "3216549", "sefi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e2) {
			Assert.fail();
		}
		Assert.assertNotEquals("", uID);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		ParseObject user = null;
		try {
			user = query.get(uID);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			Assert.fail();
		}
		Assert.assertEquals("Sefi Albo", user.getString("username"));
		Assert.assertEquals("sefi987", user.getString("password"));
		Assert.assertEquals("0507788999", user.getString("phoneNumber"));
		Assert.assertEquals("3216549", user.getString("carNumber"));
		Assert.assertEquals(StickersColor.GREEN.ordinal(), user.getInt("sticker"));
		try {
			lg.deleteUser();
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	@Test
	public void test06() {
		LoginManager lg = new LoginManager();
		Assert.assertEquals("already exist", lg.userValueCheck("David", "1234567890", "david@gmail.com", "3209654"));

		// name contains integer
		try {
			lg.userSignUp("Zahi Mizrahi1", "Zahi123", "1234567890", "3226549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			Assert.assertEquals("user has integer", (e + ""));
		}

		// short phone number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0507777", "3276549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("phone need to be in size 10", (e + ""));
		}
		// short car number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "321549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("car need to be in size 7", (e + ""));
		}

		// car number exist
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567900", "3209654", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("already exist", (e + ""));
		}

	}

	@Test
	public void test07() {
		LoginManager lg = new LoginManager();
		// name contains integer
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "1234567890", "3236549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("phone should start with 05", (e + ""));
		}

		// short phone number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0501a23456", "3266549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("phone contains only integers", (e + ""));
		}
		// short car number
	}

	@Test
	public void test08() {
		LoginManager lg = new LoginManager();
		try {
			Assert.assertTrue(lg.userLogin("3209654", "David123"));
			Assert.assertTrue(lg.userUpdate("3209654", "David", "0501234567", "david@gmail.com", "2222222"));
		} catch (LoginException e1) {
			Assert.fail();
		}
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
		query.whereEqualTo("carNumber", "3296054");
		try {
			List<ParseObject> userList = query.find();
			if (userList != null && !userList.isEmpty()) {
				Assert.assertEquals("David", userList.get(0).getString("username"));
				Assert.assertEquals("0501234567", userList.get(0).getString("phoneNumber"));
			}
		} catch (ParseException e) {
			Assert.fail();
		}
		try {
			Assert.assertTrue(
					lg.userUpdate("2222222", "David Cohen", "0508937778", "david.5581@hotmail.com", "3209654"));
		} catch (LoginException e1) {
			Assert.fail();
		}
		try {
			List<ParseObject> userList = query.find();
			if (userList != null && !userList.isEmpty()) {
				Assert.assertEquals("David Cohen", userList.get(0).getString("username"));
				Assert.assertEquals("0508937778", userList.get(0).getString("phoneNumber"));
			}
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	@Test
	public void test09() {
		LoginManager lg = new LoginManager();
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3416549", "zahi@gmaifl.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("invalid email address", (e + ""));
		}

		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3246549", "zahi@cs.technion.ac.il",
					StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("invalid email address", (e + ""));
		}

		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3256549", "zahi@campus.technion.ac.il",
					StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.fail();
		}

		try {
			lg.deleteUser();
		} catch (ParseException e) {
			Assert.fail();
		}

		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3216549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.fail();
		}

		try {
			lg.deleteUser();
		} catch (ParseException e) {
			Assert.fail();
		}
	}

	@Test
	public void test10() {
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

}
