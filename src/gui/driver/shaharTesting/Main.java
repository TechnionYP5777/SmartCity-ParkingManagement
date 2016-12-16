package gui.driver.shaharTesting;

import java.util.ArrayList;

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
		
		ChooseAction chooseActionObject = new ChooseAction();
		ArrayList<AbstractWindow> prevWindows = new ArrayList<AbstractWindow>();
		prevWindows.add(chooseActionObject);
		chooseActionObject.display(primaryStage, WindowEnum.NONE, prevWindows);
		
		
//		if (!ConfirmBox.display("Choose Action", "would you like to get password?"))
//			AlertBox.display("Goodbye!", "Hope you enjoyed!");
//		else
//			GetPassByMail.display(primaryStage, WindowEnum.NONE);
		
		
		
		//primaryStage.close();
		//VHBoxesExperiment.display(primaryStage);
	}

}
