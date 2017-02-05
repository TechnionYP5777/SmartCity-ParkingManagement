package gui.driver.shaharTesting;

import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
//import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Tut4 extends Application {

	Stage window;
	Scene scene, scene1, scene2;

	/*
	 * public static void main(String[] args) { launch(args); }
	 */

	@Override
	public void start(final Stage primaryStage) throws Exception {
		window = primaryStage;
		final Label label = new Label("Welcome to the 1st scene");
		final String message = String.valueOf("Are you sure you want to send the pictures?");
		final Button button = new Button("click me!");
		button.setOnAction(λ -> System.out.println(ConfirmBox.display("Sending pics", message)));

		final VBox layout = new VBox(20);
		layout.getChildren().addAll(label, button);
		scene = new Scene(layout, 300, 200);
		window.setScene(scene);

		/*
		 * Button button1 = new Button("go to scene2"); button1.setOnAction(e->
		 * AlertBox.display("Title!!", "Message!!"));
		 * 
		 * VBox layout1 = new VBox(20); layout1.getChildren().addAll(label,
		 * button1); scene1 = new Scene(layout1, 300, 200);
		 * 
		 * Button button2 = new Button("go back to scene1");
		 * button2.setOnAction(e-> window.setScene(scene1));
		 * 
		 * StackPane layout2 = new StackPane();
		 * layout2.getChildren().addAll(button2); scene2 = new Scene(layout2,
		 * 200, 100);
		 * 
		 * window.setScene(scene1);
		 */

		window.setTitle("Window title");
		window.show();

	}

}
