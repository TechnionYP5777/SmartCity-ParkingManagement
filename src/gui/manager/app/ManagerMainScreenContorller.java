package gui.manager.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.ResourceBundle;

import gui.map.PmMap;

public class ManagerMainScreenContorller implements Initializable {

	@FXML
	private BorderPane mainBorderPane;
	
	@FXML
	private Pane mapView;
	
	@FXML
	private BorderPane mapBorderPane;
	
	@FXML
	private ListView<String> parkingAreas;
	
	Label testLabel = new Label();
    PmMap map = new PmMap();
    BorderPane mapPane = map.getMapBorderPane();
    
    @Override
    public void initialize(URL location, ResourceBundle __) {
        System.out.println("View is now loading...");
        mapPane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        mapPane.setMaxWidth(Region.USE_COMPUTED_SIZE);
        mapPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        mapPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        mapView.getChildren().addAll(mapPane);
        BorderPane.setAlignment(mapPane, Pos.TOP_CENTER);
        
        //Initialize parking areas list
        ObservableList<String> areas = FXCollections.observableArrayList("Taub", "Ulman","Nesher");
        parkingAreas.setItems(areas);
        
        System.out.println("View is now loaded!");
    }
}