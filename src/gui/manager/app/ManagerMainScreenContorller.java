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
<<<<<<< HEAD
import javafx.util.Duration;

=======
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
>>>>>>> 8c7532649735165f1698ace6d6e3cd5b643671f8
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

<<<<<<< HEAD
import org.controlsfx.control.Notifications;

import gui.map.PmMap;
=======
import com.lynden.gmapsfx.GoogleMapView;

import gui.map.ManegerMap;
>>>>>>> 8c7532649735165f1698ace6d6e3cd5b643671f8

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
    ManegerMap map = new ManegerMap();
    GoogleMapView view = new GoogleMapView(Locale.getDefault().getLanguage(), null); 

    
    @Override
    public void initialize(URL location, ResourceBundle __) {
    	map.SetMapComponent(view);
        System.out.println("View is now loading...");
<<<<<<< HEAD
        mapPane.setMaxHeight(450);
        mapPane.setMaxWidth(550);
        mapPane.setMinHeight(Region.USE_COMPUTED_SIZE);
        mapPane.setMinWidth(Region.USE_COMPUTED_SIZE);
        mapView.getChildren().addAll(mapPane);
        BorderPane.setAlignment(mapPane, Pos.TOP_CENTER);
        
        //Initialize parking areas list
=======
        mapView.getChildren().addAll(view);
        view.setMaxHeight(400);
        view.setMaxWidth(500);
        view.setMinHeight(Region.USE_COMPUTED_SIZE);
        view.setMinWidth(Region.USE_COMPUTED_SIZE);
        BorderPane.setAlignment(view, Pos.TOP_CENTER);
>>>>>>> 8c7532649735165f1698ace6d6e3cd5b643671f8
        ObservableList<String> areas = FXCollections.observableArrayList("Taub", "Ulman","Nesher");
        parkingAreas.setItems(areas);
        
        System.out.println("View is now loaded!");
    }
}