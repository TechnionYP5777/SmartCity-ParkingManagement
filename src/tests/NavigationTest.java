package tests;
import Logic.*;


import org.junit.Assert;
import org.junit.Test;

public class NavigationTest {

	@Test
	public void test() {
		// Might fail while there is traffic in the technion.
		Assert.assertEquals(317, Navigation.getDistance(32.777400, 35.020178, 32.777544, 35.020092, false));

	}

}
