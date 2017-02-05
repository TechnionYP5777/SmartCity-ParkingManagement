package gui.manager.app;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import org.parse4j.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;

import data.members.DurationType;
import data.members.ParkingArea;
import data.members.StickersColor;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private JFXDatePicker untilDate;

    @FXML
    private JFXDatePicker untilHour;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton ChngBtn;
    
	final IntegerTextField slotsField = new IntegerTextField(0, 250, 0);
	
	ToggleGroup group = new ToggleGroup();
	String selectedColor;
	DurationType durationType = DurationType.PERMANENTLY;
	
	@FXML
	private void closeButtonAction(){
	    ((Stage) cancelBtn.getScene().getWindow()).close();
	}
	
	@FXML
	private void changeButtonAction(){
		manager.logic.ManualUpdate manualUpdate = new manager.logic.ManualUpdate();
		Date date=durationType != DurationType.TEMPORARY ? null
				: Date.from(untilDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
		System.out.println("Data sent to update is:");
		System.out.println(parkingAreaElement.getAreaId());
		System.out.println(slotsField.getText());
		System.out.println(StickersColor.valueOf(selectedColor.toUpperCase()));
		System.out.println(durationType);
		System.out.println(date);
		try {
			manualUpdate.updateArea(parkingAreaElement.getAreaId(), Integer.parseInt(slotsField.getText()), StickersColor.valueOf(selectedColor.toUpperCase()), durationType, date);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Update Succeeded");
			alert.setHeaderText(null);
			alert.setContentText("Update Succeeded");
			alert.showAndWait();
			((Stage) cancelBtn.getScene().getWindow()).close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("An Error Occurred");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
    
    private static ParkingArea parkingAreaElement;
    
    //Toggle Switch preparations
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	public SimpleBooleanProperty switchOnProperty() {
		return switchedOn;
		}
	
    public void initialize(URL location, ResourceBundle __) {
    	areaLbl.setText(parkingAreaElement.getName());
    	(new SelectAnArea()).getAllPossibleColors().forEach(c -> {
			JFXRadioButton rbtn = new JFXRadioButton(
					(Character.toUpperCase(c.charAt(0)) + c.substring(1).toLowerCase()));
			radioHBox.getChildren().addAll(rbtn);
			rbtn.setToggleGroup(group);
			if (c == parkingAreaElement.getColor().name())
				rbtn.setSelected(true);
				selectedColor=parkingAreaElement.getColor().name();
		});
    	
    	group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    	    public void changed(ObservableValue<? extends Toggle> ov, Toggle prev, Toggle next) {
				if (group.getSelectedToggle() != null)
					selectedColor = ((JFXRadioButton) next.getToggleGroup().getSelectedToggle()).getText();
				System.out.println("Selected Radio Button - " + ((JFXRadioButton) next.getToggleGroup().getSelectedToggle()).getText());
			} 
    	});
    	
    	//Slider Initialization: Number of slots
    	
    	slotsField.valueProperty().bindBidirectional(slotsSlider.valueProperty());
    	slotsField.setPrefWidth(45);
    	slotsSlider.setMin(0);
    	slotsSlider.setMax(250);
      	slotsSlider.setValue(0);
    	slotsInput.getChildren().add(slotsField);
    	
    	//Toggle switch initialization
    	tempBp.setCenter(null);
    	tempToggle.setOnAction(λ -> switchedOn.set(!switchedOn.get()));
    	HBoxPrefHeight = 150.0d;   	
		switchedOn.addListener((listener,prev,next) -> {
			Timeline timeline = new Timeline();
			if (next) {
				durationType = DurationType.TEMPORARY;
				tempHBox.setPrefHeight(0.0d);
				tempBp.setCenter(tempHBox);
				timeline.getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, new KeyValue(tempHBox.prefHeightProperty(), 0)), new KeyFrame(
								Duration.millis(300), new KeyValue(tempHBox.prefHeightProperty(), HBoxPrefHeight)));
				timeline.play();
			}
			else {
				durationType = DurationType.PERMANENTLY;
				tempBp.setCenter(tempHBox);
				timeline.getKeyFrames().addAll(
						new KeyFrame(Duration.ZERO, new KeyValue(tempHBox.prefHeightProperty(), HBoxPrefHeight)),
						new KeyFrame(Duration.millis(1), new KeyValue(tempHBox.prefHeightProperty(), 0)));
				timeline.play();
				timeline.setOnFinished(λ -> tempBp.setCenter(null));
			}
		});
    }
    
	public static void display(String parkingAreaID) throws IOException, ParseException {
		parkingAreaElement = new ParkingArea(parkingAreaID);
		Stage window = new Stage();
		Parent editAreaParent = FXMLLoader.load(EditAreaController.class.getResource("EditArea.fxml"));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Edit Parking Area: " + parkingAreaElement.getName());
		window.setScene(new Scene(editAreaParent));
		window.showAndWait();
	}
}
