package gui.driver.app;
import logic.*;
import javafx.concurrent.*;
import java.util.*;
import org.parse4j.ParseGeoPoint;
import data.management.*;

import data.members.StickersColor;

public class getSlotsTask extends Service<List<PresentParkingSlot>> {
	@Override
	protected Task<List<PresentParkingSlot>> createTask() {
		
		    return new Task<List<PresentParkingSlot>>() {
		        @Override
		        protected List<PresentParkingSlot> call() throws Exception {
		        	System.out.println("3");
		        	ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
		        	DatabaseManager d = new DatabaseManagerImpl();
		        	System.out.println("4");
		        	ParkingSlotRequest request = new ParkingSlotRequest(point, new Date(), 2, d);
		        	System.out.println("before request");
		        	List<PresentParkingSlot> l = request.getAllAvailableParkingSlot(new Billing() {
						
						@Override
						public double calculateCost(StickersColor rank, double distance) {
							// TODO Auto-generated method stub
							return 0;
						}
					});
		        	System.out.println("COOL");
		        	for(PresentParkingSlot p: l){
		        		System.out.println(p.getName());
		        	}
		        	return null;
		        }
		    };
		}
}