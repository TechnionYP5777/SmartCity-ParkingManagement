package logic;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.StickersColor;
import data.members.User;
import Exceptions.LoginException;

/**
 * @Author DavidCohen55
 */

public class Login {
	private User user;

	public Login() {
		DBManager.initialize();
	}

	public boolean userLogin(String carNumber, String password) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		query.whereEqualTo("carNumber", carNumber);
		try {
			List<ParseObject> carList = query.find();
			return carList != null && !carList.isEmpty() && password.equals(carList.get(0).getString("password"));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String UserValueCheck(String name, String pass, String phone, String car) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("PMUser");
		query.whereEqualTo("carNumber", car);
		try {
			int count = 0;
			if (query.find() != null) {
				for (@SuppressWarnings("unused")
				ParseObject ¢ : query.find())
					++count;
				if (count > 0)
					return "already exist";
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return name.matches(".*\\d.*") ? "user has integer"
				: phone.length() != 10 ? "phone need to be in size 10"
						: !phone.startsWith("05") ? "phone should start with 05"
								: phone.matches(".*[a-zA-z].*") ? "phone contains only integers"
										: car.length() == 7 ? "Good Params" : "car need to be in size 7";
	}

	public String userSignUp(String name, String pass, String phone, String car, StickersColor type)
			throws LoginException {
		user = null;
		String $ = UserValueCheck(name, pass, phone, car);
		if (!"Good Params".equals($))
			throw new LoginException($);
		try {
			user = new User(name, pass, phone, car, type, null);
			$ = user.getTableID();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			$ = "";
		}
		return $;
	}

	public boolean userUpdate(String carNumber, String name, String phoneNumber) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		query.whereEqualTo("carNumber", carNumber);
		try {

			List<ParseObject> userList = query.find();
			if (userList != null && !userList.isEmpty()) {
				userList.get(0).put("username", name);
				userList.get(0).put("phoneNumber", phoneNumber);
				userList.get(0).save();
			}

		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public void deleteUser() throws ParseException {
		user.DeleteUser();
	}
}
