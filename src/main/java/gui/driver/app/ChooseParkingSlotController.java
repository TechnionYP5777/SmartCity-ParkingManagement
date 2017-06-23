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
import javafx.concurrent.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.jfoenix.controls.*;

import Exceptions.LoginException;

import java.time.*;

public class ChooseParkingSlotController {

	public ObservableList<PresentParkingSlot> getSlots(List<PresentParkingSlot> slots){
		
		ObservableList<PresentParkingSlot> returnSlots = FXCollections.observableArrayList();
		int index = 0;
		for (PresentParkingSlot slot : slots){
			returnSlots.add(slot);
		}
		return returnSlots;
	}
	
	@FXML
	private WebView myWebView;
	@FXML
	private Button changeMarkersButton;
	private WebEngine engine;
	@FXML
	private TableView<PresentParkingSlot> slotsTable;
	@FXML
	private TableColumn<PresentParkingSlot, String> idColumn;
	@FXML
	private TableColumn<PresentParkingSlot, Double> priceColumn;
	@FXML
	private TableColumn<PresentParkingSlot, Double> distanceColumn;
	@FXML
	private TableColumn<PresentParkingSlot, Double> ratingColumn;
	
	@FXML
	private Button continueButton;
	@FXML
	private VBox lowerVBox;
	@FXML
	private Label chooseParkingSlotLabel;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private RadioButton studentHouseRadioButton;
	@FXML
	private RadioButton ulmanRadioButton;
	@FXML
	private RadioButton taubRadioButton;
	@FXML
	private RadioButton sportsCenterRadioButton;
	@FXML
	private JFXDatePicker datePicker;
	@FXML
	private JFXTimePicker arrivalTimePicker;
	@FXML
	private JFXTimePicker departureTimePicker;
	
	private ParkingSlotRequest request;
	private String userId;
	
	public void setUserId(String id){
		this.userId = id;
	}
	
