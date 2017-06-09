package gui.driver.app;
import java.util.HashMap;
import java.util.Map;


import data.management.DBManager;
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



public class ChooseParkingSlotController {
	public class Aux {
		private double lat;
		private double lon;
		private String name;
		private double price;
		private double distance;
		private double rating;
		
		public Aux(String name, double lat, double lon, double price, double distance, double rating){
			this.lat = lat;
			this.lon = lon;
			this.name = name;
			this.price = price;
			this.distance = distance;
			this.rating = rating;
		}
		public double getLat(){
			return this.lat;
		}
		public double getLon(){
			return this.lon;
		}
		public String getName(){
			return this.name;
		}
		public double getPrice(){
			return this.price;
		}
		public double getDistance(){
			return this.distance;
		}
		public double getRating(){
			return this.rating;
		}
		public void setLat(double lat){
			this.lat = lat;
		}
		public void setLon(double lon){
			this.lon = lon;
		}
		public void setName(String name){
			this.name = name;
		}
		public void setPrice(double price){
			this.price = price;
		}
		public void setDistance(double distance){
			this.distance = distance;
		}
		public void setRating(double rating){
			this.rating = rating;
		}
	}

	public ObservableList<Aux> getAuxs(){
		ObservableList<Aux> auxs = FXCollections.observableArrayList();
		auxs.add(new Aux("taub1",32.777110, 35.021328, 10, 10, 5.0));
		idToLocation.put("taub1", 0);
		//engine.executeScript("addMarker(32.777110, 35.021328);");
		auxs.add(new Aux("taub2",32.778147, 35.021843, 10, 10, 5.0));
		idToLocation.put("taub2", 1);
		//engine.executeScript("addMarker(32.778147, 35.021843);");
		auxs.add(new Aux("pool1",32.778932, 35.019461, 5, 300, 4.5));
		idToLocation.put("pool1", 2);
		//engine.executeScript("addMarker(32.778932, 35.019461);");
		auxs.add(new Aux("pool2",32.778842, 35.018742, 3, 380, 4.2));
		idToLocation.put("pool2", 3);
		//engine.executeScript("addMarker(32.778842, 35.018742);");
		return auxs;
	}
	
	@FXML
	private WebView myWebView;
	@FXML
	private Button addMarkersButton;
	@FXML
	private Button changeMarkersButton;
	private WebEngine engine;
	@FXML
	private TableView<Aux> slotsTable;
	
	private Map<String, Integer> idToLocation;
	
	
	
	@FXML
    protected void initialize(){
		
		engine = myWebView.getEngine();
		URL url = getClass().getResource("map.html");
		engine.load(url.toExternalForm());
		
		TableColumn<Aux, String> idColumn = new TableColumn<>("id");
		idColumn.setPrefWidth(100);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Aux, Double> priceColumn = new TableColumn<>("price");
		priceColumn.setPrefWidth(100);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		TableColumn<Aux, Double> distanceColumn = new TableColumn<>("distance");
		distanceColumn.setPrefWidth(100);
		distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
		
		TableColumn<Aux, Double> ratingColumn = new TableColumn<>("rating");
		ratingColumn.setPrefWidth(100);
		ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
		
		idToLocation = new HashMap<String, Integer>();
		
		slotsTable.setItems(getAuxs());
		slotsTable.getColumns().setAll(idColumn, priceColumn, distanceColumn, ratingColumn);
		
        //Add change listener
        slotsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            if (slotsTable.getSelectionModel().getSelectedItem() != null) {
            	//engine.executeScript(script)
                //lblTool.setText(newValue.getTool());
            	engine.executeScript("setSelected("+idToLocation.get(newValue.getName())+")");
            	
            }
        });
    }
	
	@FXML
	public void addMarkers(ActionEvent event) throws Exception{
		engine.executeScript("addMarker(32.777110, 35.021328);");
		engine.executeScript("addMarker(32.778147, 35.021843);");
		engine.executeScript("addMarker(32.778932, 35.019461);");
		engine.executeScript("addMarker(32.778842, 35.018742);");	}
	@FXML
	public void changeMarker(ActionEvent event) throws Exception{
		engine.executeScript("setSelected(0);");
		
	}
	
}
