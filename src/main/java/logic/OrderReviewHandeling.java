package logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.callback.GetCallback;

import Exceptions.NotExists;
import data.management.DatabaseManager;
import data.members.ParkingSlot;

/**
 * 
 * @author assaflu
 * @since 22.6.2017
 * 
 * The purpose of this class is to handle the review and rating of order and slots
 */

public class OrderReviewHandeling {

	public static List<PresentOrder> getUserLastOrder (String userID,Date currentDate,DatabaseManager db){
		List<PresentOrder> ordersList = new ArrayList<>();
		Calendar current = Calendar.getInstance();
		current.setTime(currentDate);
		Calendar StartTime = Calendar.getInstance();
		Calendar EndTime = Calendar.getInstance();
		for(ParseObject p : db.getAllObjects("Order", 100)){
			if(!p.getString("driverId").equals(userID) || p.getBoolean("reviewed"))
				continue;
			String date[] = p.getString("date").split("-");
			StartTime.set(Integer.parseInt(date[0]),
					Integer.parseInt(date[1])-1,
					Integer.parseInt(date[2]),
					p.getInt("hour")/4,
					p.getInt("hour")%4);
			EndTime.set(Integer.parseInt(date[0]),
					Integer.parseInt(date[1])-1,
					Integer.parseInt(date[2]),
					(p.getInt("hour")+p.getInt("hoursAmount"))/4,
					(p.getInt("hour")+p.getInt("hoursAmount"))%4);
			
			if(StartTime.after(current) || EndTime.after(current))
				continue;
			PresentOrder order = new PresentOrder(p.getString("slotId"),StartTime.getTime(), EndTime.getTime(), p.getDouble("price"),p.getString("id"));
			ordersList.add(order);
		}
		
		Collections.sort(ordersList);
		return ordersList;
	}
	
	public static void giveReviewToParkingSlot(PresentOrder order, int review,DatabaseManager db) throws ParseException{
		Map<String,Object> values = new HashMap<>();
		values.put("name",order.getParkingSlotId());
		ParkingSlot p = new ParkingSlot(order.getParkingSlotId(), db);
		p.setRating((p.getRating()*p.getNumOfVoters()+review)/(p.getNumOfVoters()+1));
		p.setNumOfVoters(p.getNumOfVoters()+1);
		Order o = new Order(order.getOrderId(),db);
		o.setReview();
	}
	
	
}
