package logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
	
	// The desired time in quarters
	private int hour;
	
	// The desired time
	private String actualHour;
	
	// The desired amount of hours
	private int hoursAmount;
	
	// order's price
	private int price;
	
	private final String objectClass = "Order";
	
	private DatabaseManager dbm;
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/* Constructors */
	
	public Order(DatabaseManager manager){
		this.dbm=manager;
	}

	// Create a new order. Will result in a new order in the DB.
	@SuppressWarnings("deprecation")
	public Order(final String driverId, final String slotId, Date startTime, Date endTime,int price, DatabaseManager manager) throws ParseException, InterruptedException {
		LOGGER.info("Create a new order by slot id, start time, end time");
		this.dbm = manager;
		dbm.initialize();
		
		checkParameters(driverId, slotId, startTime, endTime);
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("driverId", driverId);
		fields.put("slotId", slotId);
		fields.put("price", price);
		int hours =minDifference(endTime, startTime);
		if(price<0){
			LOGGER.severe("order price have to be positive!");
			throw new IllegalArgumentException("order price have to be positive!");
		}
		if(hours<=0)
			return;
		fields.put("hoursAmount", hours);
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime());      
		String idToString=createIdString(driverId, slotId, onlyDate, cal.getTime().getHours()+":"+cal.getTime().getMinutes());
	    fields.put("date", onlyDate);
	    fields.put("actualHour", cal.getTime().getHours()+":"+cal.getTime().getMinutes());
		fields.put("hour", getHourInQuarter(startTime));
		keyValues.put("id", idToString);
		dbm.insertObject(objectClass, keyValues, fields);
	}
	
	public Order(final ParseObject obj) throws ParseException {
		DBManager.initialize();
		this.id = obj.getString("id");
		this.driverId = obj.getString("driverId");
		this.date = obj.getString("date");
		this.slotId = obj.getString("slotId");
		this.hour = obj.getInt("hour");
		this.actualHour = obj.getString("actualHour");
		this.hoursAmount = (int)obj.get("hoursAmount");
		this.price = (int)obj.get("price");
	}
	
	public Order(final String id, DatabaseManager manager) throws ParseException {
		LOGGER.info("Create a new order by driver id");
		this.dbm = manager;
		dbm.initialize();
		
		Map<String, Object> keys = new HashMap<>();
		keys.put("id", id);
		Map<String,Object> returnV = dbm.getObjectFieldsByKey(objectClass, keys);
		this.actualHour =returnV.get("actualHour") + "";
		this.driverId=returnV.get("driverId") + "";
		this.slotId= returnV.get("slotId") + "";
		this.date= returnV.get("date")+"";
		this.hour= (int)returnV.get("hour");
		this.hoursAmount= (int) returnV.get("hoursAmount");
		this.price= (int) returnV.get("price");
		this.id=id;
	}

	/* Getters */

	public String getId() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return dbm.getObjectFieldsByKey(objectClass, key).get("id") + "";
	}
	
	public String getActualHour() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return dbm.getObjectFieldsByKey(objectClass, key).get("actualHour") + "";
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
	
	public int getPrice() {
		dbm.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return (int) dbm.getObjectFieldsByKey(objectClass, key).get("price");
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
		newFields.put("price", this.price);
		newFields.put("actualHour", this.actualHour);
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
		newFields.put("price", this.price);
		newFields.put("actualHour", this.actualHour);
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
	
	public void setPrice(final int newPrice) throws ParseException {
		LOGGER.info("Set hours amount");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("price", newPrice);
		newFields.put("actualHour", this.actualHour);
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("hour", this.hour);
		newFields.put("date", this.date);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", this.hoursAmount);
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
		newFields.put("price", this.price);
		newFields.put("actualHour", this.actualHour);
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
		newFields.put("price", this.price);
		newFields.put("actualHour", this.actualHour);
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("date", this.date);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", this.hoursAmount);
		int onlyHour=this.getHourInQuarter(newStart);
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
		newFields.put("price", this.price);
		newFields.put("actualHour", this.actualHour);
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
	
	@SuppressWarnings("deprecation")
	public void setActualHour(final Date newDate) throws ParseException {
		LOGGER.info("Set order actual hour");
		if (newDate == null){
			LOGGER.severe("end time can not be empty!");
			throw new IllegalArgumentException("end time can not be empty!");
		}
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(newDate); // sets calendar time/date    	   
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("price", this.price);
		newFields.put("actualHour", cal.getTime().getHours()+":"+cal.getTime().getMinutes());
		newFields.put("driverId", this.driverId);
		newFields.put("slotId", this.slotId);
		newFields.put("hour", this.hour);
		newFields.put("date", this.date);
		newFields.put("id", this.id);
		newFields.put("hoursAmount", this.hoursAmount);
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		dbm.update(objectClass, keys, newFields);
	}
	
	/* Methods */

	public String createIdString(final String driverId, final String slotId, String startTime, String hour){
		String s=driverId+" "+startTime+" "+hour+" "+slotId;
		return s;
	}
	
	public int getHourInQuarter(Date demandDate){
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(demandDate); 
		@SuppressWarnings("deprecation")
		int hour = Integer.valueOf(cal.getTime().getHours());
		hour = hour*4;
		@SuppressWarnings("deprecation")
		int min = Integer.valueOf(cal.getTime().getMinutes());
		switch (min){
			case 0: 
				break;
			case 15:
				hour=hour+1;
				break;
			case 30:
				hour=hour+2;
				break;
			case 45:
				hour=hour+3;
				break;
		}
		return hour;
	}
	
	public boolean checkAvaliablity(String slotId, Date start, int duration){
		List<ParseObject> tempListOrders = dbm.getAllObjects("Order", 600);
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		int wantedStartingHour = cal.get(Calendar.HOUR_OF_DAY);
		int wantedStartingQuarter = cal.get(Calendar.MINUTE);
		int wantedStartTime = wantedStartingHour*4+wantedStartingQuarter;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		for(ParseObject p : tempListOrders){
			cal.setTime(start);
			int orderStartTime = p.getInt("hour");
			int orderTimeAmount = p.getInt("hoursAmount");
			int orderEndTime = orderStartTime+orderTimeAmount;
			Boolean noValidParkingCondition = (orderStartTime == wantedStartingHour);
			noValidParkingCondition = Boolean.logicalOr(noValidParkingCondition,(orderEndTime*4) == (wantedStartingHour*4+duration));
			noValidParkingCondition = Boolean.logicalOr(noValidParkingCondition,orderStartTime<wantedStartTime && (orderEndTime) > wantedStartTime);
			noValidParkingCondition = Boolean.logicalOr(noValidParkingCondition, wantedStartTime<orderStartTime && (wantedStartingHour*4+duration) > (orderStartTime*4));
			if (noValidParkingCondition)
				return new Boolean(false);
			String orderDate = p.getString("date");
			if(formatDate.format(cal.getTime()).equals(orderDate)){
				if(noValidParkingCondition){
					return new Boolean(false);
				}
			}
		}
		return new Boolean(true);
	}
	
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
		String idToString=createIdString(this.driverId, this.slotId, this.date, this.actualHour);
		fields.put("id", idToString);
		dbm.deleteObject(objectClass, fields);
	}
	
	public void CancelOrder(){
		LOGGER.info("cancel order from DB");
		DBManager.initialize();
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("price", this.price);
		fields.put("hoursAmount", this.hoursAmount);
		fields.put("driverId", this.driverId);
		fields.put("slotId", this.slotId);
		fields.put("date", this.date);
		fields.put("hour", this.hour);
		fields.put("actualHour", this.actualHour);
		fields.put("id",this.id);
		String idToString = createIdString(driverId, slotId, date, actualHour);
		fields.put("id", idToString);
		dbm.deleteObject(objectClass, fields);
		
	}
	
}
