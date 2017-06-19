package gui.driver.app;

import logic.*;

import java.util.*;

import org.parse4j.ParseGeoPoint;

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
	
	private TableColumn<PresentParkingSlot, String> idColumn;
	private TableColumn<PresentParkingSlot, Double> priceColumn;
	private TableColumn<PresentParkingSlot, Double> distanceColumn;
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

    }
	
	@FXML
	public void continueButtonClicked(ActionEvent event) throws Exception{
	       Task<List<PresentParkingSlot>> slotsTask = new Task<List<PresentParkingSlot>>() {
	            @Override
	            protected List<PresentParkingSlot> call() throws Exception {
		        	ParseGeoPoint point = new ParseGeoPoint(32.777566, 35.022484);
		        	DatabaseManager d = new DatabaseManagerImpl();
		        	d.initialize();
		        	ParkingSlotRequest request = new ParkingSlotRequest(point, new Date(), 2, d);
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
	       			engine.executeScript("addDestinationMarker(" + location + ");");
	       			
	       			if (result.size() != 0){
	       				slotsTable.getSelectionModel().selectFirst();
	       			}
	       			//TODO: if there are no slots, notify the user
	           }
	       });

		
	}
	
}
