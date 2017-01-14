package gui.manager.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.parse4j.ParseException;

import data.members.ParkingAreas;
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
	
	@FXML
	private MenuBar mainMenuBar;
	
	@FXML
	void triggerNotifications() {
		Notifications.create().title("Color has changed").text("In Taub, from RED to GRAY").owner(mainBorderPane)
				.graphic(new ImageView(new Image(getClass().getResourceAsStream("project-logo-with-shadow.png")))).hideAfter(Duration.seconds(7))
				.position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent $) {
						System.out.println("Clicked");
					}
				}).show();
	}
	
	Label testLabel = new Label();
    PmMap map = new PmMap();
    BorderPane mapPane = map.getMapBorderPane();
    
    @Override
    public void initialize(URL location, ResourceBundle __) {
        System.out.println("View is now loading...");
        mapPane.setMaxHeight(450);
        mapPane.setMaxWidth(550);
        mapPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        mapPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        mapView.getChildren().addAll(mapPane);
        BorderPane.setAlignment(mapPane, Pos.TOP_CENTER);
        
        //Initialize parking areas list
        ParkingAreas pa = new ParkingAreas();
        try {
			parkingAreas.setItems(FXCollections.observableList(((ArrayList<String>) pa.getParkingAreasNames())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        System.out.println("View is now loaded!");
    }
}