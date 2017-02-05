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

public class ConfirmBox {

	static boolean answer;

	public static boolean display(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(50);
		Label label = new Label(message);

		Button yesButton = new Button("Yes"), noButton = new Button("No");
		yesButton.setOnAction(λ -> {
			answer = true;
			window.close();
		});

		noButton.setOnAction(λ -> {
			answer = false;
			window.close();
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, yesButton, noButton);
		layout.setAlignment(Pos.CENTER);

		window.setScene(new Scene(layout));
		window.showAndWait();

		return answer;
	}

}
