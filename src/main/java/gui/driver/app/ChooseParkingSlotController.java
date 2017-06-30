	package gui.driver.app;

import logic.*;
import java.util.*;
import org.parse4j.ParseGeoPoint;
import data.management.DBManager;
import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.web.*;
import java.net.*;
import javafx.scene.layout.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import com.jfoenix.controls.*;
import java.time.*;
import logic.Graph;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import java.util.logging.Logger;

public class ChooseParkingSlotController {

	/**
	 * @author dshames
	 *  This class contains the controller of the choose parking slot screen
	 */
		
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
	@FXML
	private Button orderButton;
	@FXML
	private Button backButton;
	@FXML
	private Button showRatingChartButton;
	@FXML
	private Button showDistanceChartButton;
	@FXML
	private Label statusLabel;
	
	private ParkingSlotRequest request;
	private String userId;

	private URL url;
	
	
	@FXML
    protected void initialize(){
		progressIndicator.setVisible(false);
		studentHouseRadioButton.setSelected(true);
		statusLabel.setVisible(false);
		setWebViewAndTable();
       request = null;
    }
	
	
	@FXML
	public void continueButtonClicked(ActionEvent event) throws Exception{
			
		ParseGeoPoint point = getSelectedDestination();
		
    	LocalDate date = datePicker.getValue();
    	LocalTime arrivalTime = arrivalTimePicker.getValue();
    	LocalTime departureTime = departureTimePicker.getValue();
    	
    	if (date == null || arrivalTime == null || departureTime == null){
    		statusLabel.setText("You have to fill all the date and time fields");
    		statusLabel.setVisible(true);
    		return;
    	}
    			        	
    	Calendar arrivalDateTime = Calendar.getInstance();
    	arrivalDateTime.set(Calendar.YEAR, date.getYear());
    	arrivalDateTime.set(Calendar.MONTH, date.getMonthValue()-1);
    	arrivalDateTime.set(Calendar.DAY_OF_MONTH,date.getDayOfMonth());
    	arrivalDateTime.set(Calendar.HOUR_OF_DAY, arrivalTime.getHour());
    	arrivalDateTime.set(Calendar.MINUTE, roundMinutes(arrivalTime.getMinute(), false));
    	arrivalDateTime.set(Calendar.SECOND, 0);

    	if (!arrivalTime.isBefore(departureTime)){
    		statusLabel.setText("Departure time must be after your arrival time");
    		statusLabel.setVisible(true);
    		return;
    	}
    	orderButton.setDisable(true);
    	showRatingChartButton.setDisable(true);
    	showDistanceChartButton.setDisable(true);
    	continueButton.setDisable(true);
    	
    	statusLabel.setVisible(false);
    	engine.executeScript("reloadAux();");
    	slotsTable.getItems().clear();
	
	       Task<List<PresentParkingSlot>> slotsTask = new Task<List<PresentParkingSlot>>() {
	            @Override
	            protected List<PresentParkingSlot> call() throws Exception {
	            	
	            	
	            	DatabaseManager d = DatabaseManagerImpl.getInstance();
	            	
		        	int departureMinutes = roundMinutes(departureTime.getMinute(), true);
		        	int quartersCounter = 0;
		        	if (departureMinutes == 60){
		        		quartersCounter = (departureTime.getHour() + 1)*4;
		        	} else {
		        		quartersCounter = departureTime.getHour()*4 + departureMinutes/15;
		        	}
		        	int diff = quartersCounter - (arrivalDateTime.get(Calendar.HOUR_OF_DAY)*4 + arrivalDateTime.get(Calendar.MINUTE)/15);
		        	System.out.println(arrivalDateTime.getTime().toString());
		        	request = new ParkingSlotRequest(point, arrivalDateTime.getTime(), diff, d);
		        	return request.getAllAvailableParkingSlot(new BasicBilling());

		        }
	        };
	        
	       new Thread(slotsTask).start();
	       
	       progressIndicator.progressProperty().bind(slotsTask.progressProperty());
	       progressIndicator.setVisible(true); 
	       slotsTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	           @Override
	           public void handle(WorkerStateEvent workerStateEvent) {
	               	List<PresentParkingSlot> result = slotsTask.getValue();   //here you get the return value of your service
	               	statusLabel.setVisible(false);
	               	slotsTable.setItems(getSlots(result));
	       			slotsTable.getColumns().setAll(idColumn, priceColumn, distanceColumn, ratingColumn);
	       			for (PresentParkingSlot slot : result){
	       				double distance = slot.getDistance();
	       				slot.setDistance(Math.round(distance * 100.0) / 100.0);
	       				double price = slot.getPrice();
	       				slot.setPrice(Math.round(price * 100.0) / 100.0);
	       				double rating = slot.getRating();
	       				slot.setRating(Math.round(rating * 100.0) / 100.0);
	       				engine.executeScript("addMarker(" + slot.getLat() + ", " + slot.getLon() + ", '" + slot.getName() + "');");
	       			}
	       			progressIndicator.setVisible(false);
	       			engine.executeScript("firstRefresh();");
	       			
	       		   	orderButton.setDisable(false);
	       		   	showRatingChartButton.setDisable(false);
	       		   	showDistanceChartButton.setDisable(false);
	       	    	continueButton.setDisable(false);
	       			
	       			if (result.size() != 0){
	       				slotsTable.getSelectionModel().selectFirst();
	       			} else {
	       				statusLabel.setText("There are no available parking slots");
	       	    		statusLabel.setVisible(true);
	       	    		return;
	       			}
	       			
	           }
	       });

	}
	
	@FXML
	public void orderButtonClicked(ActionEvent event) throws Exception{
		
		PresentParkingSlot slot = slotsTable.getSelectionModel().getSelectedItem();
		if (request == null ||slot == null){
				statusLabel.setText("Please select a parking slot from the table. If empty, search for slots.");
   	    		statusLabel.setVisible(true);
   	    		return;
		} else {
			
	    	orderButton.setDisable(true);
	    	showRatingChartButton.setDisable(true);
	    	showDistanceChartButton.setDisable(true);
	    	continueButton.setDisable(true);
	    	backButton.setDisable(true);
			
			String parkingSlotId = slot.getName();
			
			Task<Boolean> orderTask = new Task<Boolean>() {
	            @Override
	            protected Boolean call() throws Exception {	
	            	
	            	return request.orderParkingSlot(userId, parkingSlotId, slot.getPrice());
	            	
		        }
	        };
	       new Thread(orderTask).start();
			
	       progressIndicator.progressProperty().bind(orderTask.progressProperty());
	       progressIndicator.setVisible(true); 
	       

	       
	       orderTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	           @Override
	           public void handle(WorkerStateEvent workerStateEvent) {
	        	   progressIndicator.setVisible(false);
	        	   handleOrderTask(event, orderTask.getValue(), parkingSlotId, slot.getPrice());

	   			}
	       });
			
		}

	}
	
	private void handleOrderTask(ActionEvent event, boolean result, String slotId, double price){
		    	
		if(result){
			
			Task<Void> sendingEmailTask = new Task<Void>() {
	            @Override
	            protected Void call() throws Exception {
	    			Map<String, Object> key = new HashMap<String, Object>();
	    			key.put("id", userId);
	    			Map<String, Object> map = DBManager.getObjectFieldsByKey("Driver", key);
	    			String emailFromDb = (String)map.get("email");
	    			Date endDate = new Date(request.getDate().getTime() + (request.getHoursAmount() * 15 * 60 * 1000));
	            	EmailNotification.ParkingConfirmation(emailFromDb, slotId, request.getDate().toString(), endDate.toString(), price);
	            	orderButton.setDisable(false);
	            	showRatingChartButton.setDisable(false);
	            	showDistanceChartButton.setDisable(false);
	            	continueButton.setDisable(false);
	            	backButton.setDisable(false);
	        		return null;
		        }
	        };
	       new Thread(sendingEmailTask).start();
	       
	       progressIndicator.progressProperty().bind(sendingEmailTask.progressProperty());
	       progressIndicator.setVisible(true); 
	       
	       sendingEmailTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
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
	        	   } catch(Exception e){
	        		   
	        	   }
	           }
	       });
       
		} else {

 		   statusLabel.setText("The slot you choose got taken. Choose another one or search for new slots.");
 		   statusLabel.setVisible(true);
	       orderButton.setDisable(false);
	       showRatingChartButton.setDisable(false);
	       showDistanceChartButton.setDisable(false);
	       continueButton.setDisable(false);
	       backButton.setDisable(false);
		}
	}
	
	@FXML
	public void backButtonClicked(ActionEvent event) throws Exception{
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Main Screen");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));     
			Parent root = (Parent)fxmlLoader.load();          
			MainScreenController controller = fxmlLoader.<MainScreenController>getController();
			controller.setUserId(userId);
			window.setScene(new Scene(root, ScreenSizesConstants.MainScreenWidth, ScreenSizesConstants.MainScreenHeight));		
			window.show();
	}
	@FXML
	public void showRatingChartButtonClicked(ActionEvent event) throws Exception{
		Stage s = new Stage();
		s.setTitle("Price - Rating graph");
        final NumberAxis xAxis = new NumberAxis(), yAxis = new NumberAxis();
        xAxis.setLabel("Rating");
        yAxis.setLabel("Price");

        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Price - Rating");
        
        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("Price - Rating");
        
        //get data to present
        Graph g = new Graph();
        Map<Double, Double> data = g.CreatePriceRanttingData(getSelectedDestination());

        //populating the series with data
        for (Map.Entry<Double, Double> entry : data.entrySet()){
        	Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("rating: "+ entry.getKey() + " price: " + entry.getValue());
        	series.getData().add(new Data<Number, Number>(entry.getKey(), entry.getValue()));
        }
	        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().addAll(series);
       
        s.setScene(scene);
        s.show();
	}
	@FXML
	public void showDistanceChartButtonClicked(ActionEvent event) throws Exception{
		
		 Stage s = new Stage();
	        //Fill stage with content
			s.setTitle("Price - Distance graph");
	        final NumberAxis xAxis = new NumberAxis(), yAxis = new NumberAxis();
	        xAxis.setLabel("Distance");
	        yAxis.setLabel("Price");

	        //creating the chart
	        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
	                
	        lineChart.setTitle("Price - Distance");
	        
	        //defining a series
	        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
	        series.setName("Price - Distance");
	        
	        //get data to present
	        Graph g = new Graph();
	        Map<Double, Double> data = g.CreatePriceDistanceData(getSelectedDestination());

	        //populating the series with data
	        for (Map.Entry<Double, Double> entry : data.entrySet()){
	        	Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("distance: "+ entry.getKey() + " price: " + entry.getValue());
	        	series.getData().add(new Data<Number, Number>(entry.getKey(), entry.getValue()));
	        }
		        
	        Scene scene  = new Scene(lineChart,800,600);
	        lineChart.getData().addAll(series);
	       
	        s.setScene(scene);
	        s.show();
	}
	
	private void setWebViewAndTable(){
		
		engine = myWebView.getEngine();
		url = getClass().getResource("map.html");
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
		idColumn.setPrefWidth(100);
		idColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		priceColumn = new TableColumn<>("price(NIS)");
		priceColumn.setPrefWidth(100);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		distanceColumn = new TableColumn<>("distance(m)");
		distanceColumn.setPrefWidth(100);
		distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
		
		ratingColumn = new TableColumn<>("rating");
		ratingColumn.setPrefWidth(100);
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
	
	public ObservableList<PresentParkingSlot> getSlots(List<PresentParkingSlot> slots){
		
		ObservableList<PresentParkingSlot> returnSlots = FXCollections.observableArrayList();
		for (PresentParkingSlot slot : slots){
			returnSlots.add(slot);
		}
		return returnSlots;
	}
	public void setUserId(String id){
		this.userId = id;
	}
	private ParseGeoPoint getSelectedDestination(){
		
		if(studentHouseRadioButton.isSelected()){
			return new ParseGeoPoint(32.776423, 35.022825);
		}
		if(ulmanRadioButton.isSelected()){
			return new ParseGeoPoint(32.777100, 35.023770);
       	}
		if(taubRadioButton.isSelected()){
			return new ParseGeoPoint(32.777668, 35.021486);
		}
		//sportsCenterRadioButton isSelected
		return new ParseGeoPoint(32.779119, 35.019112);
	}
}










