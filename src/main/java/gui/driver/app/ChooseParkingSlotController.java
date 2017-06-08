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
public class ChooseParkingSlotController {
	
	@FXML
	private WebView myWebView;
	@FXML
	private Button addMarkersButton;
	@FXML
	private Button removeMarkersButton;
	
	@FXML
    protected void initialize(){
		WebEngine engine = myWebView.getEngine();
		engine.load("https://www.google.com");
		
    }
	
	@FXML
	public void addMarkers(ActionEvent event) throws Exception{

	}
	@FXML
	public void deleteMarkers(ActionEvent event) throws Exception{

	}
	
}
