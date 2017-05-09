package data.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.parse4j.Parse;
import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.FindCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import Exceptions.LoginException;
import data.members.dbMember;

public class DBManager {
	private static final String appId = "parkingmanagment";
	private static final String restKey = "2139d-231cb2-738aa";
	private static final String serverUrl = "https://pm-parse-server.herokuapp.com/parse";
	private static boolean init;
	private static AtomicInteger registrationMutex = new AtomicInteger(0);
	
	public static void initialize() {
		if(init)
			return;
		Parse.initialize(appId, restKey, serverUrl);
		init=true;
	}

	private static FindCallback<ParseObject> privateGetAllObjects(String objectClass,int limit,int skip,AtomicInteger mutex,List<ParseObject> allObj){
		return new FindCallback<ParseObject>(){

			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				if(arg1 == null && arg0 != null){
					if(limit == arg0.size()){
		                ParseQuery<ParseObject> query = ParseQuery.getQuery (objectClass);
		                query.skip(skip+limit);
		                query.limit(limit);
		                query.findInBackground(privateGetAllObjects(objectClass,query.getLimit(),query.getSkip(),mutex,allObj));
					}
					else{
						synchronized (mutex) {
							mutex.compareAndSet(0,1);
							allObj.addAll(arg0);
							mutex.notifyAll();
						}
					}
					if(!mutex.compareAndSet(1,1)){
						allObj.addAll(arg0);
					}
				}
				
				if(arg0 == null){
					synchronized (mutex) {
						mutex.compareAndSet(0,1);
						mutex.notifyAll();
					}
				}
			}
		};
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
		checkExsistance(objectClass,fields, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				privateDeleteObject(objectClass, arg0.getObjectId() + "", new DeleteCallback() {

					@Override
					public void done(ParseException arg0) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}
	
	public static void deleteObject(final String objectClass,Map<String, Object> fields,DeleteCallback c){
		checkExsistance(objectClass,fields, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0!=null)
					privateDeleteObject(objectClass, arg0.getObjectId() + "", c);
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
	
	public static List<ParseObject> getAllObjects (final String objectClass,int startLimit){
		ParseQuery<ParseObject> pq = ParseQuery.getQuery(objectClass);
		pq.limit(startLimit);
		AtomicInteger mutex = new AtomicInteger(0);
		List<ParseObject> allObjects = new ArrayList<ParseObject>();
		pq.findInBackground(privateGetAllObjects(objectClass,pq.getLimit(),0,mutex,allObjects));
		synchronized(mutex){
			try {
				if(mutex.compareAndSet(0,0))
					mutex.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return allObjects;
	}
	
	public static void Login (String userClass,String userNameKey, String userName, String passwordKey, String password) throws LoginException{
		AtomicInteger loged = new AtomicInteger(0);
		Map<String,Object> kval = new HashMap<>();
		kval.put(userNameKey, userName);
		checkExsistance(userClass, kval, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				synchronized(loged){
					if(arg0 != null){
						if(arg0.get(passwordKey).equals(password))
							loged.compareAndSet(0, 1);
						else
							loged.compareAndSet(0, 2);
					}
					else
						loged.compareAndSet(0, 3);
					loged.notifyAll();
				}
			}
		});
		synchronized(loged){
			try {
				if(loged.compareAndSet(0,0))
					loged.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		switch (loged.get()) {
		case 2:
			throw new LoginException("password doesn't match");	
		case 3:
			throw new LoginException("user doesn't exists");
		default:
			break;
		}
	}

	public static void Register(Map<String, Object> userKeys, Map<String, Object> userFields) throws LoginException{
		synchronized (registrationMutex) {
			if(!registrationMutex.compareAndSet(0,0)){
				try {
					registrationMutex.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
				registrationMutex.set(1);
		}
		AtomicInteger mutex = new AtomicInteger(0);
		checkExsistance("Driver", userKeys, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				synchronized(mutex){
					if(arg0 != null)
						mutex.set(1);
					else
						mutex.set(2);
					mutex.notifyAll();
				}
			}
		});
		synchronized(mutex){
			try {
				if(mutex.get() == 0)
					mutex.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(mutex.get() == 1){
			synchronized (registrationMutex) {
				registrationMutex.set(0);
				registrationMutex.notify();
			}
			throw new LoginException("user already exists");
		}
		else{
			mutex.set(0);
			privateInsertObject("Driver", userKeys, userFields, new SaveCallback() {
				
				@Override
				public void done(ParseException arg0) {
					synchronized(mutex){
						mutex.set(1);
					}
					
				}
			});
			
			synchronized(mutex){
				try {
					if(mutex.get()==0)
						mutex.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			synchronized (registrationMutex) {
				registrationMutex.set(0);
				registrationMutex.notify();
			}

		}
		
		
		
	}
	
	public static ParseObject getParseObject(final dbMember ¢) {
		return ¢.getParseObject();
	}
}
