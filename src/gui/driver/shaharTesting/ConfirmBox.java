package gui.driver.shaharTesting;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox{
	
	static boolean answer;
	static Button yesButton,noButton;
	
	public static boolean display(String title, String message){
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText(message);
		
		yesButton = new Button("Yes");
		yesButton.setOnAction(e-> {
			
			answer = true;
			window.close();
		});
		
		noButton = new Button("No");
		noButton.setOnAction(e-> {
		answer = false;	
		window.close();
		});
		
		VBox layout = new VBox();
		layout.getChildren().addAll(label, noButton, yesButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}

}
