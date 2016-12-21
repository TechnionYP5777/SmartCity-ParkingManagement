package logic;
import data.members.*;
import org.junit.Assert;
import org.junit.Test;


public class NavigationTest {

	@Test
	public void test1() {
		MapLocation location = new MapLocation(32.774586, 35.027397);
		Assert.assertEquals(2, Navigation.getClosestParkingArea(location, false));
	}
	/*
	@Test
	public void test2() {
		MapLocation location = new MapLocation(32.776395, 35.020756);
		Assert.assertEquals(4, Navigation.getClosestParkingArea(location, false));
	}
	@Test
	public void test3() {
		
		MapLocation location = new MapLocation(32.778857, 35.018535);
		Assert.assertEquals(1, Navigation.getClosestParkingArea(location, false));
	}
	@Test
	public void test4() {
		
		MapLocation location = new MapLocation(32.780571, 35.023073);
		Assert.assertEquals(3, Navigation.getClosestParkingArea(location, false));
	}
	@Test
	public void test5() {
		
		MapLocation source = new MapLocation(32.778938, 35.019168);
		MapLocation target = new MapLocation(32.778758, 35.016368);
		Assert.assertEquals(265, Navigation.getDistance(source, target, false));
	}
	*/
	
	
	
	
}
