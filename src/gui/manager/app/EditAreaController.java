package gui.manager.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
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
    private JFXTextField slotsInput;

    @FXML
    private HBox radioHBox;
    
    @FXML
    private JFXToggleButton tempToggle;

    @FXML
    private JFXButton cancelBtn;

    @FXML
    private JFXButton ChngBtn;
    
    public void initialize(URL location, ResourceBundle __) {
    	areaLbl.setText("Edit Area");
    	ToggleGroup group = new ToggleGroup();
    	(new SelectAnArea()).getAllPossibleColors().forEach(c -> {
			JFXRadioButton rbtn = new JFXRadioButton(
					(Character.toUpperCase(c.charAt(0)) + c.substring(1).toLowerCase()));
			radioHBox.getChildren().addAll((rbtn));
			rbtn.setToggleGroup(group);
			if (c == "RED")
				rbtn.setSelected(true);
		});
    }
    
	public static void display() throws IOException {
		Stage window = new Stage();
		Parent editAreaParent = FXMLLoader.load(EditAreaController.class.getResource("EditArea.fxml"));
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Edit Parking Area");
		Scene editScreen = new Scene(editAreaParent);
		window.setScene(editScreen);
		window.showAndWait();
	}
}
