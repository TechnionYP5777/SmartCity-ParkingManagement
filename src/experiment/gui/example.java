
package experiment.gui;

import javafx.application.Application;
import javafx.stage.Stage; 
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class example extends Application{
 
	Button button; 
	
	public static void main (String[] args) {
		launch(args); 
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Shahar The King"); 
		button = new Button ();
		button.setText("Click Me!");
		StackPane layout = new StackPane ();
		layout.getChildren().add(button);
		Scene scene = new Scene (layout,300,250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
