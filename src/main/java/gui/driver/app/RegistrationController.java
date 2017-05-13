package gui.driver.app;

import java.util.HashMap;
import java.util.Map;
import util.Validation;
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
	private void setAllFieldsAndLabels() {
		
		idLabel.setVisible(false);
		emailLabel.setVisible(false);
		carNumLabel.setVisible(false);
		pwLabel.setVisible(false);
		confirmPwLabel.setVisible(false);
		
		idField.setStyle("-fx-border-color: #C4C4C4 ; -fx-border-width: 1px ;");
		emailField.setStyle("-fx-border-color: #C4C4C4 ; -fx-border-width: 1px ;");
		carNumField.setStyle("-fx-border-color: #C4C4C4 ; -fx-border-width: 1px ;");
		pwField.setStyle("-fx-border-color: #C4C4C4 ; -fx-border-width: 1px ;");
		confirmPwField.setStyle("-fx-border-color: #C4C4C4 ; -fx-border-width: 1px ;");

	}
	
	@FXML
	public void createButtonClicked(ActionEvent event) throws Exception {
				
		String id = idField.getText();
		String email = emailField.getText();
		String carNum = carNumField.getText();
		String pw = pwField.getText();
		String confirmPw = confirmPwField.getText();
		
		setAllFieldsAndLabels();
		
		boolean valid = true;
		
		// TODO: validate id
		
		if (!Validation.validateMail(email)){
			valid = false;
			emailField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			emailLabel.setVisible(true);
		}
		
		if (!Validation.isLicensePlatePattern(carNum)){
			valid = false;
			carNumField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			carNumLabel.setVisible(true);
		}
		
		if (!Validation.validatePassword(pw, 6, Integer.MAX_VALUE)){
			valid = false;
			pwField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");	
			pwLabel.setVisible(true);
		}
		
		if (pw != confirmPw){
			valid = false;
			confirmPwField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");	
			confirmPwLabel.setVisible(true);
		}
		
		
		if (valid){
			
			Map<String, Object> fields = new HashMap<String, Object>(), keys = new HashMap<String, Object>();
			keys.put("id", id);
			fields.put("email", email);
			fields.put("carId", carNum);
			fields.put("password", pw);
	
			try {
				DBManager.register("Driver", keys, fields);
				// TODO:c notify about user Creation
				
			} catch(LoginException e){
				if(e.toString() == "user already exists"){
					idField.setText("User with such id already exists");
					idField.setVisible(true);
				}
			}
		}
	}
	
}
