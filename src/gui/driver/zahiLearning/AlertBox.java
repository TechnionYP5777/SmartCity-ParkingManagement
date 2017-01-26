/**
 * 
 * @Author zahimizrahi
 * 
 * 
 */

package gui.driver.zahiLearning;


import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlertBox {
	
	public static void display (String title, String message) {
		Stage alertWindow = new Stage();
		alertWindow.initModality(Modality.APPLICATION_MODAL);
		alertWindow.setTitle(title); 
		alertWindow.setMinWidth(250);
		alertWindow.setMinHeight(50);
		Label label = new Label (message); 
		Button closeButton = new Button ("Close");
		closeButton.setOnAction(e -> alertWindow.close());
		
		VBox layout = new VBox (10); 
		layout.getChildren().addAll(label, closeButton); 
		layout.setAlignment(Pos.CENTER);
		
		alertWindow.setScene(new Scene(layout));
		alertWindow.showAndWait();
	}

}
