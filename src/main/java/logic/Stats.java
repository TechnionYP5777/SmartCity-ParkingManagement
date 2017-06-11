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
/**
 * @author Toma
 * @since 11/6/17 This class represent a parking took place
 */
public class Stats {	
	// The stat id. Should be a unique value.
	private String id;
		
	// The demand slot id
	private String slotId;
		
	// The demand day
	private String date;
	
	// The desired time
	private int hour;
	
	// The desired amount of hours
	private int hoursAmount;
	
	private final String objectClass = "Stats";
	
	private DatabaseManager dbm;
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
/* Constructors */
	
	public Stats(){
		
	}
	
	@SuppressWarnings("deprecation")
	public Stats(final String slotId, Date startTime, Date endTime, DatabaseManager manager ) throws ParseException, InterruptedException{
		LOGGER.info("Create a new stats by slot id, start time, end time");
		this.dbm = manager;
		dbm.initialize();
		
		checkParameters(slotId, startTime, endTime);
		
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("slotId", slotId);
		int hours =hoursDifference(endTime, startTime);
		if (hours<=0)
			return;
		fields.put("hoursAmount", hours + 1);
		int id=0;
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(startTime); // sets calendar time/date
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    String onlyDate = format1.format(cal.getTime());  
	    String idToString = slotId + "" + onlyDate;
	    fields.put("date", onlyDate);
		for (int i=0; i<=hours; ++i){
			++id;
			idToString = slotId + "" + onlyDate + id;
			fields.put("hour", Integer.valueOf(cal.getTime().getHours()));
		    cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
			keyValues.put("id", idToString);
			dbm.insertObject(objectClass, keyValues, fields);
			Thread.sleep(6000);
		}	
	}
	
	public Stats(final ParseObject obj) throws ParseException {
		DBManager.initialize();

		this.date = obj.getString("date");
		this.slotId = obj.getString("slotId");
		this.hour = obj.getInt("hour");
		this.hoursAmount = (int)obj.get("hoursAmount");		
	}
	
	public Stats(final String id, DatabaseManager manager) throws ParseException {
		LOGGER.info("Create a new order by id");
		this.dbm = manager;
		dbm.initialize();
		
		Map<String, Object> keys = new HashMap<>();
		keys.put("id", id);
		Map<String,Object> returnV = dbm.getObjectFieldsByKey(objectClass, keys);
		
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
	
	/*Methods*/
	private static void checkParameters(final String slotId, Date startTime, Date endTime) throws ParseException{
		if ( slotId == null || startTime == null || endTime == null){
			LOGGER.severe("parameters can not be empty!");
			throw new IllegalArgumentException("parameters can not be empty!");
		}
		if(endTime.before(startTime)){
			LOGGER.severe("endTime cannot be before dtartTime");
			throw new IllegalArgumentException("endTime cannot be before dtartTime ");
		}
	}
	
	private static int hoursDifference(Date date1, Date date2) {
	    return (int) (date1.getTime() - date2.getTime()) / (1000 * 60 * 60);
	}
		

}