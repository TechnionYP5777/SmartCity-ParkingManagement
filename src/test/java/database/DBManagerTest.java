package database;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import Exceptions.LoginException;
import data.management.DBManager;
import util.Log;

public class DBManagerTest {

	private class ObjectToDelete{
		private String className;
		private Map<String,Object> key;
		public ObjectToDelete(String className, Map<String,Object> key){
			this.className = className;
			this.key = key;
		}
		
		public String getClassName(){
			return this.className;
		}
		
		public Map<String,Object> getKey(){
			return this.key;
		}
	}
	private static Collection<ObjectToDelete> objectsToDelete;
	@BeforeClass
	public static void classSetUp(){
		objectsToDelete = new ArrayList<>();
		try {		
			Log.setup();
		} catch (IOException e) {
			System.out.println("Could not set up the logger");
		}
	}
	
	@Before
	public void initialize(){
		DBManager.initialize();
	}
	
	@Test
	public void testInsert() {
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		AtomicInteger mutex = new AtomicInteger(0);
		DBManager.insertObject("assaf", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DBManager.insertObject("assaf", Kval, val,new SaveCallback() {
			@Override
			public void done(ParseException arg0) {
				mutex.set(1);
			}
		});
		try {
			Thread.sleep(6000);
			assert mutex.compareAndSet(0,1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		objectsToDelete.add(new ObjectToDelete("assaf", Kval));
	}
	
	@Test
	public void testDelete(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		AtomicInteger mutex = new AtomicInteger(0);
		DBManager.insertObject("assafDel", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DBManager.deleteObject("assafDel",Kval);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DBManager.getObjectByFields("assafDel", Kval,new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0==null) mutex.set(1);
			}
		});
		try {
			Thread.sleep(6000);
			assert mutex.compareAndSet(1,0);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	@Test
	public void getAllTest(){
		Map<String,Object> Kval1 = new HashMap<String,Object>();
		Map<String,Object> Kval2 = new HashMap<String,Object>();
		Map<String,Object> Kval3 = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval1.put("first", 1);
		Kval2.put("first", 2);
		Kval3.put("first", 3);
		val.put("second",2);
		DBManager.insertObject("assafGetAll", Kval1, val);
		DBManager.insertObject("assafGetAll", Kval2, val);
		DBManager.insertObject("assafGetAll", Kval3, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<ParseObject> mylist = DBManager.getAllObjects("assafGetAll",2);
		List<Integer> firsts = new ArrayList<>();
		for(ParseObject p : mylist){
			firsts.add(p.getInt("first"));
		}
		assert firsts.contains(1);
		assert firsts.contains(2);
		assert firsts.contains(3);
		
		objectsToDelete.add(new ObjectToDelete("assafGetAll", Kval1));
		objectsToDelete.add(new ObjectToDelete("assafGetAll", Kval2));
		objectsToDelete.add(new ObjectToDelete("assafGetAll", Kval3));
		
	}	
	
	@Test
	public void getObjectByFieldsTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		AtomicInteger testResult = new AtomicInteger(0);
		DBManager.insertObject("assafFieldTest", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBManager.getObjectByFields("assafFieldTest", Kval, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				//System.out.println(arg0.get("second"));
				if(arg0 != null) testResult.set((int) arg0.get("second"));
			}
		});
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert testResult.get() ==2;
		
		objectsToDelete.add(new ObjectToDelete("assafFieldTest", Kval));
	}
	
	@Test
	public void getObjectFieldsByKeyTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		DBManager.insertObject("assafKeyTest", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert DBManager.getObjectFieldsByKey("assafKeyTest",Kval).get("second").equals(2);
		
		objectsToDelete.add(new ObjectToDelete("assafKeyTest", Kval));
	}

	@Test
	public void registerTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("id", "2");
		val.put("password","asdf");
		val.put("email","asdfasd");
		val.put("car",123123);
		try {
			DBManager.register("assafReg2", Kval, val);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			assert false;
		}
		
		objectsToDelete.add(new ObjectToDelete("assafReg2", Kval));
	}

	@Test
	public void loginTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("id", "1");
		val.put("password","asdf");
		val.put("email","asdfasd");
		val.put("car",123123);
		try {
			DBManager.register("assafReg2", Kval, val);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			assert e.exception.equals("user already exists");
		}
		try {
			DBManager.login("assafReg2","id","1","password", "asdf");
		} catch (LoginException e) {
			assert false;
		}
		try {
			DBManager.login("assafReg2","id","3","password", "asdf");
		} catch (LoginException e) {
			e.exception.equals("user doesn't exists");
		}
		try {
			DBManager.login("assafReg2","id","2","password", "assdf");
		} catch (LoginException e) {
			e.exception.equals("password doesn't match");
		}
		
		objectsToDelete.add(new ObjectToDelete("assafReg2", Kval));
	}

	@Test
	public void updateTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		DBManager.insertObject("assafUpdate", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		val.put("second",3);
		DBManager.update("assafUpdate", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert DBManager.getObjectFieldsByKey("assafUpdate",Kval).get("second").equals(3);
		
		objectsToDelete.add(new ObjectToDelete("assafUpdate", Kval));
	}
	
	@Test
	public void getObjectFieldsByKeyEmptyTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Kval.put("first", 1);
		assert DBManager.getObjectFieldsByKey("assafEmpty",Kval).isEmpty();
	}
	
	
	@AfterClass
	public static void end() throws InterruptedException{
		AtomicInteger mutex = new AtomicInteger(objectsToDelete.size());
		for(ObjectToDelete o : objectsToDelete){
			DBManager.deleteObject(o.getClassName(),o.getKey(),new DeleteCallback() {

				@Override
				public void done(ParseException arg0) {
					synchronized (mutex) {
						mutex.decrementAndGet();
					}
					
				}});
		}
		while (!mutex.compareAndSet(0, 0));
	}
}
