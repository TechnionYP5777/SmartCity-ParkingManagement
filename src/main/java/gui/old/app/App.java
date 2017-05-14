package gui.old.app;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	@Override
	public void start(final Stage primaryStage) throws Exception {
		final Parent root = FXMLLoader.load(getClass().getResource("ManagerMainScreen.fxml"));
		primaryStage.setTitle("Manage Parking");
		final Scene scene = new Scene(root, 1200, 670);
		final URL url = this.getClass().getResource("NotificationStyle.css");
		if (url == null) {
			System.out.println("Resource not found. Aborting.");
			System.exit(-1);
		}
		scene.getStylesheets().add(url.toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(final String[] args) {
		launch(args);
	}
}
