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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
;

public class example extends Application {

	Stage window;
	Scene scene;
	Button button; 

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("choicebox example");
		
		//CheckBox
		ChoiceBox<String> choiceBox= new ChoiceBox<>();

		choiceBox.getItems().addAll("Option No.1", "Option No.2", "Option No.3");
		choiceBox.setValue("Option No.1");
		//Button
		button = new Button ("Click Me!"); 
		button.setOnAction (e -> System.out.println(choiceBox.getValue())); 
	    VBox layout = new VBox (10); 
	    layout.setPadding(new Insets(20,20,20,20));
	    layout.getChildren().addAll(choiceBox, button);
		scene = new Scene (layout, 300,250);
		
		window.setScene(scene);
		window.show();
	}
	

}
