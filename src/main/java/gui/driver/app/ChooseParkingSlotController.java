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
import java.util.Scanner;
import javafx.scene.layout.*;



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
		nameToIndexMap.put("taub1", 0);
		//index++;
		//engine.executeScript("addMarker(32.777110, 35.021328);");
		auxs.add(new Aux("taub2",32.778147, 35.021843, 10, 10, 5.0));
		nameToIndexMap.put("taub2", 1);
		//index++;
		//engine.executeScript("addMarker(32.778147, 35.021843);");
		auxs.add(new Aux("pool1",32.778932, 35.019461, 5, 300, 4.5));
		nameToIndexMap.put("pool1", 2);
		//index++;
		//engine.executeScript("addMarker(32.778932, 35.019461);");
		auxs.add(new Aux("pool2",32.778842, 35.018742, 3, 380, 4.2));
		nameToIndexMap.put("pool2", 3);
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
	
	private Map<String, Integer> nameToIndexMap;
	private int lastIndex = -1;
	
	private TableColumn<Aux, String> idColumn;
	private TableColumn<Aux, Double> priceColumn;
	private TableColumn<Aux, Double> distanceColumn;
	private TableColumn<Aux, Double> ratingColumn;
	
	@FXML
	private Button continueButton;
	@FXML
	private VBox lowerVBox;
	@FXML
	private Label chooseParkingSlotLabel;
	
	
	@FXML
    protected void initialize(){
		
		chooseParkingSlotLabel.setVisible(false);
		slotsTable.setVisible(false);
		myWebView.setVisible(false);
		addMarkersButton.setVisible(false);
		
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
		slotsTable.setItems(getAuxs());
		slotsTable.getColumns().setAll(idColumn, priceColumn, distanceColumn, ratingColumn);
		engine.executeScript("addMarker(32.777110, 35.021328, 'taub1');");
		engine.executeScript("addMarker(32.778147, 35.021843, 'taub2');");
		engine.executeScript("addMarker(32.778932, 35.019461, 'pool1');");
		engine.executeScript("addMarker(32.778842, 35.018742, 'pool2');");
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setHeight(900);
		chooseParkingSlotLabel.setVisible(true);
		slotsTable.setVisible(true);
		myWebView.setVisible(true);
		addMarkersButton.setVisible(true);

		
	}
	
	@FXML
	public void addMarkers(ActionEvent event) throws Exception{
		slotsTable.setItems(getAuxs());
		slotsTable.getColumns().setAll(idColumn, priceColumn, distanceColumn, ratingColumn);
		/*
		engine.executeScript("addMarker(32.777110, 35.021328, 'taub1');");
		engine.executeScript("addMarker(32.778147, 35.021843, 'taub2');");
		engine.executeScript("addMarker(32.778932, 35.019461, 'pool1');");
		engine.executeScript("addMarker(32.778842, 35.018742, 'pool2');");*/
	}
	@FXML
	public void changeMarker(ActionEvent event) throws Exception{
		engine.executeScript("setUnselected();");
		
	}
	
}
