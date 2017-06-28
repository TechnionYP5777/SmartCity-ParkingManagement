package logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;

/**
 * 
 * @author assaflu
 * @since 22.6.2017
 * 
 * The purpose of this class is to retrive data about user orders
 */

public class UserOrderManaging {
	
	public static List<PresentOrder> getUserOrders(String userID, DatabaseManager db){
		List<PresentOrder> ordersList = new ArrayList<>();
		Calendar SartTime = Calendar.getInstance();
		Calendar EndTiem = Calendar.getInstance();
		for(ParseObject p : db.getAllObjects("Order", 100)){
			if(!p.getString("driverId").equals(userID))
				continue;
			String date[] = p.getString("date").split("-");
			SartTime.set(Integer.parseInt(date[0]),
					Integer.parseInt(date[1])-1,
					Integer.parseInt(date[2]),
					p.getInt("hour")/4,
					15*p.getInt("hour")%4);
			EndTiem.set(Integer.parseInt(date[0]),
					Integer.parseInt(date[1])-1,
					Integer.parseInt(date[2]),
					(p.getInt("hour")+p.getInt("hoursAmount"))/4,
					15*(p.getInt("hour")+p.getInt("hoursAmount"))%4);
			PresentOrder order = new PresentOrder(p.getString("slotId"),SartTime.getTime(), EndTiem.getTime(), p.getDouble("price"),p.getString("id"));
			ordersList.add(order);
		}
		
		return ordersList;	
	}
	
	public static void cancleOrder(PresentOrder orederToCancle,DatabaseManager db) throws ParseException, InterruptedException{
		Order p = new Order(orederToCancle.getOrderId(),db);
		p.removeOrderFromDB();
	}
}
