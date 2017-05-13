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
	private Label idLabel;
	@FXML
	private Label pwLabel;
	
	@FXML
	public void forgotPwClicked(ActionEvent event) throws Exception {
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Forgot your password");
		Parent root = FXMLLoader.load(getClass().getResource("ForgotPwScreen.fxml")); 
		window.setScene(new Scene(root,300,400));		
		window.show();
	}
	@FXML
	public void loginButtonClicked(ActionEvent event) throws Exception {
		String id = idField.getText();
		String pw = pwField.getText();
		
		// TODO: validate fields, check existance and move to main screen
		
		try {
			DBManager.login("Driver", "id", id, "password", pw);

			// TODO: direct to or's screen
			
			
		} catch (LoginException e){
			
			if (e.toString() == "user doesn't exists"){
				idField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
				idLabel.setVisible(true);
				return;
				
			}
			if (e.toString() == "password doesn't match"){
				pwField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
				pwLabel.setVisible(true);
				return;
			}
			
		}
		
	}
	@FXML
	public void createButtonClicked(ActionEvent event) throws Exception{
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Registration");
		Parent root = FXMLLoader.load(getClass().getResource("RegistrationScreen.fxml")); 
		window.setScene(new Scene(root,450,700));		
		window.show();


	}
}
