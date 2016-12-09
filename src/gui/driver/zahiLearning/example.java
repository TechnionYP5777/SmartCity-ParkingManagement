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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class example extends Application {

	Stage window;
	Scene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		
		HBox topMenu = new HBox();
		Button button1 = new Button("File");
		Button button2 = new Button("Edit");
		Button button3 = new Button ("View");
		topMenu.getChildren().addAll(button1,button2, button3); 
		
		VBox leftMenu = new VBox();
		Button button4 = new Button("A");
		Button button5 = new Button("B");
		Button button6 = new Button ("C");
		leftMenu.getChildren().addAll(button4,button5, button6); 
		
		 BorderPane borderPane = new BorderPane (); 
		 borderPane.setTop(topMenu);
		 borderPane.setLeft(leftMenu);
		scene = new Scene(borderPane, 600, 300);

		window.setTitle("The Window");
		window.setScene(scene);
		window.show();
	}

}
