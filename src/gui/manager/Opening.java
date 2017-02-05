package gui.manager;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

public class Opening extends Application {

	public static void main(final String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		final ManagerEdit1 ME1 = new ManagerEdit1();
		final ArrayList<AbstractWindow> prevWindows = new ArrayList<AbstractWindow>();
		prevWindows.add(ME1);
		ME1.display(primaryStage, WindowEnum.NONE, prevWindows);

	}

}
