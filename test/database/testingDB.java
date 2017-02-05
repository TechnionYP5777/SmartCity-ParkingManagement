package database;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

public class testingDB {

	private static final String appId = "parkingmanagment";
	private static final String restKey = "2139d-231cb2-738aa";
	private static final String serverUrl = "https://pm-parse-server.herokuapp.com/parse";

	public void initialize() {
		Parse.initialize(appId, restKey, serverUrl);
	}

	@Before
	public void setUp() {
		initialize();
	}

	// The result of this test should be a new class (Named test) in the DB that
	// contains one raw.
	// Please verify the success of the test in the DB, and delete the class
	// afterwards.
	// Automation - to be added
	@Test
	public void test1() {
		// initialize();
		ParseObject test = new ParseObject("test");
		test.put("testValue0", 1);
		try {
			test.save();
			test.delete();
		} catch (ParseException ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}

	// Add an array to the DB
	@Test
	public void test2() {
		// initialize();
		ParseObject test = new ParseObject("test");
		test.put("testValue0", 2);
		List<String> types = new ArrayList<String>();
		types.add("type1");
		types.add("type2");
		types.add("type3");
		test.put("types", types);

		try {
			test.save();
			test.delete();
		} catch (ParseException ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}

	// Add a reference to another class
	@Test
	public void test3() {
		// initialize();
		ParseObject test = new ParseObject("test");
		test.put("testValue0", 3);

		ParseObject subTest = new ParseObject("subTest");
		subTest.put("value", 3);

		try {
			subTest.save();

			test.put("subClass", subTest);
			test.save();

			test.delete();
			subTest.delete();
		} catch (ParseException ¢) {
			¢.printStackTrace();
			Assert.fail();
		}

	}

	// Add a reference to another class that already exist in the DB (query it)
	@Test
	public void test4() {
		// initialize();
		ParseObject test = new ParseObject("test");
		test.put("testValue0", 4);

		ParseObject subTest = new ParseObject("subTest");
		subTest.put("value", 4);
		int val = 0;
		try {
			subTest.save();

			ParseObject returnedO = ParseQuery.getQuery("subTest").get(subTest.getObjectId());
			val = returnedO.getInt("value");
			Assert.assertEquals(4, val);

			test.put("subClass", returnedO);
			test.save();

			test.delete();
			subTest.delete();
		} catch (ParseException ¢) {
			// TODO Auto-generated catch block
			¢.printStackTrace();
			Assert.fail();
		}
	}

	// Add an array of pointers
	@Test
	public void test5() {
		// initialize();
		ParseObject test = new ParseObject("test");
		test.put("testValue0", 5);

		List<ParseObject> values = new ArrayList<ParseObject>();
		ParseObject subTest1 = new ParseObject("subTest");
		subTest1.put("value", 5.1);
		ParseObject subTest2 = new ParseObject("subTest");
		subTest2.put("value", 5.2);
		ParseObject subTest3 = new ParseObject("subTest");
		subTest3.put("value", 5.3);
		values.add(subTest1);
		values.add(subTest2);
		values.add(subTest3);
		try {
			subTest1.save();
			subTest2.save();
			subTest3.save();

			test.put("subClassArray", values);

			test.save();

			test.delete();
			subTest1.delete();
			subTest2.delete();
			subTest3.delete();
		} catch (ParseException ¢) {
			// TODO Auto-generated catch block
			¢.printStackTrace();
			Assert.fail();
		}
	}

}
