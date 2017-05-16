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
import javafx.scene.control.Label;

public class ForgotPwController {
	
	@FXML
	private Button backButton;
	@FXML
	private TextField idField;
	@FXML
	private TextField emailField;
	@FXML
	private Button restoreButton;
	@FXML
	private Label upperStatusLabel;
	@FXML
	private Label lowerStatusLabel;
	
	@FXML
	public void backButtonClicked(ActionEvent event) throws Exception {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Login");
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
		window.setScene(new Scene(root,400,500));		
		window.show();
	}
		
	@FXML
	public void restoreButtonClicked(ActionEvent event) throws Exception {
		
		String id = idField.getText();
		String email = emailField.getText();
		lowerStatusLabel.setText("ID or Email are wrong");
		
		if (id.equals("") || email.equals("")){
			lowerStatusLabel.setVisible(true);
			return;		
		}
		
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", id);
		String emailFromDb = (String)DBManager.getObjectFieldsByKey("Driver", key).get("email");
		if (emailFromDb == null || !emailFromDb.equals(email)) {
			lowerStatusLabel.setVisible(true);
			return;	
		}
		
		// TODO: send email to the user
		
		upperStatusLabel.setText("An email with your password was sent");
		lowerStatusLabel.setText("You are redirected to the login screen");
		lowerStatusLabel.setStyle(" -fx-text-fill: green; -fx-font-size: 15px; -fx-font-weight: bold");
		upperStatusLabel.setVisible(true);
		lowerStatusLabel.setVisible(true);
		
		
		//TODO: run separate thread with this:
		
		Thread.sleep(3000);
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Login");
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
		window.setScene(new Scene(root,400,500));		
		window.show();
		
	}
}