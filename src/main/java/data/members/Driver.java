package data.members;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.management.DBManager;

public class Driver {

	/**
	 * @author Inbal Matityahu
	 * @since 5/2/16 This class represent a single driver
	 */
	
	// The driver's id (the id of the table). Should be a unique value.
	private String id;
	
	// The driver's car id
	private String carId;
	
	// The driver's email
	private String email;

	// The driver's password. Through which the driver can login
	private String password;

	private final String objectClass = "Driver";
	
	/* Constructors */

	// Create a new driver. Will result in a new driver in the DB.
	public Driver(final String id, final String email, final String carId, final String password) throws ParseException {
		
		DBManager.initialize();
		
		validateArgument(id, email, carId, password);
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("email", email);
		fields.put("carId", carId);
		fields.put("password", password);
		
		keyValues.put("id", id);
		DBManager.insertObject(objectClass, keyValues, fields);
	}
	
	public Driver(final ParseObject obj) throws ParseException {
		DBManager.initialize();

		id = obj.getString("id");
		email = obj.getString("email");
		carId = obj.getString("carId");
		password = obj.getString("password");
		
	}
	
	public Driver(final String id) throws ParseException {
		DBManager.initialize();

		Map<String, Object> keys = new HashMap<>();
		keys.put("id", id);
		Map<String,Object> returnV = DBManager.getObjectFieldsByKey(objectClass, keys);
		
		this.email=returnV.get("email") + "";
		this.carId= returnV.get("carId") + "";
		this.password= returnV.get("password") + "";
		this.id=id;
	}
	

	/* Getters */

	public String getId() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return DBManager.getObjectFieldsByKey(objectClass, key).get("id") + "";
	}
	
	public String getEmail() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return DBManager.getObjectFieldsByKey(objectClass, key).get("email") + "";
	}
	
	public String getCarId() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return DBManager.getObjectFieldsByKey(objectClass, key).get("carId") + "";
	}
	
	public String getPassword() {
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		return DBManager.getObjectFieldsByKey(objectClass, key).get("password") + "";
	}
	
	/* Setters */

	public void setId(final String id) throws ParseException {
		//TODO: validateArgument
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("id", id);
		newFields.put("email", this.email);
		newFields.put("carId", this.carId);
		newFields.put("password", this.password);
			
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}

	public void setEmail(final String newEmail) throws ParseException {
		//TODO: validateArgument
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("id", this.id);
		newFields.put("email", newEmail);
		newFields.put("carId", this.carId);
		newFields.put("password", this.password);
					
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}
	
	public void setCarId(final String newCarId) throws ParseException {
		//TODO: validateArgument
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("id", this.id);
		newFields.put("email", this.email);
		newFields.put("carId", newCarId);
		newFields.put("password", this.password);
							
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}
	
	public void setPassword(final String newPassword) throws ParseException {
		//TODO: validateArgument
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("id", this.id);
		newFields.put("email", this.email);
		newFields.put("carId", this.carId);
		newFields.put("password", newPassword);
									
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("id", this.id);
		DBManager.update(objectClass, keys, newFields);
	}
	
	/* Methods */

	private void validateArgument(final String id, final String email, final String carId, final String password)
		throws IllegalArgumentException {
		//TODO: Move to Validation class
		if (id == null)
			throw new IllegalArgumentException("id can not be empty!");
		if (email == null)
			throw new IllegalArgumentException("email can not be empty!");
		if (carId == null)
			throw new IllegalArgumentException("carId can not be empty!");
		if (password == null)
			throw new IllegalArgumentException("password can not be empty!");
	}
	
	private void checkId(final String id) throws IllegalArgumentException {
		//TODO: Move to Validation class
		DBManager.initialize();
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		if (DBManager.getObjectFieldsByKey(objectClass, key).get("id")!=null)
			throw new IllegalArgumentException("id already exist!");
	}
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateMail(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		        return matcher.find();
	}
	
	private void checkEmail(final String email) throws IllegalArgumentException {
		//TODO: Move to Validation class
		if (!validateMail(email)) 
			throw new IllegalArgumentException("email is illegal!");
	}
	
	private void checkCarId(final String carId) throws IllegalArgumentException {
		//TODO: Move to Validation class
		if(carId.length()!=7)
			throw new IllegalArgumentException("carId is illegal! Must contain 7 characters!");
	}
	
	boolean validatePassword(String password, int n, int m) {
		  if (password == null || password.length() < n || password.length() > m) {
		    return false;
		  }
		  boolean upper = false;
		  boolean lower = false;
		  boolean digit = false;
		  boolean symbol = false;
		  char[] pass = password.toCharArray();
		  for (char ch : pass) {
		    if (Character.isUpperCase(ch)) {
		      upper = true;
		    } else if (Character.isLowerCase(ch)) {
		      lower = true;
		    } else if (Character.isDigit(ch)) {
		      digit = true;
		    } else { // or some symbol test.
		      symbol = true;
		    }
		    // This short-circuits the rest of the loop when all criteria are true.
		    if (upper && lower && digit && symbol) {
		      return true;
		    }
		  }
		  return upper && lower && digit && symbol;
		}
	
	private void checkPassword(final String password) throws IllegalArgumentException {
		//TODO: Move to Validation class
		if (!validatePassword(password, 1, 10))
			throw new IllegalArgumentException("password is illegal!");
			
	}
	
	public void removeDriverFromDB() throws ParseException {
		DBManager.initialize();
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("email", this.email);
		fields.put("carId", this.carId);
		fields.put("password", this.password);
		fields.put("id", this.id);
		DBManager.deleteObject(objectClass, fields);
	}
}
