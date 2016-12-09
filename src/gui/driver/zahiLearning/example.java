/**
 * 
 * @Author Zahi Mizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;

public class example extends Application {

	Stage window;
	Scene scene1, scene2; 

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		window = primaryStage;
		Label label1 = new Label("Welcome to first Scene!"); 
		Button button1 = new Button ("Click Me!"); 
		button1.setOnAction(e -> AlertBox.display("Title", "Alert Box...")); 

		VBox layout1 = new VBox();
			
		layout1.getChildren().addAll(label1,button1);
		scene1 = new Scene(layout1, 600, 300);
		

		window.setTitle("The Window");
		window.setScene(scene1);
		window.show(); 
	}

}
