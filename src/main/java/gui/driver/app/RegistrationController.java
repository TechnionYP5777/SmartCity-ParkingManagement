package gui.driver.app;

import java.util.HashMap;
import java.util.Map;
import util.Validation;
import data.management.DBManager;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.EmailNotification;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;

import Exceptions.LoginException;


public class RegistrationController {
	
	/**
	 * @author dshames
	 *  This class contains the controller of the registration screen
	 */
	
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
	private Label statusLabel;
	@FXML
	private ProgressIndicator progressIndicator;
	
	
	@FXML
    protected void initialize(){
		progressIndicator.setVisible(false);
		idField.setFocusTraversable(false);
		emailField.setFocusTraversable(false);
		carNumField.setFocusTraversable(false);
		pwField.setFocusTraversable(false);
		confirmPwField.setFocusTraversable(false);
	}
	
	@FXML
	public void backButtonClicked(ActionEvent event) throws Exception {
		 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Login");
			Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
			window.setScene(new Scene(root,400,550));		
			window.show();
	}
	private void setAllFieldsAndLabels() {
		
		idLabel.setVisible(false);
		emailLabel.setVisible(false);
		carNumLabel.setVisible(false);
		pwLabel.setVisible(false);
		confirmPwLabel.setVisible(false);
		statusLabel.setVisible(false);
		
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

		if (!Validation.isValidDriverId(id)){
			valid = false;
			idField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
			idLabel.setVisible(true);
		}
		
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
		
		if (!pw.equals(confirmPw)){
			valid = false;
			confirmPwField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");	
			confirmPwLabel.setVisible(true);
		}
		
		if (valid){	
			statusLabel.setVisible(true);
			Task<String> registrationTask = new Task<String>() {
	            @Override
	            protected String call() throws Exception {	
	            	Map<String, Object> fields = new HashMap<String, Object>(), keys = new HashMap<String, Object>();
	    			keys.put("id", id);
	    			fields.put("email", email);
	    			fields.put("carId", carNum);
	    			fields.put("password", pw);
	    			try {
	    				DBManager.register("Driver", keys, fields);  			
	            	} catch (LoginException e){
	            		return e.toString();
	        		}
	            	return "success";
		        }
	        };
	       new Thread(registrationTask).start();
	       
	       progressIndicator.progressProperty().bind(registrationTask.progressProperty());
	       progressIndicator.setVisible(true); 
	       
	       registrationTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	           @Override
	           public void handle(WorkerStateEvent workerStateEvent) {
	        	   progressIndicator.setVisible(false);
	               String result =  registrationTask.getValue();
	               
		   			if (result.equals("user already exists")){
		   				statusLabel.setVisible(false);
						idLabel.setText("User with such id already exists");
						idField.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
						idLabel.setVisible(true);
						return;
		   			}
		   			if(result.equals("success")){
		   				try {
		   					EmailNotification.SuccessfulRegistration(email);
		   					Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	   	    				window.setTitle("Main Screen");
	   	    				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));     
	   	    				Parent root = (Parent)fxmlLoader.load();          
	   	    				MainScreenController controller = fxmlLoader.<MainScreenController>getController();
	   	    				controller.setUserId(id);
	   	    				window.setScene(new Scene(root,750,650));		
	   	    				window.show();   		
		   				}
		   				catch (Exception e){
		   					
		   				}
		   				
		   			}
	           }
	       });
		}
	}
	
}
