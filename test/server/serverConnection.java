package server;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
/* @author Shay Segal
 * @since 14-12-2016
 */
public class serverConnection {
	@Test
	public void test() {
		DBManager.initialize();
		Assert.assertEquals("parkingmanagment", Parse.getApplicationId());
		Assert.assertEquals("2139d-231cb2-738aa", Parse.getRestAPIKey());
		ParseObject testConnectionObject = new ParseObject("testConnectionObject");
		testConnectionObject.put("integerCheck", 9999);
		testConnectionObject.put("stringCheck", "John Dow");
		testConnectionObject.put("boolCheck", false);
		try {
			testConnectionObject.save();
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}
		try {
			Thread.sleep(100);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		String id=testConnectionObject.getObjectId();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("testConnectionObject");
		ParseObject ret;
		try {
			ret = query.get(id);
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
			return;
		}
		Assert.assertEquals(9999, ret.getInt("integerCheck"));
		Assert.assertEquals("John Dow", ret.getString("stringCheck"));
		Assert.assertEquals(false, ret.getBoolean("boolCheck"));

		try {
			testConnectionObject.delete();
			ret = query.get(id);
			Assert.assertEquals(ret, null);
		} catch (ParseException e) {
			Assert.assertEquals(true, false);
		}

	}
}