	@FXML
    protected void initialize(){
		progressIndicator.setVisible(false);
		studentHouseRadioButton.setSelected(true);
		
		engine = myWebView.getEngine();
		URL url = getClass().getResource("map.html");
		engine.load(url.toExternalForm());
		

		myWebView.getEngine().setOnAlert((WebEvent<String> wEvent) -> {
			String clickedName = wEvent.getData();
			int index = 0;
			for(PresentParkingSlot parkingSlot :slotsTable.getItems()){
				if (parkingSlot.getName().equals(clickedName)){
					slotsTable.getSelectionModel().select(index);
					slotsTable.getSelectionModel().focus(index);
					break;
				}
				index++;	
			}
		});
		
		idColumn = new TableColumn<>("id");
		idColumn.setPrefWidth(130);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		priceColumn = new TableColumn<>("price");
		priceColumn.setPrefWidth(90);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		distanceColumn = new TableColumn<>("distance");
		distanceColumn.setPrefWidth(90);
		distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
		
		ratingColumn = new TableColumn<>("rating");
		ratingColumn.setPrefWidth(90);
		ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
		
		
       slotsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
    	   
            if (slotsTable.getSelectionModel().getSelectedItem() != null) {
            	if(oldValue != null){ 
            		engine.executeScript("setUnSelected('" + oldValue.getName() + "');");	
            	}
            	engine.executeScript("setSelected('" + newValue.getName()  + "');");	
            	engine.executeScript("refresh()");
            }
        });
       
       request = null;
    }
	
	private int roundMinutes(int minutes, boolean roundUp){
		if (0 < minutes && minutes < 15){
			if(roundUp)
				return 15;
			return 0;
		}
		if (15 < minutes && minutes < 30){
			if(roundUp)
				return 30;
			return 15;
		}
		if (30 < minutes && minutes < 45){
			if(roundUp)
				return 45;
			return 30;
		}
		if (45 < minutes && minutes < 60){
			if(roundUp)
				return 60;
			return 45;
		}
		return minutes;
	}
	
	
	@FXML
	@SuppressWarnings("deprecation")
	public void continueButtonClicked(ActionEvent event) throws Exception{
		
	       Task<List<PresentParkingSlot>> slotsTask = new Task<List<PresentParkingSlot>>() {
	            @Override
	            protected List<PresentParkingSlot> call() throws Exception {
	            	
		        	ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
		        	DatabaseManager d = DatabaseManagerImpl.getInstance();
		        	d.initialize();
		        	
		        	LocalDate date = datePicker.getValue();
		        	LocalTime arrivalTime = arrivalTimePicker.getValue();
		        	LocalTime departureTime = departureTimePicker.getValue();

		        	// TODO: clean map!
		        	
		        	if (date == null || arrivalTime == null || departureTime == null){
		        		System.out.println("You have to fill all the date and time fields");
		        		return null;
		        	}
		        	Calendar arrivalDateTime = Calendar.getInstance();
		        	arrivalDateTime.set(Calendar.YEAR, date.getYear());
		        	arrivalDateTime.set(Calendar.MONTH, date.getMonthValue());
		        	arrivalDateTime.set(Calendar.DAY_OF_MONTH,date.getDayOfMonth());
		        	arrivalDateTime.set(Calendar.HOUR, arrivalTime.getHour());
		        	arrivalDateTime.set(Calendar.MINUTE, roundMinutes(arrivalTime.getMinute(), false));
		        	
		        	/*
		        	if (new Date(date.getYear(),date.getMonthValue(),date.getDayOfMonth(),
		        			arrivalTime.getHour(), arrivalTime.getMinute()).before(new Date().setMinutes(-10))){
		        		// TODO: notify user.
		        	}*/
		        	if (!arrivalTime.isBefore(departureTime)){
		        		System.out.println("Your departure time must be after your arrival time");
		        		return null;
		        	}
		        	
		        	int departureMinutes = roundMinutes(departureTime.getMinute(), true);
		        	int quartersCounter = 0;
		        	if (departureMinutes == 60){
		        		quartersCounter = (departureTime.getHour() + 1)*4;
		        	} else {
		        		quartersCounter = departureTime.getHour()*4 + departureMinutes/15;
		        	}
		        	int diff = quartersCounter - (arrivalDateTime.get(Calendar.HOUR)*4 + arrivalDateTime.get(Calendar.MINUTE)/15);
		        	request = new ParkingSlotRequest(point, arrivalDateTime.getTime(), diff, d);
		        	return request.getAllAvailableParkingSlot(new Billing() {
						@Override
						public double calculateCost(StickersColor rank, double distance) {
							// TODO Auto-generated method stub
							return 0;
						}
						@Override
						public double calculateCostBySlot(ParkingSlot slot, ParseGeoPoint point) {
							// TODO Auto-generated method stub
							return 0;
						}
					});

		        }
	        };
	       new Thread(slotsTask).start();
	       
	       progressIndicator.progressProperty().bind(slotsTask.progressProperty());
	       progressIndicator.setVisible(true); 
	       slotsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	           @Override
	           public void handle(WorkerStateEvent workerStateEvent) {
	               	List<PresentParkingSlot> result = slotsTask.getValue();   //here you get the return value of your service
	               	if (result == null){
	               		progressIndicator.setVisible(false);
	               		return;
	               	}
	               	slotsTable.setItems(getSlots(result));
	       			slotsTable.getColumns().setAll(idColumn, priceColumn, distanceColumn, ratingColumn);
	       			for (PresentParkingSlot slot : result){
	       				engine.executeScript("addMarker(" + slot.getLat() + ", " + slot.getLon() + ", '" + slot.getName() + "');");
	       			}
	       			progressIndicator.setVisible(false);
	       			String location = "";
	       			if(studentHouseRadioButton.isSelected()){
	       				location = "32.776423, 35.022825";
	       			}
					if(ulmanRadioButton.isSelected()){
						location = "32.777100, 35.023770";
			       	}
					if(taubRadioButton.isSelected()){
						location = "32.777668, 35.021486";
		   			}
					if(sportsCenterRadioButton.isSelected()){
		   				location = "32.779119, 35.019112";
		   			}
	       			//engine.executeScript("addDestinationMarker(" + location + ");");
	       			
	       			if (result.size() != 0){
	       				slotsTable.getSelectionModel().selectFirst();
	       			}
	       			//TODO: if there are no slots, notify the user
	           }
	       });

		
	}
	
	@FXML
	public void orderButtonClicked(ActionEvent event) throws Exception{
		
		if (request == null){
			return;
		}
		
		if (slotsTable.getSelectionModel().getSelectedItem() == null){
			return;
		} else {
			
			String parkingSlotId = slotsTable.getSelectionModel().getSelectedItem().getName();
			
			Task<Boolean> orderTask = new Task<Boolean>() {
	            @Override
	            protected Boolean call() throws Exception {	
	            	return request.orderParkingSlot(userId, parkingSlotId);
		        }
	        };
	       new Thread(orderTask).start();
			
	       progressIndicator.progressProperty().bind(orderTask.progressProperty());
	       progressIndicator.setVisible(true); 
	       
	       orderTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	           @Override
	           public void handle(WorkerStateEvent workerStateEvent) {
	        	   progressIndicator.setVisible(false);
	               boolean result =  orderTask.getValue();
	               if(result){
	            	   System.out.println("SUCCESS");
	               } else {
	            	   System.out.println("FAILURE");
	               }
	               try{
		               Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		               window.setTitle("Main Screen");
		               FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));     
		               Parent root = (Parent)fxmlLoader.load();          
		               MainScreenController controller = fxmlLoader.<MainScreenController>getController();
		               controller.setUserId(userId);
		               window.setScene(new Scene(root,750,650));		
		               window.show();
	               } catch(Exception e){
	            	   
	               }
	   			}

	       });
			
		}

		
	}
	
}
