/*
 * 
 * @author zahimizrahi
 * 
 */
package gui.driver.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Login extends Application {

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

		Label user = new Label("Username");
		TextField nameInput = new TextField("Username");

		Label pass = new Label("Password");
		TextField passInput = new TextField("Password");
		passInput.setPromptText("password");
		Label forgotPass = new Label ("Forgot Password?");
		
		Button button = new Button("Login");
		GridPane.setConstraints(user, 0, 0);
		GridPane.setConstraints(nameInput, 1, 0);
		GridPane.setConstraints(pass, 0, 1);
		GridPane.setConstraints(passInput, 1, 1);
		GridPane.setConstraints(button, 1, 2);
		GridPane.setConstraints(forgotPass,2,2);

		grid.getChildren().addAll(user, nameInput, pass, passInput, button,forgotPass);
		Scene scene = new Scene(grid);
		window.setScene(scene);
		window.setTitle("Login");
		window.show();
	}

}
