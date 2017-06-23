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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
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
	private Button notNowButton;
	@FXML
	private Button submitButton;
	private ArrayList<ImageView> images;
	private Image fullStarImage;
	private Image emptyStarImage;
	private int rating;
	private String userId;
	private String parkingSlotId;
	
	@FXML
    protected void initialize(){
		ratingLabel.setVisible(false);
		rating = -1;
		setImages();
	}
	
	@FXML
	public void notNowButtonClicked(ActionEvent event) throws Exception{
		// TODO: disable button untill there is no userId
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Main Screen");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));     
			Parent root = (Parent)fxmlLoader.load();          
			MainScreenController controller = fxmlLoader.<MainScreenController>getController();
			controller.setUserId(userId);
			window.setScene(new Scene(root,750,650));		
			window.show();
	}
	
	@FXML
	public void submitButtonClicked(ActionEvent event) throws Exception{
		if (rating == -1){
			// TODO: notify user to choose ratings
		}
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
	public void setParkingSlotId(String parkingSlotId){
		this.parkingSlotId = parkingSlotId;
	}
	
}
