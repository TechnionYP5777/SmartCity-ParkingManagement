/*
 * 
 * @author zahimizrahi
 * 
 */
package gui.driver.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
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
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);

		//title 
		DropShadow shadow = new DropShadow(); 
		shadow.setOffsetY(4.0);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
		Label title = new Label();
		title.setEffect(shadow);
		title.setTextFill(Color.ROYALBLUE);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setText("Login");
		title.setFont(Font.font(null, FontWeight.BOLD, 48));

		//user
		Label user = new Label("Username");
		TextField nameInput = new TextField();
		nameInput.setPromptText("username");
		
		//password
		Label pass = new Label("Password");
		PasswordField passInput = new PasswordField();
		passInput.setPromptText("password");
		Label forgotPass = new Label ("Forgot Password?");
		
		Button button = new Button("Login");
		GridPane.setRowIndex(title, 0);
		GridPane.setConstraints(user, 0, 1);
		GridPane.setConstraints(nameInput, 1, 1);
		GridPane.setConstraints(pass, 0, 2);
		GridPane.setConstraints(passInput, 1, 2);
		GridPane.setConstraints(button, 1, 3);
		GridPane.setConstraints(forgotPass,3,3);

		grid.getChildren().addAll(title,user, nameInput, pass, passInput, button,forgotPass);
		button.setOnAction( e-> display("Successful", "You have successfully logged in!"));
		Scene scene = new Scene(grid);
		window.setScene(scene);
		window.setTitle("Login");
		window.show();
	}
	
	
	public static void display (String title, String message) {
		Stage messageBox = new Stage();
		messageBox.initModality(Modality.APPLICATION_MODAL);
		messageBox.setTitle(title); 
		messageBox.setMinWidth(250);
		messageBox.setMinHeight(50);
		Label label = new Label (message); 
		Button closeButton = new Button ("Close");
		closeButton.setOnAction(e -> messageBox.close());
		
		VBox layout = new VBox (10); 
		layout.getChildren().addAll(label, closeButton); 
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout); 
		messageBox.setScene(scene);
		messageBox.showAndWait();
	}

}
