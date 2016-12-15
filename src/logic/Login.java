package logic;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

/**
 * @Author DavidCohen55
 */

public class Login {
	public Login() {
		DBManager.initialize();
	}

	public boolean userLogin(String carNumber, String password) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
		query.whereEqualTo("carNumber", carNumber);
		try {
			List<ParseObject> carList = query.find();
			return carList != null && !carList.isEmpty() && password.equals(carList.get(0).getString("userPass"));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
}
