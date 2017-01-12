package logic;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import Exceptions.LoginException;
import data.management.DBManager;
import data.members.StickersColor;
import data.members.User;

/**
 * @Author DavidCohen55
 */

public class LoginTest {

	@Before
	public void BeforeLoginTest() {
		try {
			// making a new user in the database for all of the tests
			new User("Test User", "Test", "0500000000", "0000000", "test@gmail.com", StickersColor.BLUE, null);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test01() {
		Assert.assertTrue((new LoginManager()).userLogin("0000000", "Test"));
	}

	@Test
	public void test02() {
		Assert.assertFalse((new LoginManager()).userLogin("0000000", "David1"));
	}

	@Test
	public void test03() {
		Assert.assertFalse((new LoginManager()).userLogin("0000001", "David1"));
	}

	@Test
	public void test04() {
		DBManager.initialize();
		try {
			String id = (new User("0000000")).getObjectId();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			ParseObject ret = query.get(id);
			Assert.assertEquals("Test User", ret.getString("username"));
			Assert.assertEquals("Test", ret.getString("password"));
			Assert.assertEquals("0000000", ret.getString("carNumber"));
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test05() {
		LoginManager lg = new LoginManager();
		String uID = "";
		try {
			uID = lg.userSignUp("Sefi Albo", "sefi987", "0507788999", "3216549", "sefi@gmail.com", StickersColor.GREEN);

			Assert.assertNotEquals("", uID);
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			ParseObject user = query.get(uID);
			Assert.assertEquals("Sefi Albo", user.getString("username"));
			Assert.assertEquals("sefi987", user.getString("password"));
			Assert.assertEquals("0507788999", user.getString("phoneNumber"));
			Assert.assertEquals("3216549", user.getString("carNumber"));
			Assert.assertEquals(StickersColor.GREEN.ordinal(), user.getInt("sticker"));
			lg.deleteUser();
		} catch (LoginException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void test06() {
		LoginManager lg = new LoginManager();
		Assert.assertEquals("User already exist",
				lg.userValueCheck("David", "1234567890", "david@gmail.com", "0000000"));

		// name contains integer
		try {
			lg.userSignUp("Zahi Mizrahi1", "Zahi123", "1234567890", "3226549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			Assert.assertEquals("User has integer", (e + ""));
		}

		// short phone number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0507777", "3276549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("Phone need to be in size 10", (e + ""));
		}
		// short car number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "321549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("Car need to be in size 7", (e + ""));
		}

		// car number exist
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567900", "3209654", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("User already exist", (e + ""));
		}
	}

	@Test
	public void test07() {
		LoginManager lg = new LoginManager();
		// name contains integer
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "1234567890", "3236549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("Phone should start with 05", (e + ""));
		}

		// short phone number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0501a23456", "3266549", "zahi@gmail.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("Phone contains only integers", (e + ""));
		}
	}

	@Test
	public void test08() {
		LoginManager lg = new LoginManager();
		try {
			Assert.assertTrue(lg.userLogin("0000000", "Test"));
			Assert.assertTrue(lg.userUpdate("0000000", "Test David", "0501234567", "david@gmail.com", "2222222", null));
			ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
			query.whereEqualTo("carNumber", "2222222");
			List<ParseObject> userList = query.find();
			if (userList != null && !userList.isEmpty()) {
				Assert.assertEquals("Test David", userList.get(0).getString("username"));
				Assert.assertEquals("0501234567", userList.get(0).getString("phoneNumber"));
			}
			Assert.assertTrue(lg.userUpdate("2222222", "Test User", "0500000000", "test@gmail.com", "0000000", null));
			List<ParseObject> userList1 = query.find();
			if (userList1 != null && !userList1.isEmpty()) {
				Assert.assertEquals("Test User", userList1.get(0).getString("username"));
				Assert.assertEquals("0500000000", userList1.get(0).getString("phoneNumber"));
			}
		} catch (LoginException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void test09() {
		LoginManager lg = new LoginManager();
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3416549", "zahi@gmaifl.com", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("Invalid email address", (e + ""));
		}

		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3246549", "zahi@cs.technion.ac.il",
					StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("Invalid email address", (e + ""));
		}

		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3256549", "zahi@campus.technion.ac.il",
					StickersColor.GREEN);
			lg.deleteUser();
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "3216549", "zahi@gmail.com", StickersColor.GREEN);
			lg.deleteUser();

		} catch (LoginException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (ParseException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@After
	public void AfterLoginTest() {
		try {
			// delete the template user from the database
			(new User("0000000")).deleteParseObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
