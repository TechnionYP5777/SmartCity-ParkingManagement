package gui.driver.app;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.*;
import data.management.DBManager;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import Exceptions.LoginException;


public class LoginContorller {
	
	@FXML
	private TextField idField;
	@FXML
	private PasswordField pwField;
	@FXML
	private Hyperlink forgotPwHyperLink;
	@FXML
	private Label errorLabel;
	@FXML
	private Button loginButton;
	@FXML
	private Button createNewButton;
	
	@FXML
	public void forgotPwClicked(ActionEvent event) throws Exception {
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Forgot your password");
		Parent root = FXMLLoader.load(getClass().getResource("ForgotPwScreen.fxml")); 
		window.setScene(new Scene(root,400,500));		
		window.show();
	}
	@FXML
	public void loginButtonClicked(ActionEvent event) throws Exception {
		
		String id = idField.getText();
		String pw = pwField.getText();
		
		if (id.equals("") || pw.equals("")){
			errorLabel.setVisible(true);
			return;		
		}
				
		try {
			DBManager.login("Driver", "id", id, "password", pw);
			// TODO: direct to or's screen

		} catch (LoginException e){
			String err = e.toString();
			if (err.equals("user doesn't exists") || err.equals("password doesn't match")){
				errorLabel.setVisible(true);
				return;		
			}

		}
		
	}
	@FXML
	public void createButtonClicked(ActionEvent event) throws Exception{
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Registration");
		Parent root = FXMLLoader.load(getClass().getResource("RegistrationScreen.fxml")); 
		window.setScene(new Scene(root,400,700));		
		window.show();


	}
}
