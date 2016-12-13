package server;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.Parse;

import data.management.DBManager;

public class serverConnection {

	@Test
	public void test() {
		DBManager.initialize();
		Assert.assertEquals("parkingmanagment", Parse.getApplicationId());
		Assert.assertEquals("2139d-231cb2-738aa", Parse.getRestAPIKey());
	}
}