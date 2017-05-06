package gui.driver.app;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.event.*;
import javafx.fxml.FXMLLoader;

public class LoginContorller {
	
	private TextField usernameField;
	private PasswordField pwField;
	private Hyperlink forgotPwHyperLink;
	private Button loginButton;
	private Hyperlink createNewAccHyperLink;
	
	
	public void forgotPwClicked(ActionEvent event) throws Exception {
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Forgot your password");
		Parent root = FXMLLoader.load(getClass().getResource("ForgotPwScreen.fxml")); 
		window.setScene(new Scene(root,300,400));		
		window.show();
	}
	
	public void loginButtonClicked(ActionEvent event) throws Exception {
		String username = usernameField.getText();
		String pw = pwField.getText();
		// TODO: validate fields, check existance and move to main screen
		
	}
	
	public void createHyperLinkClicked(ActionEvent event) throws Exception{
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Registration");
		Parent root = FXMLLoader.load(getClass().getResource("RegistrationScreen.fxml")); 
		window.setScene(new Scene(root,400,400));		
		window.show();


	}
}
