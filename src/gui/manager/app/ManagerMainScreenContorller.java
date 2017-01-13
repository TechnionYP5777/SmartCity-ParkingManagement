package gui.manager.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import com.lynden.gmapsfx.GoogleMapView;

import gui.map.ManegerMap;

public class ManagerMainScreenContorller implements Initializable {

	@FXML
	private BorderPane mainBorderPane;
	
	@FXML
	private Pane mapView;
	
	@FXML
	private BorderPane mapBorderPane;
	
	@FXML
	private ListView<String> parkingAreas;
	
	@FXML
	private MenuBar mainMenuBar;
	
	@FXML
	void switchToNotificationsScene(ActionEvent $)  throws Exception {
		Parent notificationsRoot = FXMLLoader.load(getClass().getResource("Notifications.fxml"));
		Scene notificationsScene = new Scene(notificationsRoot);
		Stage appStage = (Stage) mainMenuBar.getScene().getWindow();
		appStage.hide();
		appStage.setScene(notificationsScene);
		appStage.show();
		
	}
	
	Label testLabel = new Label();
    ManegerMap map = new ManegerMap();
    GoogleMapView view = new GoogleMapView(Locale.getDefault().getLanguage(), null); 

    
    @Override
    public void initialize(URL location, ResourceBundle __) {
    	map.SetMapComponent(view);
        System.out.println("View is now loading...");
        mapView.getChildren().addAll(view);
        view.setMaxHeight(400);
        view.setMaxWidth(500);
        view.setMinHeight(Region.USE_COMPUTED_SIZE);
        view.setMinWidth(Region.USE_COMPUTED_SIZE);
        BorderPane.setAlignment(view, Pos.TOP_CENTER);
        ObservableList<String> areas = FXCollections.observableArrayList("Taub", "Ulman","Nesher");
        parkingAreas.setItems(areas);
        
        System.out.println("View is now loaded!");
    }
}