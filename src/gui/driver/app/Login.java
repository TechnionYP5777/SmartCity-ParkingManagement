/*
 * 
 * @author zahimizrahi
 * 
 */
package gui.driver.app;



import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Login extends AbstractWindow {

	Stage window;
	Scene scene;

	public Login() {
		windowEnum = WindowEnum.LOG_IN;
	}
	
	
	public void display(Stage primaryStage, WindowEnum windowEnum, ArrayList<AbstractWindow> prevWindows) {
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
		Label user = new Label("Car Number");
		TextField nameInput = new TextField();
		nameInput.setPromptText("car number");
		
		//password
		Label pass = new Label("Password");
		PasswordField passInput = new PasswordField();
		passInput.setPromptText("password");
		Label forgotPass = new Label ("Forgot Password?");
		
		
		Button button = new Button("Login");
		GridPane.setConstraints(title, 1, 0);
		GridPane.setConstraints(user, 0, 1);
		GridPane.setConstraints(nameInput, 1, 1);
		GridPane.setConstraints(pass, 0, 2);
		GridPane.setConstraints(passInput, 1, 2);
		GridPane.setConstraints(button, 1, 3);
		GridPane.setConstraints(forgotPass,3,3);

		grid.getChildren().addAll(title,user, nameInput, pass, passInput, button,forgotPass); 
		button.setOnAction( e-> {
			Stage messageBox = new Stage();
			messageBox.initModality(Modality.APPLICATION_MODAL);
			messageBox.setTitle("Successful"); 
			messageBox.setMinWidth(250);
			messageBox.setMinHeight(50);
			Label label = new Label ("You have successfuly logged in"); 
			Button closeButton = new Button ("Close");
			closeButton.setOnAction ( e1 -> {
				this.window.close(); 
				prevWindows.get(prevWindows.size()-1).window.show();
				prevWindows.remove(prevWindows.size()-1);
			});
			messageBox.close();
			VBox layout = new VBox (10); 
			layout.getChildren().addAll(label, closeButton); 
			layout.setAlignment(Pos.CENTER);
			
			Scene scene = new Scene(layout); 
			messageBox.setScene(scene);
			messageBox.showAndWait();
		}); 
		grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2,2,2,2))));
		Scene scene = new Scene(grid);
		window.setScene(scene);
		window.setTitle("Login");
		window.show();
	}
	

}
