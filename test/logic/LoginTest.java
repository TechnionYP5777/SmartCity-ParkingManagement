package logic;

import org.junit.Assert;
import org.junit.Test;

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

}
