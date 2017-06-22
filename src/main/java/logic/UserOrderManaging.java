package logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;

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
					Integer.parseInt(date[1]),
					Integer.parseInt(date[2]),
					p.getInt("hour")/4,
					p.getInt("hour")%4);
			EndTiem.set(Integer.parseInt(date[0]),
					Integer.parseInt(date[1]),
					Integer.parseInt(date[2]),
					(p.getInt("hour")+p.getInt("hoursAmount"))/4,
					(p.getInt("hour")+p.getInt("hoursAmount"))%4);
			PresentOrder order = new PresentOrder(p.getString("slotId"),SartTime.getTime(), EndTiem.getTime(), p.getDouble("price"),p.getString("id"));
			ordersList.add(order);
		}
		
		return ordersList;	
	}
}
