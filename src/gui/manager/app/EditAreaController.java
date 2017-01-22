package gui.manager.app;

import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;

import org.parse4j.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;

import data.members.ParkingArea;
import javafx.animation.KeyValue;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.logic.SelectAnArea;

public class EditAreaController implements Initializable {
	
    @FXML
    private Label areaLbl;
    
    @FXML
    private VBox curbstoneView;

    @FXML
    private Slider slotsSlider;

    @FXML
    private AnchorPane slotsInput;

    @FXML
    private HBox radioHBox;
    
    @FXML
    private JFXToggleButton tempToggle;
    
    @FXML
    private BorderPane tempBp;
    
    @FXML
    private HBox tempHBox;
    
    @FXML
    private Double HBoxPrefHeight = 0.0d;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton ChngBtn;
    

	@FXML
	private void closeButtonAction(){
	    ((Stage) cancelBtn.getScene().getWindow()).close();
	}
    
    private static ParkingArea parkingAreaElement;
    
    //Toggle Switch preparations
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	public SimpleBooleanProperty switchOnProperty() {
		return switchedOn;
		}
	
    public void initialize(URL location, ResourceBundle __) {
    	areaLbl.setText(parkingAreaElement.getName());
    	ToggleGroup group = new ToggleGroup();
    	(new SelectAnArea()).getAllPossibleColors().forEach(c -> {
			JFXRadioButton rbtn = new JFXRadioButton(
					(Character.toUpperCase(c.charAt(0)) + c.substring(1).toLowerCase()));
			radioHBox.getChildren().addAll((rbtn));
			rbtn.setToggleGroup(group);
			if (c == parkingAreaElement.getColor().name())
				rbtn.setSelected(true);
		});
    	
    	//Slider Initialization: Number of slots
    	final IntegerTextField slotsField = new IntegerTextField(0, 250, 0);
    	
    	slotsField.valueProperty().bindBidirectional(slotsSlider.valueProperty());
    	slotsField.setPrefWidth(45);
    	slotsSlider.setMin(0);
    	slotsSlider.setMax(250);
      	slotsSlider.setValue(0);
    	slotsInput.getChildren().add(slotsField);
    	
    	//Toggle switch initialization
    	tempBp.setCenter(null);
    	tempToggle.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
    	HBoxPrefHeight = 150.0d;   	
		switchedOn.addListener((listener,prev,next) -> {
			Timeline timeline = new Timeline();
			if (next) {
				tempHBox.setPrefHeight(0.0d);
				tempBp.setCenter(tempHBox);
				timeline.getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, new KeyValue(tempHBox.prefHeightProperty(), 0)), new KeyFrame(
								Duration.millis(300), new KeyValue(tempHBox.prefHeightProperty(), HBoxPrefHeight)));
				timeline.play();
			}
			else {
				tempBp.setCenter(tempHBox);
				timeline.getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, new KeyValue(tempHBox.prefHeightProperty(), HBoxPrefHeight)),
						new KeyFrame(Duration.millis(1), new KeyValue(tempHBox.prefHeightProperty(), 0)));
				timeline.play();
				timeline.setOnFinished((evt) -> tempBp.setCenter(null));
			}
		});
    }
    
	public static void display(String parkingAreaID) throws IOException, ParseException {
		parkingAreaElement = new ParkingArea(parkingAreaID);
		Stage window = new Stage();
		Parent editAreaParent = FXMLLoader.load(EditAreaController.class.getResource("EditArea.fxml"));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Edit Parking Area: " + parkingAreaElement.getName());
		Scene editScreen = new Scene(editAreaParent);
		window.setScene(editScreen);
		window.showAndWait();
	}
}
