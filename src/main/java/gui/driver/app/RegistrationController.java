package gui.driver.app;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class RegistrationController {
	
	private Button backButton;
	private TextField idField;
	private TextField emailField;
	private TextField carNumField;
	private PasswordField pwField;
	private PasswordField confirmPwField;
	private Button createButton;
	
	
	public void backButtonClicked(ActionEvent event) throws Exception {
		 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Login");
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
			window.setScene(new Scene(root,300,400));		
			window.show();
	}
	public void createButtonClicked(ActionEvent event) throws Exception {
		String id = idField.getText();
		String email = emailField.getText();
		String carNum = carNumField.getText();
		String pw = pwField.getText();
		String confirmPw = confirmPwField.getText();
		
		// TODO: validate and create a new user
	}
	
}
