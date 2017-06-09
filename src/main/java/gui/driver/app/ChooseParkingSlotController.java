package gui.driver.app;
import java.util.HashMap;
import java.util.Map;

import data.management.DBManager;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.web.*;
import java.net.*;
import java.io.*;


public class ChooseParkingSlotController {
	
	@FXML
	private WebView myWebView;
	@FXML
	private Button addMarkersButton;
	@FXML
	private Button changeMarkersButton;
	
	private WebEngine engine;
	
	@FXML
    protected void initialize(){
		engine = myWebView.getEngine();
		URL url = getClass().getResource("map.html");
		engine.load(url.toExternalForm());
		
    }
	
	@FXML
	public void addMarkers(ActionEvent event) throws Exception{
		System.out.println("CALLED");
			engine.executeScript("addMarker(32.778573, 35.021421);");
	}
	@FXML
	public void changeMarker(ActionEvent event) throws Exception{
		engine.executeScript("setSelected(0);");
		
	}
	
}
