package gui.driver.app;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ForgotPwController {
	
	
	private Button backButton;
	private TextField usernameField;
	private TextField emailField;
	private Button restoreButton;
	
	public void backButtonClicked(ActionEvent event) throws Exception {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Login");
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
		window.setScene(new Scene(root,300,400));		
		window.show();
	}
	
	public void restoreButtonClicked(ActionEvent event) throws Exception {
		String username = usernameField.getText();
		String email = emailField.getText();
		// TODO: validate fields, maybe pop up and redirect to main screen
	}
}