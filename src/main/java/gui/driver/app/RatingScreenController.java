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
	
	@FXML
    protected void initialize(){
		System.out.println("initialize");
	}
	
	@FXML
	public void notNowButtonClicked(ActionEvent event) throws Exception{
		System.out.println("notNowButtonClicked");
	}
	
	@FXML
	public void submitButtonClicked(ActionEvent event) throws Exception{
		System.out.println("submitButtonClicked");
	}
	
	
	
}
