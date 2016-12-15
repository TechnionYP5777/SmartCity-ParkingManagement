package logic;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.StickersColor;
import data.members.User;

/**
 * @Author DavidCohen55
 */

public class Login {
	private User user;
	
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


	public String userSignUp(String name,String pass,String phone,String car,StickersColor type) {
		user = null;
		String str="";
		try {
			user = new User(name,pass,phone,car,type,null);
			str=user.getTableID();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			str="";
		}
		return str;
	}
	
	public void deleteUser() throws ParseException{
		user.DeleteUser();
	}
}
