package gui.manager.OrLearning;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class CreatingABasicWindow extends Application implements EventHandler<ActionEvent> {
	Stage window;
	Button myButton;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage mainStage) {
		window=mainStage;
		window.setTitle("Yearly Project is awesome");
		
		myButton = new Button();
		myButton.setText("Click Here");
		myButton.setOnAction(this);
		StackPane layout = new StackPane();
		layout.getChildren().add(myButton);
		Scene scene = new Scene(layout, 500, 500);
		
		window.setScene(scene);
		window.show();
	}
	
	@Override
	public void handle(ActionEvent ¢) {
		if (¢.getSource()==myButton)
			System.out.println("That's worked");
	}
	
}
