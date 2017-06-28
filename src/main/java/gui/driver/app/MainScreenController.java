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
	@FXML
	private Label statusLabel;
	
	private String userId;

	@FXML
    protected void initialize(){
		
		progressIndicator.setVisible(false);
		newOrderButton.setDisable(true);
		cancelOrderButton.setDisable(true);
		statusLabel.setVisible(false);
		
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
			statusLabel.setText("No selection");
			statusLabel.setVisible(true);
			return;
		}
		if (order.getStartTime().getTime() - new Date().getTime() <=	4 * 60 * 60 * 1000){
			statusLabel.setText("You can't cancel a booking less than 4 hours before it starts");
			statusLabel.setVisible(true);
			return;
		}
		cancelOrderButton.setDisable(true);
		Task<Void> cancelOrderTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {	
            	DatabaseManager d = DatabaseManagerImpl.getInstance();
            	UserOrderManaging.cancleOrder(order, d);
            	return null;
	        }
        };
       new Thread(cancelOrderTask).start();
       
       progressIndicator.progressProperty().bind(cancelOrderTask.progressProperty());
       progressIndicator.setVisible(true); 
       
       cancelOrderTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent workerStateEvent) {
        	   progressIndicator.setVisible(false);	   
        	   
        	   int index = 0;
        	   ObservableList<PresentOrder> updatedFutureOrders = futureOrdersTable.getItems();
        	   for (PresentOrder p: updatedFutureOrders){
        		   if (p.getOrderId().equals(order.getOrderId())){
        			   updatedFutureOrders.remove(index);
        			   break;
        		   }
        		   index++;
        	   }
        	   cancelOrderButton.setDisable(false);
           }
       });
       
	}
	
	private void setColumns(){
		
		parkingSlotIdColumnFutureTable = new TableColumn<>("slot id");
		parkingSlotIdColumnFutureTable.setPrefWidth(195);
		parkingSlotIdColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("parkingSlotId"));
		
		startTimeColumnFutureTable = new TableColumn<>("start time");
		startTimeColumnFutureTable.setPrefWidth(195);
		startTimeColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		finishTimeColumnFutureTable = new TableColumn<>("finish time");
		finishTimeColumnFutureTable.setPrefWidth(195);
		finishTimeColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
		
		priceColumnFutureTable = new TableColumn<>("price(NIS)");
		priceColumnFutureTable.setPrefWidth(195);
		priceColumnFutureTable.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		parkingSlotIdColumnPastTable = new TableColumn<>("slot id");
		parkingSlotIdColumnPastTable.setPrefWidth(195);
		parkingSlotIdColumnPastTable.setCellValueFactory(new PropertyValueFactory<>("parkingSlotId"));
		
		startTimeColumnPastTable = new TableColumn<>("start time");
		startTimeColumnPastTable.setPrefWidth(195);
		startTimeColumnPastTable.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		finishTimeColumnPastTable = new TableColumn<>("finish time");
		finishTimeColumnPastTable.setPrefWidth(195);
		finishTimeColumnPastTable.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
		
		priceColumnPastTable = new TableColumn<>("price(NIS)");
		priceColumnPastTable.setPrefWidth(195);
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
            		
       				double price = order.getPrice();
       				order.setPrice(Math.round(price * 100.0) / 100.0);
            		
            		if (order.getStartTime().after(new Date())){
            			futureOrders.add(order);
            		} else {
            			pastOrders.add(order);
            		}
            	}
            	futureOrdersTable.setItems(futureOrders);
            	pastOrdersTable.setItems(pastOrders);
         	   	newOrderButton.setDisable(false);
            	cancelOrderButton.setDisable(false);
                progressIndicator.setVisible(false); 
           }
       });
			
	}
}
