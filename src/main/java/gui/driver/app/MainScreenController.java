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
	
	@FXML
	private Button newOrderButton;
	@FXML
	private TableView<PresentOrder> futureOrdersTable;
	@FXML
	private Button cancelOrderButton;
	@FXML
	private TableView<PresentOrder> pastOrdersTable;
	@FXML
	private TableColumn<PresentOrder, String> parkingSlotIdColumn;
	@FXML
	private TableColumn<PresentOrder, Date> startTimeColumn;
	@FXML
	private TableColumn<PresentOrder, Date> finishTimeColumn;
	@FXML
	private TableColumn<PresentOrder, Double> priceColumn;
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
	@FXML void logOutButtonClicked(ActionEvent event) throws Exception {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Login");
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
		window.setScene(new Scene(root,400,550));
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
			window.setScene(new Scene(root,1300,900));		
			window.show();
	}
	@FXML
	public void cancelOrderButtonClicked(ActionEvent event) throws Exception {
		System.out.println("cancelOrderButtonClicked");
	}
	
	private void setColumns(){
		
		parkingSlotIdColumn = new TableColumn<>("slot id");
		parkingSlotIdColumn.setPrefWidth(180);
		parkingSlotIdColumn.setCellValueFactory(new PropertyValueFactory<>("parkingSlotId"));
		
		startTimeColumn = new TableColumn<>("start time");
		startTimeColumn.setPrefWidth(180);
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		finishTimeColumn = new TableColumn<>("finish time");
		finishTimeColumn.setPrefWidth(180);
		finishTimeColumn.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
		
		priceColumn = new TableColumn<>("price");
		priceColumn.setPrefWidth(180);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		futureOrdersTable.getColumns().setAll(parkingSlotIdColumn, startTimeColumn, finishTimeColumn, priceColumn);
		pastOrdersTable.getColumns().setAll(parkingSlotIdColumn, startTimeColumn, finishTimeColumn, priceColumn);
		
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
            	
            	Date d = new Date();
            	for (PresentOrder order: result){
            		System.out.println(order.getOrderId());
            		if (order.getFinishTime().before(d)){
            			pastOrders.add(order);
            		} else {
            			futureOrders.add(order);
            		}
            	}
            	futureOrdersTable.setItems(futureOrders);
            	pastOrdersTable.setItems(pastOrders);

                progressIndicator.setVisible(false); 
           }
       });
		
		
		
		
		
		
		
	}
}











