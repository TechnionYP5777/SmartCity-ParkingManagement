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
	Button myButton, switchToScene2, switchToScene1;
	Scene scene1, scene2;
	
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
		
		//Change scene buttons, using lambda action event
		switchToScene2 = new Button();
		switchToScene2.setText("Switch to 2");
		switchToScene2.setOnAction(e -> window.setScene(scene2));
		
		switchToScene1 = new Button();
		switchToScene1.setText("Switch to 1");
		switchToScene1.setOnAction(e -> window.setScene(scene1));
		
		//Layouts
		StackPane layout = new StackPane();
		layout.getChildren().add(switchToScene2);
		scene1 = new Scene(layout, 500, 500);
		
		StackPane layout2 = new StackPane();
		layout2.getChildren().add(switchToScene1);
		scene2 = new Scene(layout2, 500, 500);
		
		window.setScene(scene1);
		window.show();
	}
	
	@Override
	public void handle(ActionEvent ¢) {
		if (¢.getSource()==myButton)
			System.out.println("That's worked");
	}
	
}
