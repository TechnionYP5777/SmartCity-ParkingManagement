package gui.manager.OrLearning;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreatingABasicWindow extends Application implements EventHandler<ActionEvent> {
	Stage window;
	Button myButton, switchToScene2, switchToScene1, alertBoxBtn;
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
		
		alertBoxBtn = new Button();
		alertBoxBtn.setText("Open Alert Box");
		alertBoxBtn.setOnAction(e -> AlertBox.display("Box", "Please close this window"));
		
		switchToScene1 = new Button();
		switchToScene1.setText("Switch to 1");
		switchToScene1.setOnAction(e -> window.setScene(scene1));
		
		//Layouts
		VBox layout = new VBox(10);
		layout.getChildren().addAll(myButton,switchToScene2, alertBoxBtn);
		layout.setAlignment(Pos.CENTER);
		scene1 = new Scene(layout, 500, 500);
		
		//GridPane form
		GridPane gridLayout = new GridPane();
		gridLayout.setPadding(new Insets(15,15,15,15));
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		
		//labels & Text Fields
		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 0);
		
		TextField usernameInput = new TextField();
		usernameInput.setPromptText("username");
		GridPane.setConstraints(usernameInput, 1, 0);
		
		Label passLabel = new Label("Password:");
		GridPane.setConstraints(passLabel, 0, 1);
		
		TextField passInput = new TextField();
		passInput.setPromptText("password");
		GridPane.setConstraints(passInput, 1, 1);
		
		//Login button
		Button loginBtn = new Button("Login");
		GridPane.setConstraints(loginBtn, 0, 2);
		
		//Taking care of switchToScene1 button from before
		GridPane.setConstraints(switchToScene1, 0, 3);

		
		gridLayout.getChildren().addAll(switchToScene1, usernameLabel, usernameInput, passLabel, passInput, loginBtn);
		scene2 = new Scene(gridLayout, 500, 500);
		
		window.setScene(scene1);
		window.show();
	}
	
	@Override
	public void handle(ActionEvent ¢) {
		if (¢.getSource()==myButton)
			System.out.println("That's worked");
	}
	
}
