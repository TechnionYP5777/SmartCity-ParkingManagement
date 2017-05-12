package database;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

import Exceptions.LoginException;
import data.management.DBManager;

public class DBManagerTests {


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
	}
	
	@Test
	public void testDelete(){
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
		DBManager.deleteObject("assaf",Kval);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		DBManager.getObjectByFields("assaf", Kval,new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				if(arg0==null) mutex.set(1);
			}
		});
		try {
			Thread.sleep(6000);
			assert mutex.compareAndSet(0,1);
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
		DBManager.insertObject("assaf", Kval1, val);
		DBManager.insertObject("assaf", Kval2, val);
		DBManager.insertObject("assaf", Kval3, val);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<ParseObject> mylist = DBManager.getAllObjects("assaf",2);
		
		for(ParseObject p : mylist){
			System.out.println(p.get("first"));
			System.out.println(p.get("second"));
		}
		assert (mylist.get(0).get("first").equals(3));
		assert (mylist.get(1).get("first").equals(2));
		assert (mylist.get(2).get("first").equals(1));
		
		}	
	
	@Test
	public void getObjectByFieldsTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		AtomicInteger testResult = new AtomicInteger(0);
		DBManager.insertObject("assaf", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DBManager.getObjectByFields("assaf", Kval, new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject arg0, ParseException arg1) {
				System.out.println(arg0.get("second"));
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
	}
	
	@Test
	public void getObjectFieldsByKeyTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		DBManager.insertObject("assaf", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert DBManager.getObjectFieldsByKey("assaf",Kval).get("second").equals(2);
	}

	@Test
	public void registerTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("id", 2);
		val.put("password","asdf");
		val.put("email","asdfasd");
		val.put("car",123123);
		try {
			DBManager.register("assafReg2", Kval, val);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			assert e.exception.equals("user already exists");
		}
	}

	@Test
	public void loginTest(){
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
			assert e.exception.equals("user already exists");
		}
		try {
			DBManager.login("assafReg2","id","2","password", "asdf");
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
	}

	@Test
	public void updateTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Map<String,Object> val = new HashMap<String,Object>();
		Kval.put("first", 1);
		val.put("second",2);
		DBManager.insertObject("assaf", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		val.put("second",3);
		DBManager.update("assaf", Kval, val);
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assert DBManager.getObjectFieldsByKey("assaf",Kval).get("second").equals(3);
	}
	
	@Test
	public void getObjectFieldsByKeyEmptyTest(){
		Map<String,Object> Kval = new HashMap<String,Object>();
		Kval.put("first", 1);
		assert DBManager.getObjectFieldsByKey("assafEmpty",Kval).isEmpty();
	}
}
