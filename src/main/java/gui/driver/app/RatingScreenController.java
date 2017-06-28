package gui.driver.app;

import logic.*;

import java.util.*;

import org.parse4j.ParseGeoPoint;
import java.util.Calendar;
import data.management.DBManager;
import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
import data.members.ParkingSlot;
import data.members.StickersColor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.*;
import java.net.*;
import java.awt.geom.Point2D;
import java.io.*;

import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.concurrent.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.jfoenix.controls.*;

import Exceptions.LoginException;

import java.time.*;


public class RatingScreenController {
	
	/**
	 * @author dshames
	 *  This class contains the controller of the rating screen
	 */
	
	@FXML
	private Label headerLabel;
	@FXML
	private ImageView firstStarImage;
	@FXML
	private ImageView secondStarImage;
	@FXML
	private ImageView thirdStarImage;
	@FXML
	private ImageView fourthStarImage;
	@FXML
	private ImageView fifthStarImage;
	@FXML
	private Label ratingLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Button notNowButton;
	@FXML
	private Button submitButton;
	
	private ArrayList<ImageView> images;
	private Image fullStarImage;
	private Image emptyStarImage;
	private int rating;
	private String userId;
	private PresentOrder parkingOrder;
	
	@FXML
    protected void initialize(){
		submitButton.setDisable(true);
		ratingLabel.setVisible(false);
		statusLabel.setVisible(false);
		progressIndicator.setVisible(false);
		rating = -2;
		setImages();
		getUserIdAndOrder();
	}
	private void getUserIdAndOrder(){
		statusLabel.setText("Loading...");
		statusLabel.setVisible(true);
		Task<Void> getUserDetailsTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {	
            	while(userId == null){
            		
            	}
            	while(parkingOrder == null){
            		
            	}
            	return null;
	        }
        };
       new Thread(getUserDetailsTask).start();
       
       progressIndicator.progressProperty().bind(getUserDetailsTask.progressProperty());
       progressIndicator.setVisible(true); 
       
       getUserDetailsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent workerStateEvent) {
        	   headerLabel.setText("Please rate the quality of your last parking experience at " + parkingOrder.getParkingSlotId() + ".");
        	   submitButton.setDisable(false);
        	   progressIndicator.setVisible(false);
        	   statusLabel.setVisible(false);
           }
       });
	}
	@FXML
	public void notNowButtonClicked(ActionEvent event) throws Exception{
		
			rating = -1;
			submitButtonClicked(event);
	}
	
	@FXML
	public void submitButtonClicked(ActionEvent event) throws Exception{
		System.out.println(rating);
		if (rating == -2){
			statusLabel.setText("Click on the stars in order to choose the rating");
			statusLabel.setVisible(true);
			return;
		}
		
		if (rating == -1){
			statusLabel.setText("Thanks anyway! redirecting to main screen...");
		} else {
			statusLabel.setText("Thanks! sending your rating... you will be redirected to main screen");
		}
		
		statusLabel.setVisible(true);
		Task<Void> rateTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {	
            	DatabaseManager d = DatabaseManagerImpl.getInstance();
               	d.initialize();
               	OrderReviewHandeling.giveReviewToParkingSlot(parkingOrder, rating, d);
               	Thread.sleep(1500);
            	return null;
	        }
        };
       new Thread(rateTask).start();
       
       progressIndicator.progressProperty().bind(rateTask.progressProperty());
       progressIndicator.setVisible(true); 
       
       rateTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent workerStateEvent) {
        	   try{
	  				Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	  				window.setTitle("Main Screen");
	  				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));     
	  				Parent root = (Parent)fxmlLoader.load();          
	  				MainScreenController controller = fxmlLoader.<MainScreenController>getController();
	  				controller.setUserId(userId);
	  				window.setScene(new Scene(root, ScreenSizesConstants.MainScreenWidth, ScreenSizesConstants.MainScreenHeight));		
	  				window.show();
        	   } catch (Exception e){
        		   
        	   }
           }
       });
		
	
		// TODO: update ratings at db
	}
	
	private void setImages(){
		
		images = new ArrayList<ImageView>();
		images.add(0, firstStarImage);
		images.add(1, secondStarImage);
		images.add(2, thirdStarImage);
		images.add(3, fourthStarImage);
		images.add(4, fifthStarImage);
		
		fullStarImage = new Image(getClass().getResource("full_star.png").toExternalForm());
		emptyStarImage = new Image(getClass().getResource("empty_star.png").toExternalForm());
		
		for (ImageView image: images){
			image.setImage(emptyStarImage);
		}
	}
	public void firstStarClicked(){
		StarClickedAux(0);
	}
	public void secondStarClicked(){
		StarClickedAux(1);
	}
	public void thirdStarClicked(){
		StarClickedAux(2);
	}
	public void fourthStarClicked(){
		StarClickedAux(3);
	}
	public void fifthStarClicked(){
		StarClickedAux(4);
	}
	public void StarClickedAux(int index){
		this.rating = index+1;
		for (int i = 0; i < images.size(); i++){
			if (i <= index){
				images.get(i).setImage(fullStarImage);
				continue;
			}
			images.get(i).setImage(emptyStarImage);
		}
	
		if (index == 0){
			ratingLabel.setText("Very Bad");
		}
		else if (index == 1){
			ratingLabel.setText("Bad");
		}
		else if (index == 2){
			ratingLabel.setText("Okay");
		}
		else if (index == 3){
			ratingLabel.setText("Good");
		} 
		else{ // index = 4
			ratingLabel.setText("Very Good");
		} 
		ratingLabel.setVisible(true);
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	public void setParkingOrder(PresentOrder parkingOrder){
		this.parkingOrder = parkingOrder;
	}
	
}
