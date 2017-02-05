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
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.parse4j.ParseException;

import com.jfoenix.controls.JFXButton;
import com.lynden.gmapsfx.GoogleMapView;

import data.members.MapLocation;
import data.members.ParkingArea;
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
	private ListView<String> parkingAreasListView;

	@FXML
	private JFXButton showAllBtn;

	@FXML
	private JFXButton editBtn;

	@FXML
	private Label totalSlotsLbl;

	@FXML
	private Label freeSlotsLbl;

	@FXML
	private Label takenSlotsLbl;

	@FXML
	private MenuBar mainMenuBar;

	@FXML
	private void triggerNotifications() {
		Notifications.create().title("Color has changed").text("In Taub, from RED to GRAY").owner(mainBorderPane)
				.graphic(new ImageView(new Image(getClass().getResourceAsStream("project-logo-with-shadow.png"))))
				.hideAfter(Duration.seconds(7)).position(Pos.BOTTOM_RIGHT).onAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent $) {
						System.out.println("Clicked");
					}
				}).show();
	}

	@FXML
	private void popEditAreaScreen() throws IOException, ParseException {
		EditAreaController.display(parkingAreasListView.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void resetParkingAreasListView() {
		parkingAreasListView.getSelectionModel().clearSelection();
		editBtn.setDisable(true);
		freeSlotsLbl.setText(String.valueOf(allFreeSlots));
		takenSlotsLbl.setText(String.valueOf(allTakenSlots));
		totalSlotsLbl.setText(String.valueOf(allFreeSlots + allTakenSlots));
	}

	private int allFreeSlots;
	private int allTakenSlots;

	@Override
	public void initialize(URL location, ResourceBundle __) {

		editBtn.setDisable(true);

		// Initialize parking areas list
		ParkingAreas pa = new ParkingAreas();
		try {
			parkingAreasListView.setItems(FXCollections.observableList((ArrayList<String>) pa.getParkingAreasNames()));
		} catch (ParseException ¢) {
			¢.printStackTrace();
		}

		// Initialize map
		ParkingAreas pareas = new ParkingAreas();
		HashMap<String, MapLocation> locations = new HashMap<>();
		HashMap<String, String> colors = new HashMap<>();
		try {
			locations = pareas.getParkingAreasLocation();
			pareas.getParkingAreasColor().forEach((k, v) -> colors.put(k, v.name()));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ManegerMap map = new ManegerMap(locations, colors);
		GoogleMapView view = new GoogleMapView(Locale.getDefault().getLanguage(), null);
		map.SetMapComponent(view);
		System.out.println("View is now loading...");
		view.setMaxHeight(Region.USE_COMPUTED_SIZE);
		view.setMaxWidth(Region.USE_COMPUTED_SIZE);
		view.setMinHeight(Region.USE_COMPUTED_SIZE);
		view.setMinWidth(Region.USE_COMPUTED_SIZE);
		mapVBox.getChildren().addAll(view);

		// Initialize total slots amount
		ParkingAreas parkingAreas = new ParkingAreas();
		try {
			allFreeSlots = parkingAreas.getNumOfFreeSlots();
			allTakenSlots = parkingAreas.getNumOfTakenSlots();
		} catch (Exception e) {
			allTakenSlots = allFreeSlots = 0;
			freeSlotsLbl.setTextFill(Color.web("#cc3300"));
			takenSlotsLbl.setTextFill(Color.web("#cc3300"));
			totalSlotsLbl.setTextFill(Color.web("#cc3300"));
		}

		freeSlotsLbl.setText(String.valueOf(allFreeSlots));
		takenSlotsLbl.setText(String.valueOf(allTakenSlots));
		totalSlotsLbl.setText(String.valueOf(allFreeSlots + allTakenSlots));

		// Initialize ListView listener for parking areas
		parkingAreasListView.getSelectionModel().selectedItemProperty().addListener((v, prev, next) -> {
			if (next == null) {
				map.resetMap();
				return;
			}
			map.focusOnParkingArea(next);
			editBtn.setDisable(false);
			ParkingArea area;
			int freeSlots = 0, takenSlots = 0;
			try {
				area = new ParkingArea(next);
				freeSlots = (int) area.getFreeSlots().stream().count();
				takenSlots = (int) area.getTakenSlots().stream().count();
				freeSlotsLbl.setTextFill(Color.web("#000"));
				takenSlotsLbl.setTextFill(Color.web("#000"));
				totalSlotsLbl.setTextFill(Color.web("#000"));
			} catch (Exception e) {
				freeSlotsLbl.setTextFill(Color.web("#cc3300"));
				takenSlotsLbl.setTextFill(Color.web("#cc3300"));
				totalSlotsLbl.setTextFill(Color.web("#cc3300"));
			} finally {
				freeSlotsLbl.setText(String.valueOf(freeSlots));
				takenSlotsLbl.setText(String.valueOf(takenSlots));
				totalSlotsLbl.setText(String.valueOf(freeSlots + takenSlots));
			}

		});
		System.out.println("View is now loaded!");
	}
}