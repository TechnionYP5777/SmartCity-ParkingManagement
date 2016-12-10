/**
 * 
 * @Author zahimizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
;

public class example extends Application {

	Stage window;
	Scene scene;
	Button button; 
	ComboBox<String> comboBox; 

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("choicebox example");
		
		comboBox = new ComboBox<> (); 
		comboBox.getItems().addAll ("Pizza", "Sushi", "Hamburger", "Fries");
		
		comboBox.setPromptText ("What is your favorite food?");

		//Button
		button = new Button ("Click Me!"); 
		button.setOnAction(e -> System.out.println(comboBox.getValue()));
		
		comboBox.setOnAction(e -> System.out.println("User Selected: "+comboBox.getValue()));
	    VBox layout = new VBox (10); 
	    layout.setPadding(new Insets(20,20,20,20));
	    layout.getChildren().addAll(comboBox, button);
		scene = new Scene (layout, 300,250);
		
		window.setScene(scene);
		window.show();
	}
	

}
