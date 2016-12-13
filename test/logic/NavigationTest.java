package logic;
import org.junit.Assert;
import org.junit.Test;

import logic.*;

public class NavigationTest {

	@Test
	public void test() {
		double sourceLat = 32.777400;
		double sourceLon = 35.020178;
		double targetLat = 32.777544;
		double targetLon = 35.020092;
		
		// Might fail while there is traffic in the technion.
		Assert.assertEquals(317,Navigation.getDistance( sourceLat,  sourceLon,  targetLat,  targetLon, false));

	}

}
