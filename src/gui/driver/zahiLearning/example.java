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
import javafx.scene.layout.StackPane;

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
		window.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});
		Button button = new Button("Close Progress!");
		button.setOnAction(e -> closeProgram());
		StackPane layout1 = new StackPane();

		layout1.getChildren().addAll(button);
		scene1 = new Scene(layout1, 600, 300);

		window.setTitle("The Window");
		window.setScene(scene1);
		window.show();
	}

	private void closeProgram() {
		if (ConfirmBox.display("title", "Are you sure you want to exit?"))
			window.close();

	}

}
