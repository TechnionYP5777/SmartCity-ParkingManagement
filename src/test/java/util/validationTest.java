package util;

import org.junit.Assert;
import org.junit.Test;

public class validationTest {
	@Test
	public void testIsInt() throws Exception {
		Assert.assertEquals(false, Validation.isInt("textNotInt"));
		Assert.assertEquals(true, Validation.isInt("123456789"));
	}
	
	@Test
	public void testIsFullNamePattern() {
		Assert.assertEquals(true, Validation.isFullNamePattern("Gadi Eli"));
		Assert.assertEquals(true, Validation.isFullNamePattern("Gadi Ben-Ari"));
		Assert.assertEquals(false, Validation.isFullNamePattern("Gadi Eli2"));
		Assert.assertEquals(false, Validation.isFullNamePattern("Gadi#Eli"));
	}
	
	@Test
	public void testIsLicensePlatePattern() {
		Assert.assertEquals(true, Validation.isLicensePlatePattern("1122211"));
		Assert.assertEquals(true, Validation.isLicensePlatePattern("111222"));
		Assert.assertEquals(false, Validation.isLicensePlatePattern("1122"));
		Assert.assertEquals(false, Validation.isLicensePlatePattern("aPlateNmber"));
	}
}