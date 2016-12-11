package gui.manager.OrLearning;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CreatingABasicWindow extends Application {
	Stage window;
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage mainStage) {
		window=mainStage;
		window.setTitle("Yearly Project is awesome");
		
		StackPane layout = new StackPane();
		layout.getChildren().add(new Button("DO NOT CLICK HERE"));
		Scene scene = new Scene(layout, 500, 500);
		
		window.setScene(scene);
		window.show();
	}
	
}
