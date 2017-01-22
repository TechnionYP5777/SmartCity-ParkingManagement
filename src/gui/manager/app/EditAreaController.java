package gui.manager.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.parse4j.ParseException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;

import data.members.ParkingArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
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
    private JFXButton cancelBtn;

    @FXML
    private JFXButton ChngBtn;
    
    private static ParkingArea parkingAreaElement;
    

	
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
    	final IntegerTextField slotsField = new IntegerTextField(0, 250, 0);
    	
    	slotsField.valueProperty().bindBidirectional(slotsSlider.valueProperty());
    	slotsField.setPrefWidth(45);
    	slotsSlider.setMin(0);
    	slotsSlider.setMax(250);
      	slotsSlider.setValue(0);
    	slotsInput.getChildren().add(slotsField);
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
