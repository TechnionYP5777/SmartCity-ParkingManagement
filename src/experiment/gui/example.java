
package experiment.gui;

import javafx.application.Application;
import javafx.stage.Stage; 
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class example extends Application implements EventHandler<ActionEvent> {
 
	Button button1; 
	Button button2; 
	public static void main (String[] args) {
		launch(args); 
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("First Window"); 
		
		button1 = new Button ();
		button2 = new Button(); 
		
		button1.setText("Accept");
		button1.setOnAction(this); 
		
		button2.setText("Accept");
		button2.setOnAction(this); 
		
		StackPane layout = new StackPane ();
		
		layout.getChildren().add(button1);
		layout.getChildren().add(button2);
		Scene scene = new Scene (layout,300,250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	@Override
	public void handle(ActionEvent ¢) {
		if (¢.getSource().equals(button1))
			System.out.println("OK!");
		if (¢.getSource().equals(button2))
			System.out.println("You Can't!");
	}
}
