package logic;

import org.parse4j.ParseException;
import data.management.DBManager;
import data.members.StickersColor;
import data.members.User;
import Exceptions.LoginException;
import java.util.Date;

/**
 * @author DavidCohen55
 * @author sefialbo
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
	public boolean userLogin(final String carNumber, final String $) {
		try {
			final User tmp = new User(carNumber);
			if (tmp.getPassword().equals($)) {
				user = tmp;
				user.setLastLoginTime(new Date());
			}
			return user.getPassword().equals($);
		} catch (final Exception e) {
			return false;
		}
	}

	public String userValueCheck(final String $, final String phone, final String email, final String car) {
		try {
			if (car != null) {
				new User(car);
				return "User already exist";
			}
		} catch (final Exception e) {
		}
		return $ != null && $.matches(".*\\d.*") ? "User has integer"
				: phone != null && phone.length() != 10 ? "Phone need to be in size 10"
						: phone != null && !phone.startsWith("05") ? "Phone should start with 05"
								: phone != null && phone.matches(".*[a-zA-z].*") ? "Phone contains only integers"
										: email != null && !email.matches(
												"[\\d\\w\\.]+@(campus.technion.ac.il|gmail.com|walla.com|hotmail.com|t2.technion.ac.il)")
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
	public String userSignUp(final String name, final String pass, final String phone, final String car, final String email, final StickersColor type)
			throws LoginException {
		user = null;
		String $ = userValueCheck(name, phone, email, car);
		if (!"Good Params".equals($))
			throw new LoginException($);
		try {
			user = new User(name, pass, phone, car, email, type, null);
			$ = user.getObjectId();
		} catch (final Exception e) {
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
	public boolean userUpdate(final String carNumber, final String name, final String phoneNumber, final String email, final String newCar,
			final StickersColor type) throws LoginException {
		try {
			final String s = userValueCheck(name, phoneNumber, email, newCar.equals(carNumber) ? null : newCar);
			if (!"Good Params".equals(s))
				throw new LoginException(s);
			if (name != null)
				user.setName(name);
			if (phoneNumber != null)
				user.setPhoneNumber(phoneNumber);
			if (email != null)
				user.setEmail(email);
			if (newCar != null)
				user.setCarName(newCar);
			if (type != null)
				user.setSticker(type);
		} catch (final ParseException e) {
			throw new LoginException("connection problem with DB");
		}
		return true;
	}

	public String getEmail() {
		return user.getEmail();
	}

	public String getUserName() {
		return user.getName();
	}

	public String getCarNumber() {
		return user.getCarNumber();
	}

	public void deleteUser() throws ParseException {
		user.deleteParseObject();
	}

	public String getPhoneNumber() {
		return user.getPhoneNumber();
	}

	public StickersColor getSticker() {
		return user.getSticker();
	}

	public User getUser() {
		return user;
	}
}
