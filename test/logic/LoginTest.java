package logic;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import Exceptions.LoginException;
import data.management.DBManager;
import data.members.StickersColor;

/**
 * @Author DavidCohen55
 */

public class LoginTest {

	@Test
	public void test1() {
		try {
			Assert.assertTrue((new Login()).userLogin("3209654", "David123"));
		} catch (LoginException e) {
			System.out.println(e + " test1");
			Assert.assertEquals(true, false);
		}
	}

	@Test
	public void test2() {
		try {
			Assert.assertFalse((new Login()).userLogin("3209654", "David1"));
		} catch (LoginException e) {
			System.out.println(e + " test2");
			Assert.assertEquals(true, false);
		}
	}

	@Test
	public void test3() {
		try {
			Assert.assertFalse((new Login()).userLogin("1111111", "David1"));
		} catch (LoginException e) {
			System.out.println(e + " test3");
			Assert.assertEquals(true, false);
		}
	}

	@Test
	public void test4() {
		DBManager.initialize();
		ParseObject testUserObject = new ParseObject("PMUser");
		testUserObject.put("username", "Shay");
		testUserObject.put("password", "shayS");
		testUserObject.put("carNumber", "1234567");
		try {
			testUserObject.save();
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
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
			Assert.assertEquals(true, false);
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
			Assert.assertEquals(true, false);
		}
	}

	@Test
	public void test5() {
		Login lg = new Login();
		String uID = "";
		try {
			uID = lg.userSignUp("Sefi Albo", "sefi987", "0507788999", "3216549", StickersColor.GREEN);
		} catch (LoginException e2) {
			Assert.assertEquals(true, false);
		}
		Assert.assertNotEquals("", uID);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		ParseObject user = null;
		try {
			user = query.get(uID);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			Assert.assertEquals(true, false);
		}
		Assert.assertEquals("Sefi Albo", user.getString("username"));
		Assert.assertEquals("sefi987", user.getString("password"));
		Assert.assertEquals("0507788999", user.getString("phoneNumber"));
		Assert.assertEquals("3216549", user.getString("carNumber"));
		Assert.assertEquals(StickersColor.GREEN.ordinal(), user.getInt("sticker"));
		try {
			lg.deleteUser();
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}
	}

	@Test
	public void test6() {
		Login lg = new Login();
		Assert.assertEquals("already exist", lg.UserValueCheck("David", "111", "1234567890", "3209654"));

		// name contains integer
		try {
			lg.userSignUp("Zahi Mizrahi1", "Zahi123", "1234567890", "3216549", StickersColor.GREEN);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			Assert.assertEquals("user has integer", (e + ""));
		}

		// short phone number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0507777", "3216549", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("phone need to be in size 10", (e + ""));
		}
		// short car number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "321549", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("car need to be in size 7", (e + ""));
		}

		// car number exist
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567900", "3209654", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("already exist", (e + ""));
		}

	}

	@Test
	public void test7() {
		Login lg = new Login();
		// name contains integer
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "1234567890", "3216549", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("phone should start with 05", (e + ""));
		}

		// short phone number
		try {
			lg.userSignUp("Zahi Mizrahi", "Zahi123", "0501a23456", "3216549", StickersColor.GREEN);
		} catch (LoginException e) {
			Assert.assertEquals("phone contains only integers", (e + ""));
		}
		// short car number
	}

	@Test
	public void test8() {
		Login lg = new Login();
		try {
			Assert.assertTrue(lg.userUpdate("3209654", "David", "0501234567"));
		} catch (LoginException e1) {
			Assert.assertEquals(true, false);
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
			Assert.assertEquals(true, false);
		}
		try {
			Assert.assertTrue(lg.userUpdate("3296054", "David Cohen", "0508937778"));
		} catch (LoginException e1) {
			Assert.assertEquals(true, false);
		}
		try {
			List<ParseObject> userList = query.find();
			if (userList != null && !userList.isEmpty()) {
				Assert.assertEquals("David Cohen", userList.get(0).getString("username"));
				Assert.assertEquals("0508937778", userList.get(0).getString("phoneNumber"));
			}
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}
	}

}
