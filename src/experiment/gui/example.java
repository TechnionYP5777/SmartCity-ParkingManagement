
package experiment.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;

public class example extends Application {

	Button button1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("First Window");

		button1 = new Button();

		button1.setText("Accept");
		button1.setOnAction(e -> {
			
		System.out.println("OK");
		System.out.println("Got it?");
		});

		StackPane layout = new StackPane();

		layout.getChildren().add(button1);
		Scene scene = new Scene(layout, 300, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
