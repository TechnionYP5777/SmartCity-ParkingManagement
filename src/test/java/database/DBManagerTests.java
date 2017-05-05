package database;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.GetCallback;
import org.parse4j.callback.SaveCallback;

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
	
	

}
