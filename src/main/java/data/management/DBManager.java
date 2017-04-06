package data.management;

import java.util.Map;

import org.parse4j.Parse;
import org.parse4j.ParseObject;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import data.members.dbMember;

public class DBManager {
	private static final String appId = "parkingmanagment";
	private static final String restKey = "2139d-231cb2-738aa";
	private static final String serverUrl = "https://pm-parse-server.herokuapp.com/parse";
	private static boolean init;

	public static void initialize() {
		if(init)
			return;
		Parse.initialize(appId, restKey, serverUrl);
		init=true;
	}

	
	public static void insertObject(final String objectClass, Map<String, Object> fields){
		
	}
	
	public static void insertObject(final String objectClass, Map<String, Object> fields,SaveCallback c){
		
	}
	
	public static void deleteObject(final String objectClass,Map<String, Object> fields){
		
	}
	
	public static void deleteObject(final String objectClass,Map<String, Object> fields,DeleteCallback c){
		
	}
	
	public static void update(final String objectClass,Map<String, Object> fields){
		
	}
	
	public static void getObjectByFields(final String objectClass, Map<String, Object> values,GetCallback<ParseObject> o){
		
	}
	
	public static Map<String, Object> getObjectFieldsByKey(final String objectClass, Map<String, Object> keys){
		return keys;
	}
	
	
	public static ParseObject getParseObject(final dbMember ¢) {
		return ¢.getParseObject();
	}
}
