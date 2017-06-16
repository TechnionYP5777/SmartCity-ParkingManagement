package gui.driver.app;

import logic.*;

import java.util.*;

import org.parse4j.ParseGeoPoint;

import data.management.DBManager;
import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
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
			nameToIndexMap.put(slot.getName(), index);
			index++;
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
	
	private Map<String, Integer> nameToIndexMap;
	private int lastIndex = -1;
	
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
			int row = nameToIndexMap.get(wEvent.getData());
			slotsTable.getSelectionModel().select(row);
			slotsTable.getSelectionModel().focus(row);
			if (lastIndex != -1){
				engine.executeScript("setUnSelected(" + String.valueOf(lastIndex)  + ")");	
			}
			engine.executeScript("setSelected(" + row  + ")");
		});
		myWebView.getEngine().executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
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
		
		nameToIndexMap = new HashMap<String, Integer>();

		
		//todo: if no slots
		//slotsTable.getSelectionModel().selectFirst();
		
        //Add change listener
       slotsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
    	   
            if (slotsTable.getSelectionModel().getSelectedItem() != null) {
            	if(oldValue != null){  
            		engine.executeScript("setUnSelected(" + String.valueOf(nameToIndexMap.get(oldValue.getName()))  + ")");	
            	}
            	lastIndex = nameToIndexMap.get(newValue.getName());
            	engine.executeScript("setSelected(" + String.valueOf(lastIndex)  + ")");	
            	//engine.executeScript("refresh()");
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
		        	return request.getAllAvailableParkingSlot(new BillingClass() {
						@Override
						public double calculateCost(StickersColor rank, double distance) {
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
					//engine.executeScript("map.setCenter(" + location + ")");
	       			engine.executeScript("addDestinationMarker(" + location + ");");
	           }
	       });

		
	}
	
}
