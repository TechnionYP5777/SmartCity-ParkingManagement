package logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.management.DBManager;
import data.management.DatabaseManager;

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
	private String date;
	
	// The desired time
	private int hour;
	
	// The desired amount of hours
	private int hoursAmount;
	
	private final String objectClass = "Order";
	
	private DatabaseManager dbm;
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/* Constructors */
	
	public Order(){
		
	}

	// Create a new order. Will result in a new order in the DB.
	@SuppressWarnings("deprecation")
	public Order(final String driverId, final String slotId, Date startTime, Date endTime,DatabaseManager manager) throws ParseException, InterruptedException {
		LOGGER.info("Create a new order by slot id, start time, end time");
		this.dbm = manager;
		dbm.initialize();
		
		checkParameters(driverId, slotId, startTime, endTime);
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("driverId", driverId);
		fields.put("slotId", slotId);
		int hours =minDifference(endTime, startTime);
		if (hours<=0)
			return;
		fields.put("hoursAmount", hours);
		int id=0;
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime());      
		String idToString=driverId + "" + onlyDate;
	    fields.put("date", onlyDate);
		++id;
		idToString=driverId + "" + onlyDate + id;
		fields.put("hour", Integer.valueOf(cal.getTime().getHours()));
		keyValues.put("id", idToString);
		dbm.insertObject(objectClass, keyValues, fields);
		Thread.sleep(6000);
	}
	
	public Order(final ParseObject obj) throws ParseException {
		DBManager.initialize();

		this.driverId = obj.getString("driverId");
		this.date = obj.getString("date");
		this.slotId = obj.getString("slotId");
		this.hour = obj.getInt("hour");
		this.hoursAmount = (int)obj.get("hoursAmount");
		
	}
	
	public Order(final String id, DatabaseManager manager) throws ParseException {
		LOGGER.info("Create a new order by driver id");
		this.dbm = manager;
		dbm.initialize();
		
		Map<String, Object> keys = new HashMap<>();
		keys.put("id", id);
		Map<String,Object> returnV = dbm.getObjectFieldsByKey(objectClass, keys);
		
		this.driverId=returnV.get("driverId") + "";
		this.slotId= returnV.get("slotId") + "";
		this.date= returnV.get("date")+"";
		this.hour= (int)returnV.get("hour");
		this.hoursAmount= (int) returnV.get("hoursAmount");
		this.id=id;
	}

	/* Getters */

	public String getId() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return dbm.getObjectFieldsByKey(objectClass, key).get("id") + "";
	}
	
	public String getDriverId() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return dbm.getObjectFieldsByKey(objectClass, key).get("driverId") + "";
	}
	
	public String getSlotId() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return dbm.getObjectFieldsByKey(objectClass, key).get("slotId") + "";
	}
	
	public String getDate() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return dbm.getObjectFieldsByKey(objectClass, key).get("date")+"";
	}
	
	public int getHour() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return (int) dbm.getObjectFieldsByKey(objectClass, key).get("hour");
	}
	
	public int getHoursAmount() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return (int) dbm.getObjectFieldsByKey(objectClass, key).get("hoursAmount");
	}
	
	/* Setters */

	public void setDriverId(final String newDriverId) throws ParseException {
		LOGGER.info("Set driver id");
		if (newDriverId == null){
			LOGGER.severe("driver id can not be empty!");
			throw new IllegalArgumentException("driver id can not be empty!");
		}
		
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", newDriverId);
		newFields.put("slotId", this.slotId);
		newFields.put("hour", this.hour);
		newFields.put("date", this.date);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", this.hoursAmount);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		dbm.update(objectClass, keys, newFields);
	}
	
	public void setHoursAmount(final int newAmount) throws ParseException {
		LOGGER.info("Set hours amount");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("hour", this.hour);
		newFields.put("date", this.date);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", newAmount);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		dbm.update(objectClass, keys, newFields);
	}

	public void setSlotId(final String newSlot) throws ParseException {
		LOGGER.info("Set slot id");
		if (newSlot == null){
			LOGGER.severe("slot id can not be empty!");
			throw new IllegalArgumentException("slot id can not be empty!");
		}

		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", newSlot);
		newFields.put("hour", this.hour);
		newFields.put("date", this.date);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", this.hoursAmount);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		dbm.update(objectClass, keys, newFields);
	}
	
	public void setStartTime(final Date newStart) throws ParseException {
		LOGGER.info("Set order start time");
		if (newStart == null){
			LOGGER.severe("start date can not be empty!");
			throw new IllegalArgumentException("start date can not be empty!");
		}
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("date", this.date);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", this.hoursAmount);
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(newStart); // sets calendar time/date
	    @SuppressWarnings("deprecation")
		int onlyHour=cal.getTime().getHours();
		newFields.put("hour", onlyHour);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		dbm.update(objectClass, keys, newFields);
	}
	
	public void setDate(final Date newDate) throws ParseException {
		LOGGER.info("Set order end time");
		if (newDate == null){
			LOGGER.severe("end time can not be empty!");
			throw new IllegalArgumentException("end time can not be empty!");
		}
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(newDate); // sets calendar time/date
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime());      	   
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("hour", this.hour);
		newFields.put("date", onlyDate);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", this.hoursAmount);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		dbm.update(objectClass, keys, newFields);
	}
	
	/* Methods */
	
	//TODO::
//	public void checkAvaliablity(String slotId, Date start, Date end){
//		dbm.initialize();
//		Map<String, Object> key = new HashMap<String, Object>();
//		key.put("id", id);
//		return dbm.getObjectByFields("Order", values);
//	
//	}
	
	private static int minDifference(Date date1, Date date2) {
	    return (int) (date1.getTime() - date2.getTime()) / (1000 * 60 * 15);
	}
	
	private static void checkParameters(final String driverId, final String slotId, Date startTime, Date endTime) throws ParseException{
		if (!checkIfNull(driverId, slotId, startTime, endTime))
			return;
		LOGGER.severe("parameters can not be empty!");
		throw new IllegalArgumentException("parameters can not be empty!");
	}
		
	private static boolean checkIfNull(final String driverId, final String slotId, Date startTime, Date endTime){
		return driverId == null || slotId == null || startTime == null || endTime == null;
	}
	
	public void removeOrderFromDB() throws ParseException, InterruptedException {
		LOGGER.info("delete order from DB");
		dbm.initialize();
		Map<String, Object> fields = new HashMap<String, Object>();
		int newid=1;
		String idToString = driverId + "" + this.date + newid;
		fields.put("id", idToString);
		dbm.deleteObject(objectClass, fields);
	}
	
	public void CancelOrder(){
		LOGGER.info("cancel order from DB");
		DBManager.initialize();
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("hoursAmount", this.hoursAmount);
		fields.put("driverId", this.driverId);
		fields.put("slotId", this.slotId);
		fields.put("date", this.date);
		fields.put("hour", this.hour);
		fields.put("id",this.id);
		int newid=1;
		String idToString = driverId + "" + this.date + newid;
		fields.put("id", idToString);
		dbm.deleteObject(objectClass, fields);
		
	}
	
}
