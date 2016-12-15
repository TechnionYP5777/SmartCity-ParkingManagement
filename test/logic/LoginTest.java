package logic;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.ParseUser;

import data.management.DBManager;

/**
 * @Author DavidCohen55
 */

public class LoginTest {

	@Test
	public void test1() {
		Assert.assertTrue((new Login()).userLogin("3209654", "David123"));
	}
	
	@Test
	public void test2() {
		Assert.assertFalse((new Login()).userLogin("3209654", "David1"));
	}
	
	@Test
	public void test3() {
		Assert.assertFalse((new Login()).userLogin("1111111", "David1"));
	}
	
	@Test
	public void test4() {
		DBManager.initialize();
		ParseUser testUserObject = new ParseUser();
		testUserObject.setUsername("Shay");
		testUserObject.setPassword("shayS");
		testUserObject.put("userPass", "shayS");
		testUserObject.put("carNumber", "1234567");
		try {
			testUserObject.signUp();
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String id=testUserObject.getObjectId();
		ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
		ParseObject ret;
		try {
			ret = query.get(id);
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
			return;
		}
		Assert.assertEquals("Shay", ret.getString("username"));
		Assert.assertEquals("shayS", ret.getString("userPass"));
		Assert.assertEquals("1234567", ret.getString("carNumber"));
		try {
			testUserObject.delete();
			ret = query.get(id);
			Assert.assertEquals(ret, null);
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}
	}

}
