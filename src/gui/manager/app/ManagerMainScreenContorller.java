package gui.manager.app;

import javafx.collections.FXCollections;
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
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.parse4j.ParseException;

import com.jfoenix.controls.JFXButton;
import com.lynden.gmapsfx.GoogleMapView;

import data.members.ParkingAreas;
import gui.map.ManegerMap;

public class ManagerMainScreenContorller implements Initializable {

	@FXML
	private BorderPane mainBorderPane;
	
	@FXML
	private VBox mapVBox;
	
	@FXML
	private BorderPane mapBorderPane;
	
	@FXML
	private ListView<String> parkingAreas;
	
    @FXML
    private JFXButton editBtn;
	
	@FXML
	private MenuBar mainMenuBar;
	
	@FXML
	private void triggerNotifications() {
		Notifications.create().title("Color has changed").text("In Taub, from RED to GRAY").owner(mainBorderPane)
				.graphic(new ImageView(new Image(getClass().getResourceAsStream("project-logo-with-shadow.png")))).hideAfter(Duration.seconds(7))
				.position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent $) {
						System.out.println("Clicked");
					}
				}).show();
	}
	
	@FXML
	private void popEditAreaScreen() throws IOException {
		EditAreaController.display();
	}
	
	Label testLabel = new Label();
    //PmMap map = new PmMap();
    //BorderPane mapPane = map.getMapBorderPane();
    ManegerMap map = new ManegerMap();
    //TODO: **************************Need to Change to ********************************************/
    //ManegerMap map = new ManegerMap(locations,colors);
    //Where locations is a HashMap<String,MapLocation> of parking areas and their location from DB 
    //Where colors is a HashMap<String,String> of parking areas and their colors from DB
    //also when change a color please update to map with ChangeColorOfParking(String id ,String color)
    // event handler should use focusOnParkingArea(String AreaID)
    /**********************************************************************************************/
    GoogleMapView view = new GoogleMapView(Locale.getDefault().getLanguage(), null); 
    @Override
    public void initialize(URL location, ResourceBundle __) {
    	map.SetMapComponent(view);
        System.out.println("View is now loading...");
        view.setMaxHeight(Region.USE_COMPUTED_SIZE);
        view.setMaxWidth(Region.USE_COMPUTED_SIZE);
        view.setMinHeight(Region.USE_COMPUTED_SIZE);
        view.setMinWidth(Region.USE_COMPUTED_SIZE);
        mapVBox.getChildren().addAll(view);
        //BorderPane.setAlignment(mapPane, Pos.TOP_CENTER);
        
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