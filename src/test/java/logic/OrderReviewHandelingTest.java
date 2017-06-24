package logic;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;

public class OrderReviewHandelingTest {
	
	@Test
	public void UserHasNoOrders(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	
    	List<ParseObject> orders = new ArrayList<>();
    	Mockito.when(d.getAllObjects("Order", 100)).thenReturn(orders);
    	Calendar cal = Calendar.getInstance();
    	cal.set(2017, 12, 21, 17, 15);
    	assert OrderReviewHandeling.getUserLastOrder("123123123",cal.getTime(), d).isEmpty();
	}
	
	@Test
	public void AllUserOrdersAreReviewed(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i=0; i<10;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", "123123123");
    		p.put("reviewed", true);
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 100)).thenReturn(orders);
    	Calendar cal = Calendar.getInstance();
    	cal.set(2017, 12, 21, 17, 15);
    	assert OrderReviewHandeling.getUserLastOrder("123123123",cal.getTime(), d).isEmpty();
	}
	
	@Test
	public void MixedOrderThatPassAndNotPassTheDate(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i=0; i<5;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", "123123123");
    		p.put("hours", 64);
    		p.put("hoursAmount", 8);
    		p.put("date", "2018-5-2");
    		orders.add(p);
    	}
    	
    	for(int i=0; i<5;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", "123123123");
    		p.put("hours", 64);
    		p.put("hoursAmount", 8);
    		p.put("date", "2017-5-2");
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 100)).thenReturn(orders);
    	Calendar cal = Calendar.getInstance();
    	cal.set(2017, 12, 21, 17, 15);
    	assert OrderReviewHandeling.getUserLastOrder("123123123",cal.getTime(), d).size() == 5;
	}
	
	@Test
	public void MixOfMultipaleUsers(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i=0; i<5;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", "123");
    		p.put("hours", 64);
    		p.put("hoursAmount", 8);
    		p.put("date", "2017-5-2");
    		orders.add(p);
    	}
    	
    	for(int i=0; i<5;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", "123123123");
    		p.put("hours", 64);
    		p.put("hoursAmount", 8);
    		p.put("date", "2017-5-2");
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 100)).thenReturn(orders);
    	Calendar cal = Calendar.getInstance();
    	cal.set(2017, 12, 21, 17, 15);
    	assert OrderReviewHandeling.getUserLastOrder("123123123",cal.getTime(), d).size() == 5;
	}
	
	@Test
	public void CorrectListOrderTest(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);

    	List<ParseObject> orders = new ArrayList<>();
    	for(int i=1; i<6;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", "123123123");
    		p.put("hours", 64);
    		p.put("hoursAmount", 8);
    		p.put("date", "2017-5-"+i);
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 100)).thenReturn(orders);
    	Calendar cal = Calendar.getInstance();
    	cal.set(2017, 12, 21, 17, 15);
    	//SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    	List<PresentOrder> o = OrderReviewHandeling.getUserLastOrder("123123123",cal.getTime(), d);
    	assert o.size() == 5;
    	for(int i=1; i<6;i++){
    		cal.setTime(o.get(i-1).getStartTime());
    		assertEquals(cal.get(Calendar.DAY_OF_MONTH),6-i);
    	}
	}
}
