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
    protected void initialize(){
		setColumns();
	}
	@FXML
	public void newOrderButtonClicked(ActionEvent event) throws Exception {
		System.out.println("newOrderButtonClicked");
	}
	@FXML
	public void cancelOrderButtonClicked(ActionEvent event) throws Exception {
		System.out.println("cancelOrderButtonClicked");
	}
	
	private void setColumns(){
		parkingSlotIdColumn = new TableColumn<>("slot id");
		parkingSlotIdColumn.setPrefWidth(100);
		parkingSlotIdColumn.setCellValueFactory(new PropertyValueFactory<>("parkingSlotId"));
		
		startTimeColumn = new TableColumn<>("start time");
		startTimeColumn.setPrefWidth(100);
		startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
		
		finishTimeColumn = new TableColumn<>("finish time");
		finishTimeColumn.setPrefWidth(100);
		finishTimeColumn.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
		
		priceColumn = new TableColumn<>("price");
		priceColumn.setPrefWidth(100);
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
	}
}
