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
import com.jfoenix.controls.*;

import Exceptions.LoginException;

public class MainScreenController {
	
	@FXML
	private Button newOrderButton;
	@FXML
	private TableView futureOrdersTable;
	@FXML
	private Button cancelOrderButton;
	@FXML
	private TableView ordersHistoryTable;
	
	@FXML
    protected void initialize(){
		
	}
	@FXML
	public void newOrderButtonClicked(ActionEvent event) throws Exception {
		System.out.println("newOrderButtonClicked");
	}
	@FXML
	public void cancelOrderButtonClicked(ActionEvent event) throws Exception {
		System.out.println("cancelOrderButtonClicked");
	}
	
}
