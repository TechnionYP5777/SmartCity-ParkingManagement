package gui.manager;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

public class Opening extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ManagerEdit1 ME1 = new ManagerEdit1();
		ArrayList<AbstractWindow> prevWindows = new ArrayList<AbstractWindow>();
		prevWindows.add(ME1);
		ME1.display(primaryStage, WindowEnum.NONE, prevWindows);
		
	}

}
