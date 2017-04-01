package gui.driver.shaharTesting;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class Main extends Application {
	static Scene scene;
	Stage window;

	public static Class<? extends AbstractWindow> getLastWindowClass(final ArrayList<AbstractWindow> prevWindows) {
		switch (prevWindows.get(prevWindows.size() - 1).windowEnum) {
		case CHOOSE_ACTION:
			return ChooseAction.class;
		case GET_PASS_BY_MAIL:
			return GetPassByMail.class;
		case MY_DETAILS:
			return MyDetails.class;
		case MY_DETAILS_EDIT:
			return MyDetailsEdit.class;
		default:
			return null;
		}
	}

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		final ChooseAction chooseActionObject = new ChooseAction();
		final ArrayList<AbstractWindow> prevWindows = new ArrayList<AbstractWindow>();
		prevWindows.add(chooseActionObject);

		final Task<Object> task = new Task<Object>() {

			@Override
			protected Object call() throws Exception {
				final int s = 100;
				final AudioClip audio = new AudioClip(getClass().getResource("sound.mp3").toExternalForm());
				audio.setVolume(0.5f);
				audio.setCycleCount(s);
				audio.play();
				return null;
			}
		};
		new Thread(task).start();
		chooseActionObject.display(primaryStage, WindowEnum.NONE, prevWindows);
		// if (!ConfirmBox.display("Choose Action", "would you like to get
		// password?"))
		// AlertBox.display("Goodbye!", "Hope you enjoyed!");
		// else
		// GetPassByMail.display(primaryStage, WindowEnum.NONE);

		// primaryStage.close();
		// VHBoxesExperiment.display(primaryStage);
	}

}
