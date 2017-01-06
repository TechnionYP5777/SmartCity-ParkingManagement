package manager.logic;

import org.junit.Assert;
import org.junit.Test;

import data.management.DBManager;
import data.members.StickersColor;

/**
 * @Author Inbal Matityahu
 */

public class selectAreaTest {
	@Test
	public void test1() {
		DBManager.initialize();
		Assert.assertEquals(1,new SelectAnArea().getNumOfFreeSlotsPerArea(16));
	}
	
	@Test
	public void test2() {
		DBManager.initialize();
		Assert.assertEquals(1,new SelectAnArea().getNumOfTakenSlotsPerArea(16));
	}
	
	@Test
	public void test3() {
		DBManager.initialize();
		Assert.assertEquals(2,new SelectAnArea().getTotalNumOfSlotsPerArea(16));
	}
	
	@Test
	public void test4() {
		DBManager.initialize();
		Assert.assertEquals(StickersColor.RED,new SelectAnArea().getColorOfArea(16));
	}
	
	@Test(expected=RuntimeException.class)
	public void test5() {
		DBManager.initialize();
		System.out.println((new SelectAnArea()).getNumOfFreeSlotsPerArea(100));
	}
	
	@Test(expected=RuntimeException.class)
	public void test6() {
		DBManager.initialize();
		(new SelectAnArea()).getNumOfTakenSlotsPerArea(100);
	}
	
	@Test(expected=RuntimeException.class)
	public void test7() {
		DBManager.initialize();
		(new SelectAnArea()).getTotalNumOfSlotsPerArea(100);
	}
	
	@Test(expected=RuntimeException.class)
	public void test8() {
		DBManager.initialize();
		(new SelectAnArea()).getColorOfArea(100);
	}
}
