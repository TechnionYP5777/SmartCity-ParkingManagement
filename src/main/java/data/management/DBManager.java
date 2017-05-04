package data.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.FindCallback;
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

	private static void checkExsistance(final String objectClass, Map<String, Object> keyValues,GetCallback<ParseObject> o){
		ParseQuery<ParseObject> pq = ParseQuery.getQuery(objectClass);
		for (String key : keyValues.keySet())
			pq.whereEqualTo(key, keyValues.get(key));
		pq.limit(1);
		pq.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if (arg1 != null || arg0==null)
					o.done(null, arg1);
				else
					o.done(arg0.get(0), null);
			}
		});
	}
	
	private static void privateInsertObject(final String objectClass, Map<String, Object> keyValues,Map<String, Object> fields,SaveCallback c){
		checkExsistance(objectClass, keyValues, new GetCallback<ParseObject>() {
			@Override
			public void done(ParseObject arg0, ParseException arg1){
				if(arg0 != null) return;
				final ParseObject obj = new ParseObject(objectClass);
				for(String key: keyValues.keySet())
					obj.put(key, keyValues.get(key));
				for(String key: fields.keySet())
					obj.put(key, fields.get(key));
				obj.saveInBackground(c);
			}
		});
	}
	
	private static void privateDeleteObject (final String objectClass,String id,DeleteCallback c){
		final ParseObject obj = new ParseObject(objectClass);
		obj.setObjectId(id);
		obj.deleteInBackground(c);
	}

	private static void privateFind (final String objectClass, Map<String,Object> fields,FindCallback<ParseObject> o){
		
	}
	
	public static void insertObject(final String objectClass, Map<String, Object> keyValues,Map<String, Object> fields){
		privateInsertObject(objectClass, keyValues, fields, new SaveCallback() {
			
			@Override
			public void done(ParseException arg0) {
				// do nothing
				
			}
		});
	}
	
	public static void insertObject(final String objectClass,Map<String, Object> keyValues, Map<String, Object> fields,SaveCallback c){
		privateInsertObject(objectClass,keyValues,fields,c);
	}
	
	public static void deleteObject(final String objectClass,Map<String, Object> fields){
		privateFind(objectClass,fields, new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg0!=null)
					privateDeleteObject(objectClass, arg0.get(0).getObjectId() + "", new DeleteCallback() {
						@Override
						public void done(ParseException arg0) {
						}
					});
			}
			
		});
	}
	
	public static void deleteObject(final String objectClass,Map<String, Object> fields,DeleteCallback c){
		privateFind(objectClass,fields, new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg0!=null)
					privateDeleteObject(objectClass, arg0.get(0).getObjectId() + "", c);
			}
			
		});
	}
	
	public static void update(final String objectClass,Map<String, Object> keys,Map<String, Object> newFields){
		checkExsistance(objectClass,keys,new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0 == null) return;
				final ParseObject obj = new ParseObject(objectClass);
				for(String key: newFields.keySet())
					obj.put(key, newFields.get(key));
				obj.setObjectId(arg0.getObjectId());
				obj.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException arg0) {
						//do nothing
						
					}
				});
			}
		});
	}
	
	public static void getObjectByFields(final String objectClass, Map<String, Object> values,GetCallback<ParseObject> o){
		checkExsistance(objectClass, values, o);
	}
	
	public static Map<String, Object> getObjectFieldsByKey(final String objectClass, Map<String, Object> keys){
		Map<String, Object> objectFields = new HashMap<String,Object>();
		StringBuilder mutex = new StringBuilder();
		checkExsistance(objectClass, keys, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0 == null) return;
				for(String key : arg0.keySet())
					objectFields.put(key, arg0.get(key));
				synchronized (mutex) {
					mutex.append("done");
					mutex.notifyAll();
				}
			}
		});
		
		synchronized (mutex) {
			if(!"done".equals(mutex)){
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					
				}
			}
		}
		
		return objectFields;
	}
	
	
	public static ParseObject getParseObject(final dbMember ¢) {
		return ¢.getParseObject();
	}
}
