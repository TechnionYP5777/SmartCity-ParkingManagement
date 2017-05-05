package gui.driver.app;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class RegistrationController {
	
	@FXML
	private Button backButton;
	
	public void backButtonClicked(ActionEvent event) throws Exception{
		 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Login");
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
			window.setScene(new Scene(root,300,400));		
			window.show();
	}
	
}
