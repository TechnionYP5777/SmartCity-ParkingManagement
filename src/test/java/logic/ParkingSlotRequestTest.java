package logic;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
import data.members.StickersColor;

public class ParkingSlotRequestTest {

	@Test
	public void testEmptyDB() {
		
    	ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
    	DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	BillingClass b = Mockito.mock(BillingClass.class);
    	Date date = new Date();
    	
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(new ArrayList<ParseObject>());
    	Mockito.when(d.getAllObjects("ParkingSlot", 600)).thenReturn(new ArrayList<ParseObject>());
    	
    	ParkingSlotRequest request = new ParkingSlotRequest(point,date , 2, d);
    	
    	assertTrue(request.getAllAvailableParkingSlot(b).isEmpty());
	}
	
	@Test
	public void testEmptyOrder() {
		
    	ParseGeoPoint point = new ParseGeoPoint(0,0);
    	DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	BillingClass b = new BasicBillingClass();
    	Date date = new Date();
    	
    	List<ParseObject> parkingSlots = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("ParkingSlot");
    		p.put("name", i+"");
    		p.put("location",new ParseGeoPoint(i,i));
    		p.put("rank", 1);
    		parkingSlots.add(p);
    	}
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(new ArrayList<ParseObject>());
    	Mockito.when(d.getAllObjects("ParkingSlot", 600)).thenReturn(parkingSlots);
    	
    	ParkingSlotRequest request = new ParkingSlotRequest(point,date , 2, d);
    	List<PresentParkingSlot> checkList = request.getAllAvailableParkingSlot(b);
    	
    	assertTrue(checkList.size() == 5);
    	List<String> names = new ArrayList<>();
    	for(PresentParkingSlot p : checkList){
    		names.add(p.getName());
    	}
    	for(int i = 0; i<5; i++){
    		assertTrue(names.contains(i+""));
    	}
    	
	}
	
	@Test
	public void testEmptyParkingSlot() {
		
    	ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
    	DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	BillingClass b = Mockito.mock(BillingClass.class);
    	Date date = new Date();
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("hour", i);
    		p.put("date",(new Date()).toString());
    		p.put("slotId", i+"");
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
    	Mockito.when(d.getAllObjects("ParkingSlot", 600)).thenReturn(new ArrayList<ParseObject>());
    	
    	ParkingSlotRequest request = new ParkingSlotRequest(point,date , 2, d);
    	
    	assertTrue(request.getAllAvailableParkingSlot(b).isEmpty());
	}
	
	@Test
	public void testNoCollision() {
		
    	ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
    	DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	BillingClass b = Mockito.mock(BillingClass.class);
    	Date date = new Date(2018,11,11);
    	
    	List<ParseObject> parkingSlots = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("ParkingSlot");
    		p.put("name", i+"");
    		p.put("location",new ParseGeoPoint(i,i));
    		p.put("rank", 1);
    		parkingSlots.add(p);
    	}
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("hour", i);
    		p.put("date",(new Date()).toString());
    		p.put("slotId", i+"");
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
    	Mockito.when(d.getAllObjects("ParkingSlot", 600)).thenReturn(parkingSlots);
    	
    	ParkingSlotRequest request = new ParkingSlotRequest(point,date , 2, d);
    	List<PresentParkingSlot> checkList = request.getAllAvailableParkingSlot(b);
    	
    	assertTrue(checkList.size() == 5);
    	List<String> names = new ArrayList<>();
    	for(PresentParkingSlot p : checkList){
    		names.add(p.getName());
    	}
    	for(int i = 0; i<5; i++){
    		assertTrue(names.contains(i+""));
    	}
	}
	
	@Test
	public void testOneCollision() {
		
		ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
    	DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	BillingClass b = Mockito.mock(BillingClass.class);
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    	
    	List<ParseObject> parkingSlots = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("ParkingSlot");
    		p.put("name", i+"");
    		p.put("location",new ParseGeoPoint(i,i));
    		p.put("rank", 1);
    		parkingSlots.add(p);
    	}
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("hour", (cal.get(Calendar.HOUR_OF_DAY)+1+i)%24);
    		p.put("date",formatDate.format((new Date())));
    		p.put("hoursAmount",1);
    		p.put("slotId", i+"");
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
    	Mockito.when(d.getAllObjects("ParkingSlot", 600)).thenReturn(parkingSlots);
    	
    	ParkingSlotRequest request = new ParkingSlotRequest(point,date , 2, d);
    	List<PresentParkingSlot> checkList = request.getAllAvailableParkingSlot(b);
    	System.out.println(checkList.size());
    	assertTrue(checkList.size() == 4);
    	List<String> names = new ArrayList<>();
    	for(PresentParkingSlot p : checkList){
    		names.add(p.getName());
    	}
    	for(int i = 1; i<5; i++){
    		assertTrue(names.contains(i+""));
    	}
	}

	@Test
	public void testMoreThanOneCollision() {
		
		ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
    	DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	BillingClass b = Mockito.mock(BillingClass.class);
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    	
    	List<ParseObject> parkingSlots = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("ParkingSlot");
    		p.put("name", i+"");
    		p.put("location",new ParseGeoPoint(i,i));
    		p.put("rank", 1);
    		parkingSlots.add(p);
    	}
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i = 0; i<5; i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("hour", (cal.get(Calendar.HOUR_OF_DAY))%24);
    		p.put("date",formatDate.format((new Date())));
    		p.put("hoursAmount",1);
    		p.put("slotId", i+"");
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 600)).thenReturn(orders);
    	Mockito.when(d.getAllObjects("ParkingSlot", 600)).thenReturn(parkingSlots);
    	
    	ParkingSlotRequest request = new ParkingSlotRequest(point,date , 2, d);
    	List<PresentParkingSlot> checkList = request.getAllAvailableParkingSlot(b);
    	System.out.println(checkList.size());
    	assertTrue(checkList.isEmpty());
	}
}
