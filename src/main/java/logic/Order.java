package logic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.management.DBManager;

public class Order {
	/**
	 * @author Inbal Matityahu
	 * @since 8/5/16 This class represent an order for renting a parking slot
	 */
	
	// The order id. Should be a unique value.
	private String id;
	
	// The driver's id 
	private String driverId;
		
	// The demand slot id
	private String slotId;
		
	// The demand day
	private Date date;
	
	// The desired start time
	private Date startHour;
	
	// The desired end time
	private Date endHour;
	
	private final String objectClass = "Order";
	
	/* Constructors */
	
	public Order(){
		
	}

	// Create a new order. Will result in a new order in the DB.
	public Order(int id, final String driverId, final String slotId, Date startTime, Date endTime) throws ParseException, InterruptedException {
		
		DBManager.initialize();
		//TODO: check input
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("driverId", driverId);
		fields.put("slotId", slotId);
		
		fields.put("date", startTime.getDate());
		
		int hours =hoursDifference(endTime, startTime);
		System.out.println("the different is "+hours);
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
		for (int i=0; i<hours; i++){
			id++;
			fields.put("hour",  cal.getTime());
		    cal.add(Calendar.HOUR_OF_DAY, 2); // adds one hour
			keyValues.put("id", id);
			DBManager.insertObject(objectClass, keyValues, fields);
			Thread.sleep(6000);
		}
	}
	
	public Order(final ParseObject obj) throws ParseException {
		DBManager.initialize();

		this.driverId = obj.getString("driverId");
		this.date = obj.getDate("date");
		this.slotId = obj.getString("slotId");
		this.startHour = obj.getDate("startHour");
		this.endHour = obj.getDate("endHour");
		
	}
	
	public Order(final String id) throws ParseException {
		DBManager.initialize();

		Map<String, Object> keys = new HashMap<>();
		keys.put("id", id);
		Map<String,Object> returnV = DBManager.getObjectFieldsByKey(objectClass, keys);
		
		this.driverId=returnV.get("driverId") + "";
		this.slotId= returnV.get("slotId") + "";
		this.date= (Date)returnV.get("date");
		this.startHour= (Date)returnV.get("startHour");
		this.endHour= (Date)returnV.get("endHour");
		this.id=id;
	}

	/* Getters */

	public String getId() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return DBManager.getObjectFieldsByKey(objectClass, key).get("id") + "";
	}
	
	public String getDriverId() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return DBManager.getObjectFieldsByKey(objectClass, key).get("driverId") + "";
	}
	
	public String getSlotId() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return DBManager.getObjectFieldsByKey(objectClass, key).get("slotId") + "";
	}
	
	public Date getDate() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return (Date)DBManager.getObjectFieldsByKey(objectClass, key).get("date");
	}
	
	public Date getStartTime() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return (Date)DBManager.getObjectFieldsByKey(objectClass, key).get("startTime");
	}
	
	public Date getEndTime() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return (Date)DBManager.getObjectFieldsByKey(objectClass, key).get("endTime");
	}
	
	/* Setters */

	public void setDriverId(final String newDriverId) throws ParseException {
		if (newDriverId == null)
			throw new IllegalArgumentException("driver id can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", newDriverId);
		newFields.put("slotId", this.slotId);
		newFields.put("endTime", this.endHour);
		newFields.put("startTime", this.startHour);
		newFields.put("date", this.date);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}

	public void setSlotId(final String newSlot) throws ParseException {
		if (newSlot == null)
			throw new IllegalArgumentException("slot id can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", newSlot);
		newFields.put("endTime", this.endHour);
		newFields.put("startTime", this.startHour);
		newFields.put("date", this.date);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}
	
	public void setStartTime(final Date newStart) throws ParseException {
		if (newStart == null)
			throw new IllegalArgumentException("start date can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("endTime", this.endHour);
		newFields.put("startTime", newStart);
		newFields.put("date", this.date);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}
	
	public void setEndTime(final Date newEnd) throws ParseException {
		if (newEnd == null)
			throw new IllegalArgumentException("end time can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("endTime", newEnd);
		newFields.put("startTime", this.startHour);
		newFields.put("date", this.date);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}
	
	public void setDate(final Date newDate) throws ParseException {
		if (newDate == null)
			throw new IllegalArgumentException("end time can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("endTime", this.endHour);
		newFields.put("startTime", this.startHour);
		newFields.put("date", newDate);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}
	
	/* Methods */
	
	private static int hoursDifference(Date date1, Date date2) {
	    final int MILLI_TO_HOUR = 1000 * 60 * 60;
	    return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
	}
	
	
}
