package database;

import org.parse4j.Parse;
import org.parse4j.ParseException;
//import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

public class populateDB {
	public static void main(String[] args) {
		System.out.println("hello world");
		initialize();
		test1();
		System.out.println("goodbye world");
	}
	
	private static final String appId = "parkingmanagment";
	private static final String restKey = "2139d-231cb2-738aa";
	private static final String serverUrl = "https://pm-parse-server.herokuapp.com/parse";
	
	
	public static void initialize() {
		Parse.initialize(appId, restKey, serverUrl);
	}
	
	// The result of this test should be a new class (Named test) in the DB that contains one raw.
	// Please verify the succuess of the test in the DB, and delete the class afterwards.
	// Automation - to be added
	public static void test1(){	
		Parse.initialize("parkingmanagment", "2139d-231cb2-738aa", "https://pm-parse-server.herokuapp.com/parse");
		
		ParseObject test = new ParseObject("test");
		test.put("testValue0", 0);
		test.put("testValue1", 1);
		test.put("testValue2", 2);
		try {
			test.save();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
