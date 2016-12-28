package logic;

import org.parse4j.ParseException;
import data.management.DBManager;
import data.members.StickersColor;
import data.members.User;
import Exceptions.LoginException;

/**
 * @Author DavidCohen55
 */

public class LoginManager {

	private User user;

	public LoginManager() {
		DBManager.initialize();
		user = null;
	}

	/***
	 * userLogin uses the car number and password of the user.
	 * 
	 * @param carNumber
	 * @param password
	 * @return true if it found a match in the DB, else false.
	 * @throws LoginException
	 *             is thrown if there is a problem
	 */
	public boolean userLogin(String carNumber, String password) {
		try {
			User tmp = new User(carNumber);
			if (tmp.getPassword().equals(password))
				this.user = tmp;
			return user.getPassword().equals(password);
		} catch (Exception e) {
			return false;
		}
	}

	public String userValueCheck(String name, String phone, String email, String car) {
		try {
			if (car != null) {
				new User(car);
				return "User already exist";
			}
		} catch (Exception e) {
		}
		return name != null && name.matches(".*\\d.*") ? "User has integer"
				: phone != null && phone.length() != 10 ? "Phone need to be in size 10"
						: phone != null && !phone.startsWith("05") ? "Phone should start with 05"
								: phone != null && phone.matches(".*[a-zA-z].*") ? "Phone contains only integers"
										: (email != null && !email.matches(
												"[\\d\\w\\.]+@(campus.technion.ac.il|gmail.com|walla.com|hotmail.com|t2.technion.ac.il)"))
														? "Invalid email address"
														: car == null || car.length() == 7 ? "Good Params"
																: "Car need to be in size 7";
	}

	/***
	 * 
	 * @param name
	 *            shouldn't have integer in the name
	 * @param pass
	 * @param phone
	 *            need to be in size of 10, start with 05 and contain only
	 *            integers
	 * @param car
	 *            need to be in size of 7
	 * @param email
	 *            should be a valid email address
	 * @param type
	 *            should be one of the enum types
	 * 
	 * @return the id of the user in the DB
	 * @throws LoginException
	 *             is thrown if there is a problem with the user value according
	 *             to the UserValueCheck function
	 */
	public String userSignUp(String name, String pass, String phone, String car, String email, StickersColor type)
			throws LoginException {
		this.user = null;
		String $ = userValueCheck(name, phone, email, car);
		if (!"Good Params".equals($))
			throw new LoginException($);
		try {
			this.user = new User(name, pass, phone, car, email, type, null);
			$ = this.user.getobjectId();
		} catch (Exception e) {
			$ = "";
		}
		return $;
	}

	/***
	 * Update the user that has the carNumber and update his row for the
	 * following values if they are correct
	 * 
	 * @param carNumber
	 * @param name
	 *            new name of the user
	 * @param phoneNumber
	 *            new phone number
	 * @param email
	 *            new email address
	 * @param newCar
	 *            new car number(in case of a switch) shouldn't be in the system
	 * @return true if everything is correct
	 * @throws LoginException
	 *             is thrown if there is a problem with the user value according
	 *             to the UserValueCheck function
	 */
	public boolean userUpdate(String carNumber, String name, String phoneNumber, String email, String newCar, StickersColor type)
			throws LoginException {
		try {
			String s = userValueCheck(name, phoneNumber, email, newCar.equals(carNumber) ? null : newCar);
			if (!"Good Params".equals(s))
				throw new LoginException(s);
			if (name != null)
				this.user.setName(name);
			if (phoneNumber != null)
				this.user.setPhoneNumber(phoneNumber);
			if (email != null)
				this.user.setEmail(email);
			if (newCar != null)
				this.user.setCarName(newCar);
			if (type != null)
				this.user.setSticker(type);
		} catch (ParseException e) {
			throw new LoginException("connection problem with DB");
		}
		return true;
	}

	public String getEmail() {
		return this.user.getEmail();
	}

	public String getUserName() {
		return this.user.getName();
	}

	public String getCarNumber() {
		return this.user.getCarNumber();
	}

	public void deleteUser() throws ParseException {
		user.deleteParseObject();
	}
	
	public String getPhoneNumber() {
		return this.user.getPhoneNumber();
	}
	
	public StickersColor getSticker() {
		return this.user.getSticker();
	}
}
