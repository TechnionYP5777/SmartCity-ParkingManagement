package gui.driver.app;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Billing;
import logic.ParkingSlotRequest;
import logic.PresentParkingSlot;
import javafx.scene.*;
import javafx.fxml.*;
import data.management.DBManager;
import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
import data.members.ParkingSlot;
import data.members.StickersColor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.parse4j.ParseGeoPoint;

import Exceptions.LoginException;


public class LoginContorller {
	
	@FXML
	private TextField idField;
	@FXML
	private PasswordField pwField;
	@FXML
	private Hyperlink forgotPwHyperLink;
	@FXML
	private Label statusLabel;
	@FXML
	private Button loginButton;
	@FXML
	private Button createNewButton;
	@FXML
	private ProgressIndicator progressIndicator;
	
	
	@FXML
    protected void initialize(){
		progressIndicator.setVisible(false);
	}
	
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
		statusLabel.setText("ID or Password are wrong");
		
		if (id.equals("") || pw.equals("")){
			statusLabel.setVisible(true);
			return;		
		}
		
		statusLabel.setText("Loading...");
		statusLabel.setStyle(" -fx-text-fill: black; -fx-font-size: 15px; -fx-font-weight: normal");
		statusLabel.setVisible(true);
		
		Task<String> loginTask = new Task<String>() {
            @Override
            protected String call() throws Exception {	
            	try {
	            	DBManager.login("Driver", "id", id, "password", pw);
	    			
            	} catch (LoginException e){
            		return e.toString();
        		}
  	
            	return "success:" + id;
	        }
        };
       new Thread(loginTask).start();
       
       progressIndicator.progressProperty().bind(loginTask.progressProperty());
       progressIndicator.setVisible(true); 
       
       loginTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent workerStateEvent) {
        	   progressIndicator.setVisible(false);
               String result =  loginTask.getValue();
               
   			if (result.equals("user doesn't exists") || result.equals("password doesn't match")){
				statusLabel.setText("ID or Password are wrong");
				statusLabel.setStyle(" -fx-text-fill: red; -fx-font-size: 15px; -fx-font-weight: bold");
				statusLabel.setVisible(true);
				return;
   			}
   			if(result.contains("success:")){
   				try {
	   				Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	   				window.setTitle("Main Screen");
	   				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));     
	   				Parent root = (Parent)fxmlLoader.load();          
	   				MainScreenController controller = fxmlLoader.<MainScreenController>getController();
	   				controller.setUserId(result.substring(8));
	   				window.setScene(new Scene(root,750,650));		
	   				window.show();
   				}
   				catch (Exception e){
   					
   				}
   				
   			}
               
           }
       });
		
	}
	@FXML
	public void createButtonClicked(ActionEvent event) throws Exception{
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Registration");
		Parent root = FXMLLoader.load(getClass().getResource("RegistrationScreen.fxml")); 
		window.setScene(new Scene(root,400,750));		
		window.show();


	}
}
