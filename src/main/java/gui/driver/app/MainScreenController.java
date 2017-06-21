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
	private TableView<PresentOrder> ordersHistoryTable;
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
		ordersHistoryTable.getColumns().setAll(parkingSlotIdColumn, startTimeColumn, finishTimeColumn, priceColumn);
		
	}
	
	private ObservableList<PresentOrder> getOrders(List<PresentOrder> orders){
		ObservableList<PresentOrder> returnOrders = FXCollections.observableArrayList();
		for (PresentOrder order : orders){
			returnOrders.add(order);
		}
		return returnOrders;
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
        	   System.out.println(userId);
        	   newOrderButton.setDisable(false);
           }
       });
	}
	public void setUserId(String userId){
		this.userId = userId;
	}
}











