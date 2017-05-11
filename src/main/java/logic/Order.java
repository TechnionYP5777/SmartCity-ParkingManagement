package logic;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.management.DBManager;
import data.members.Driver;
import data.members.ParkingSlot;

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
	
	// The desired end time
	private String hour;
	
	private final String objectClass = "Order";
	
	/* Constructors */
	
	public Order(){
		
	}

	// Create a new order. Will result in a new order in the DB.
	public Order(final String driverId, final String slotId, Date startTime, Date endTime) throws ParseException, InterruptedException {
		
		DBManager.initialize();
		
		String idToString;
//		if (!checkParameters(idToString, driverId, slotId, startTime, endTime))
//			throw new IllegalArgumentException("arguments are illegeal!");
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("driverId", driverId);
		fields.put("slotId", slotId);
		fields.put("date", startTime);
		
		int hours =hoursDifference(endTime, startTime);
		int id=0;
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
		for (int i=0; i<=hours; i++){
			id++;
			idToString=(new Date().toString())+id;
			fields.put("hour", cal.getTime().toString());
		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
			keyValues.put("id", idToString);
			DBManager.insertObject(objectClass, keyValues, fields);
			Thread.sleep(6000);
		}
	}
	
	public Order(final ParseObject obj) throws ParseException {
		DBManager.initialize();

		this.driverId = obj.getString("driverId");
		this.date = obj.getDate("date");
		this.slotId = obj.getString("slotId");
		this.hour = obj.getString("hour");
		
	}
	
	public Order(final String id) throws ParseException {
		DBManager.initialize();

		Map<String, Object> keys = new HashMap<>();
		keys.put("id", id);
		Map<String,Object> returnV = DBManager.getObjectFieldsByKey(objectClass, keys);
		
		this.driverId=returnV.get("driverId") + "";
		this.slotId= returnV.get("slotId") + "";
		this.date= (Date)returnV.get("date");
		this.hour= returnV.get("hour")+"";
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
	
	public String getHour() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return (String) DBManager.getObjectFieldsByKey(objectClass, key).get("hour");
	}
	
	/* Setters */

	public void setDriverId(final String newDriverId) throws ParseException {
		if (newDriverId == null)
			throw new IllegalArgumentException("driver id can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", newDriverId);
		newFields.put("slotId", this.slotId);
		newFields.put("hour", this.hour);
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
		newFields.put("hour", this.hour);
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
		newFields.put("hour", newStart);
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
		newFields.put("hour", this.hour);
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
	
	private static boolean checkParameters(final String id, final String driverId, final String slotId, Date startTime, Date endTime) throws ParseException{
		if (!checkIfNotNull(id, driverId, slotId, startTime, endTime))
			return false;
		else{
			//check if user exist
			if (!checkIfDriverExist(driverId))
				return false;
			//check if id for order is not exist
			if (checkIfOrderIdExist(id))
				return false;
			//check if slot id exist
			if (!checkIfSlotExist(slotId))
				return false;
			//check if in this range on this slot is free
			if(!checkIfRangeFree(startTime, endTime, slotId))
				return false;
			return true;
		}
	}
		
	private static boolean checkIfNotNull(final String id, final String driverId, final String slotId, Date startTime, Date endTime){
		if (id == null || driverId==null || slotId==null || startTime==null || endTime==null){
			return false;
		}
		return true;
	}
	
	private static boolean checkIfDriverExist(final String driverId) throws ParseException{
		Driver d = new Driver(driverId);
		if (d.getId()==null){
			return false;
		}
		return true;
	}
	
	private static boolean checkIfOrderIdExist(final String id) throws ParseException{
		Order o = new Order(id);
		if (o.getId()!=null){
			return true;
		}
		return false;
	}
	
	private static boolean checkIfSlotExist(final String slotId) throws ParseException{
		ParkingSlot slot = new ParkingSlot(slotId);
		if (slot.getName()==null){
			return false;
		}
		return true;
	}
	
	private static boolean checkIfRangeFree(Date startTime, Date endTime, final String slotId){
		int hours = hoursDifference(endTime, startTime);
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		for (int i=0; i<hours; i++){
			key.put("slotId", slotId);
			key.put("date", startTime.getDate());
			key.put("hour", cal.getTime());
			if (DBManager.getObjectFieldsByKey("Order", key).get("id")!=null)
				return false;
			cal.add(Calendar.HOUR_OF_DAY, 1);
		}
		return true;
	}
	
}
