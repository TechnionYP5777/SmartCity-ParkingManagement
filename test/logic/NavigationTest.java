package logic;
import org.junit.Assert;
import org.junit.Test;

import logic.*;

public class NavigationTest {

	@Test
	public void test1() {
		// Might fail while there is traffic in the technion.
		Assert.assertEquals(317, Navigation.getDistance(32.777400, 35.020178, 32.777544, 35.020092, false));
		
		
	}
	@Test
	public void test2() {
		Assert.assertEquals(0, Navigation.getClosestParkingArea(32.777297, 35.019971, false));
	}
	@Test
	public void test3() {
		Assert.assertEquals(1, Navigation.getClosestParkingArea(32.778848, 35.017095, false));
	}
}
