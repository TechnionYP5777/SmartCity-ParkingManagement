package data.management;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

public class DBManager {
	private static final String appId = "parkingmanagment";
	private static final String restKey = "2139d-231cb2-738aa";
	private static final String serverUrl = "https://pm-parse-server.herokuapp.com/parse";
	
	public static void initialize() {
		Parse.initialize(appId, restKey, serverUrl);
	}
	
}
