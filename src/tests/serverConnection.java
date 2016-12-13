package tests;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.Parse;

public class serverConnection {

	@Test
	public void test() {
		Parse.initialize("parkingmanagment", "2139d-231cb2-738aa");
		Assert.assertEquals("parkingmanagment", Parse.getApplicationId());
		Assert.assertEquals("2139d-231cb2-738aa", Parse.getRestAPIKey());
	}

}
