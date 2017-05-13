package gui.driver.app;

import java.util.HashMap;
import java.util.Map;

import data.management.DBManager;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

import Exceptions.LoginException;

public class RegistrationController {
	
	@FXML
	private Button backButton;
	@FXML
	private TextField idField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField carNumField;
	@FXML
	private PasswordField pwField;
	@FXML
	private PasswordField confirmPwField;
	@FXML
	private Button createButton;
	@FXML
	private Label idLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label carNumLabel;
	@FXML
	private Label pwLabel;
	@FXML
	private Label confirmPwLabel;
	
	@FXML
	public void backButtonClicked(ActionEvent event) throws Exception {
		 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Login");
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
			window.setScene(new Scene(root,400,500));		
			window.show();
	}
	@FXML
	public void createButtonClicked(ActionEvent event) throws Exception {
		
		String id = idField.getText();
		String email = emailField.getText();
		String carNum = carNumField.getText();
		String pw = pwField.getText();
		String confirmPw = confirmPwField.getText();
		
		
		if (pw != confirmPw){
			//pwField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			//confirmPwField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");	
		}
		
		
		Map<String, Object> fields = new HashMap<String, Object>(), keys = new HashMap<String, Object>();
		keys.put("id", id);
		fields.put("email", email);
		fields.put("carId", carNum);
		fields.put("password", pw);
		try {
			DBManager.register("Driver", keys, fields);
			// TODO:c notify about user Creation
			
		} catch(LoginException e){
			// TODO: what about other input
			
			if(e.toString() == "user already exists"){
				// TODO: notify user
				
			}
		}
	}
	
}
