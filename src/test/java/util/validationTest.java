package util;

import static org.junit.Assert.fail;

import org.junit.Test;

public class validationTest {
	@Test
	public void testIsInt1() {
		String textString = "textNotInt";
		try {
			Validation.isInt(textString);
			fail();
		} catch (Exception e) {
			return;
		}
	}
	
	@Test
	public void testIsInt2() {
		String intString = "123456789";
		try {
			Validation.isInt(intString);
		} catch (Exception e) {
			fail();
		}
	}
}