/**
 * 
 * @Author zahimizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
;

public class example extends Application {

	Stage window;
	Scene scene;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label user = new Label ("Username");
		user.setId("label-bold");
		TextField nameInput = new TextField("Zahi");
		
		Label pass = new Label ("Password");
		TextField passInput = new TextField();
		passInput.setPromptText("password");
		
		Button button1 = new Button("Login"), button2 = new Button("Sign Up");
		
		button2.getStyleClass().add("button-blue");
		
		GridPane.setConstraints (user,0,0);
		GridPane.setConstraints(nameInput, 1,0); 
		GridPane.setConstraints(pass, 0,1);
		GridPane.setConstraints(passInput, 1,1);
		GridPane.setConstraints(button1,1,2); 
		GridPane.setConstraints(button2,1,3); 
		
		
		grid.getChildren().addAll(user,nameInput,pass,passInput,button1, button2);
		Scene scene = new Scene (grid);
		scene.getStylesheets().add(getClass().getResource("example_style.css").toExternalForm());
		window.setScene(scene);
		window.setTitle("The Window");
		window.show();
	}

}