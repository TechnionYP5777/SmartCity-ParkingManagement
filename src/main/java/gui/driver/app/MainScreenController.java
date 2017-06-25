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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

public class MainScreenController {
	
	/**
	 * @author dshames
	 * @since 21/6/2017
	 *  This class contains the controller of the main screen
	 */
	
	@FXML
	private Button newOrderButton;
	@FXML
	private TableView<PresentOrder> futureOrdersTable;
	@FXML
	private TableColumn<PresentOrder, String> parkingSlotIdColumnFutureTable;
	@FXML
	private TableColumn<PresentOrder, Date> startTimeColumnFutureTable;
	@FXML
	private TableColumn<PresentOrder, Date> finishTimeColumnFutureTable;
	@FXML
	private TableColumn<PresentOrder, Double> priceColumnFutureTable;
	@FXML
	private Button cancelOrderButton;
	@FXML
	private TableView<PresentOrder> pastOrdersTable;
	@FXML
	private TableColumn<PresentOrder, String> parkingSlotIdColumnPastTable;
	@FXML
	private TableColumn<PresentOrder, Date> startTimeColumnPastTable;
	@FXML
	private TableColumn<PresentOrder, Date> finishTimeColumnPastTable;
	@FXML
	private TableColumn<PresentOrder, Double> priceColumnPastTable;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private Button logOutButton;
	
	private String userId;

	@FXML
    protected void initialize(){
		
		progressIndicator.setVisible(false);
		newOrderButton.setDisable(true);
		cancelOrderButton.setDisable(true);
		
		setColumns();
		getUserIdAndSetOrders();
	}
	
	@FXML 
	void logOutButtonClicked(ActionEvent event) throws Exception {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Login");
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
		window.setScene(new Scene(root, ScreenSizesConstants.LoginScreenWidth, ScreenSizesConstants.LoginScreenHeight));
		DBManager.initialize();
		window.show();
	}
	@FXML
	public void newOrderButtonClicked(ActionEvent event) throws Exception {
		
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("New Order");
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseParkingSlotScreen.fxml"));     
			Parent root = (Parent)fxmlLoader.load();          
			ChooseParkingSlotController controller = fxmlLoader.<ChooseParkingSlotController>getController();
			controller.setUserId(userId);
			window.setScene(new Scene(root, ScreenSizesConstants.MapScreenWidth, ScreenSizesConstants.MapScreenHeight));		
			window.show();
	}
	@FXML
	public void cancelOrderButtonClicked(ActionEvent event) throws Exception {
		PresentOrder order = futureOrdersTable.getSelectionModel().getSelectedItem();
		if (order == null){
			System.out.println("you didnt select...");
			return;
		}
		if (order.getStartTime().getTime() - new Date().getTime() <	4 * 60 * 60 * 1000){
			System.out.println("Sorry, you can't cancel a booking less than 4 hours before it starts");
			return;
		}
	}
	
	private void setColumns(){
		
		parkingSlotIdColumnFutureTable = new TableColumn<>("slot id");
		parkingSlotIdColumnFutureTable.setPrefWidth(180);
		parkingSlotIdColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("parkingSlotId"));
		
		startTimeColumnFutureTable = new TableColumn<>("start time");
		startTimeColumnFutureTable.setPrefWidth(180);
		startTimeColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		finishTimeColumnFutureTable = new TableColumn<>("finish time");
		finishTimeColumnFutureTable.setPrefWidth(180);
		finishTimeColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
		
		priceColumnFutureTable = new TableColumn<>("price");
		priceColumnFutureTable.setPrefWidth(180);
		priceColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		parkingSlotIdColumnPastTable = new TableColumn<>("slot id");
		parkingSlotIdColumnPastTable.setPrefWidth(180);
		parkingSlotIdColumnPastTable.setCellValueFactory(new PropertyValueFactory<>("parkingSlotId"));
		
		startTimeColumnPastTable = new TableColumn<>("start time");
		startTimeColumnPastTable.setPrefWidth(180);
		startTimeColumnPastTable.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		finishTimeColumnPastTable = new TableColumn<>("finish time");
		finishTimeColumnPastTable.setPrefWidth(180);
		finishTimeColumnPastTable.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
		
		priceColumnPastTable = new TableColumn<>("price");
		priceColumnPastTable.setPrefWidth(180);
		priceColumnPastTable.setCellValueFactory(new PropertyValueFactory<>("price"));
			
		futureOrdersTable.getColumns().setAll(parkingSlotIdColumnFutureTable, startTimeColumnFutureTable, finishTimeColumnFutureTable, priceColumnFutureTable);
		pastOrdersTable.getColumns().setAll(parkingSlotIdColumnPastTable, startTimeColumnPastTable, finishTimeColumnPastTable, priceColumnPastTable);
		
	}
	
	private void getUserIdAndSetOrders(){
		
		Task<Void> getUserIdTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {	
            	while(userId == null){
            		
            	}
            	return null;
	        }
        };
       new Thread(getUserIdTask).start();
       
       progressIndicator.progressProperty().bind(getUserIdTask.progressProperty());
       progressIndicator.setVisible(true); 
       
       getUserIdTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent workerStateEvent) {
        	   progressIndicator.setVisible(false);
        	   newOrderButton.setDisable(false);
        	   setOrders();
           }
       });
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public void setOrders(){
		Task<List<PresentOrder>> ordersTask = new Task<List<PresentOrder>>() {
            @Override
            protected List<PresentOrder> call() throws Exception {
            	
	        	DatabaseManager d = DatabaseManagerImpl.getInstance();
	        	d.initialize();
	        	return UserOrderManaging.getUserOrders(userId, d);
	        }
        };
       new Thread(ordersTask).start();
       
       progressIndicator.progressProperty().bind(ordersTask.progressProperty());
       progressIndicator.setVisible(true); 
       
       ordersTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent workerStateEvent) {
               	List<PresentOrder> result = ordersTask.getValue(); 
               	
               	if (result == null){
               		progressIndicator.setVisible(false);
               		return;
               	}
               	
               	ObservableList<PresentOrder> futureOrders = FXCollections.observableArrayList();
            	ObservableList<PresentOrder> pastOrders = FXCollections.observableArrayList();
            	
            	for (PresentOrder order: result){
            		
            		System.out.println(order.getFinishTime().toString());
            		
            		if (order.getStartTime().after(new Date())){
            			futureOrders.add(order);
            		} else {
            			pastOrders.add(order);
            		}
            	}
            	futureOrdersTable.setItems(futureOrders);
            	pastOrdersTable.setItems(pastOrders);
            	cancelOrderButton.setDisable(false);
                progressIndicator.setVisible(false); 
           }
       });
			
	}
}
