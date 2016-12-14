package gui.driver.shaharTesting;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	static Scene scene;
	Stage window;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if(ConfirmBox.display("Choose Action", "would you like to get password?"))
			GetPassByMail.display(primaryStage);
		else
			AlertBox.display("Goodbye!", "Hope you enjoyed!");
		
		
		
		//primaryStage.close();
		//VHBoxesExperiment.display(primaryStage);
	}

}
