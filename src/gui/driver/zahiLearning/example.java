/**
 * 
 * @Author Zahi Mizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
		window.setTitle("checkbox example");
		
		//CheckBox
		CheckBox box1 = new CheckBox ("Option No.1"); 
		CheckBox box2 = new CheckBox ("Option No.2"); 
		box1.setSelected(true);
		//Button
		button = new Button ("Click Me!"); 
		button.setOnAction( e -> handleOptions(box1,box2));
	    VBox layout = new VBox (10); 
	    layout.setPadding(new Insets(20,20,20,20));
	    layout.getChildren().addAll(box1, box2, button);
		scene = new Scene (layout, 300,250);
		
		window.setScene(scene);
		window.show();
	}
	
	private void handleOptions (CheckBox b1, CheckBox b2) {
		String message = "User chose:\n" + (!b1.isSelected() ? "" : "option no.1\n"); 
		if (b2.isSelected())
			message += "option no.2\n";
		
		System.out.println(message);
		
	}
}
