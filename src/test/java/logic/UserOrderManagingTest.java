package logic;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;

public class UserOrderManagingTest {

	@Test
	public void driverHasNoOrders(){
    	DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	
    	List<ParseObject> orders = new ArrayList<>();
    	Mockito.when(d.getAllObjects("Order", 100)).thenReturn(orders);
    	assert UserOrderManaging.getUserOrders("123123123", d).isEmpty();
	}
	
	@Test
	public void exactOrdersReturnToDriver(){
		DatabaseManager d = Mockito.mock(DatabaseManager.class);
    	
    	List<ParseObject> orders = new ArrayList<>();
    	for(int i=0; i<10;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", "123123123");
    		p.put("date", "2017-3-3");
    		p.put("hour", 4);
    		p.put("hoursAmount", 3);
    		p.put("slotId",i+"");
    		p.put("price", 1);
    		p.put("id", i+"");
    		orders.add(p);
    	}
    	
    	for(int i=0; i<10;i++){
    		ParseObject p = new ParseObject("Order");
    		p.put("driverId", i+"");
    		p.put("date", "2017-3-3");
    		p.put("hour", 4);
    		p.put("hoursAmount", 3);
    		p.put("slotId",i);
    		p.put("price", 2);
    		p.put("id", i);
    		orders.add(p);
    	}
    	
    	Mockito.when(d.getAllObjects("Order", 100)).thenReturn(orders);
    	List<PresentOrder> p = UserOrderManaging.getUserOrders("123123123", d);
    	assert p.size() ==10;
    	for(PresentOrder t : p){
    		assert t.getPrice() == 1;
    	}
	}
}
