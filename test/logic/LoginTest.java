package logic;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.StickersColor;

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
		ParseObject testUserObject = new ParseObject("PMUser");
		testUserObject.put("username","Shay");
		testUserObject.put("password","shayS");
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
		String id=testUserObject.getObjectId();
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
	public void test5(){
		Login lg = new Login();
		String uID = lg.userSignUp("Sefi Albo", "sefi987", "0507788999", "3216549", StickersColor.GREEN);
		Assert.assertNotEquals("", uID);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		ParseObject user=null;
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
	public void test6(){
		Login lg = new Login();
		Assert.assertEquals("already exist",lg.UserValueCheck("David", "111", "1234567890", "3209654"));
		
		// name contains integer
		String uID = lg.userSignUp("Zahi Mizrahi1", "Zahi123", "1234567890", "3216549", StickersColor.GREEN);
		Assert.assertEquals("user has integer", uID);
		// short phone number
		uID="";
		uID = lg.userSignUp("Zahi Mizrahi", "Zahi123", "0507777", "3216549", StickersColor.GREEN);
		Assert.assertEquals("phone need to be in size 10", uID);
		// short car number
		uID="";
		uID = lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567890", "321549", StickersColor.GREEN);
		Assert.assertEquals("car need to be in size 7", uID);
		// car number exist
		uID="";
		uID = lg.userSignUp("Zahi Mizrahi", "Zahi123", "0534567900", "3209654", StickersColor.GREEN);
		Assert.assertEquals("already exist", uID);		
	}
	
	@Test
	public void test7(){
		Login lg = new Login();
		// name contains integer
		String uID = lg.userSignUp("Zahi Mizrahi", "Zahi123", "1234567890", "3216549", StickersColor.GREEN);
		Assert.assertEquals("phone should start with 05", uID);
		// short phone number
		uID="";
		uID = lg.userSignUp("Zahi Mizrahi", "Zahi123", "0501a23456", "3216549", StickersColor.GREEN);
		Assert.assertEquals("phone contains only integers", uID);
		// short car number
	}
	
	@Test
	public void test8(){
		Login lg = new Login();		
		Assert.assertTrue(lg.userUpdate("3209654", "David", "0501234567"));
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
		query.whereEqualTo("carNumber", "3296054");
		try {
			List<ParseObject> userList = query.find();
			if (userList != null && !userList.isEmpty()) {
				Assert.assertEquals("David",userList.get(0).getString("username"));
				Assert.assertEquals("0501234567",userList.get(0).getString("phoneNumber"));
			}
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}
		Assert.assertTrue(lg.userUpdate("3296054", "David Cohen", "0508937778"));
		try {
			List<ParseObject> userList = query.find();
			if (userList != null && !userList.isEmpty()) {
				Assert.assertEquals("David Cohen",userList.get(0).getString("username"));
				Assert.assertEquals("0508937778",userList.get(0).getString("phoneNumber"));
			}
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}
	}

}
