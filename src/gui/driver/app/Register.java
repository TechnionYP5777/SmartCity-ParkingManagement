
/*
 * 
 * @author zahimizrahi
 * 
 */
package gui.driver.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Register extends AbstractWindow {

	public void display(Stage primaryStage, WindowEnum __) {
		window = primaryStage;
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);

		// title
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(4.0);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
		Label title = new Label();
		title.setEffect(shadow);
		title.setTextFill(Color.ROYALBLUE);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setText("Register");
		title.setFont(Font.font(null, FontWeight.BOLD, 48));

		// user
		Label user = new Label("Username");
		TextField nameInput = new TextField();
		nameInput.setPromptText("username");

		// password
		Label pass = new Label("Password");
		PasswordField passInput = new PasswordField();
		passInput.setPromptText("password");

		// phone number
		HBox hboxPhone = new HBox();
		Label phoneNumber = new Label("Phone Number");
		ChoiceBox<String> prefixNumber = new ChoiceBox<> (); 
		prefixNumber.getItems().addAll("050","052","053","054", "057");
		prefixNumber.setValue("050"); 
		TextField phoneNumberInput = new TextField();
		phoneNumberInput.setMaxWidth(95);
		//phoneNumberInput.setMaxWidth(50);
		passInput.setPromptText("phone number");
		hboxPhone.getChildren().addAll(prefixNumber,phoneNumberInput);
		
		// car number
		Label carNumber = new Label("Car Number");
		TextField carNumberInput = new TextField();
		passInput.setPromptText("car number");

		//email
		Label mail = new Label("E-Mail");
		TextField mailInput = new TextField();
		passInput.setPromptText("e-mail");
		
		
		
		
		
		Hyperlink wantLogin = new Hyperlink();
		wantLogin.setText("Are you a registered member? Sign In!");
		wantLogin.setOnAction(e -> {
			AbstractWindow.prevWindows.add(this);
			window.close();
			(new Login()).display(primaryStage, WindowEnum.SIGN_UP);
		});

		Button registerButton = new Button("Register");
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> {
			// move to editing my details
			this.window.close();
			AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1).window.show();
			AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size() - 1);
		});
		HBox hbox = new HBox();

		GridPane.setConstraints(title, 1, 0);
		GridPane.setColumnSpan(title, 3);
		GridPane.setConstraints(user, 0, 1);
		GridPane.setConstraints(nameInput, 1, 1);
		GridPane.setConstraints(pass, 0, 2);
		GridPane.setConstraints(passInput, 1, 2);
		GridPane.setConstraints(hboxPhone, 0, 3); 
		GridPane.setConstraints(phoneNumber, 0, 3);
		GridPane.setConstraints(hboxPhone, 1, 3); 
		GridPane.setColumnSpan(hboxPhone, 2);
//		GridPane.setConstraints(prefixNumber, 1, 3);
//		GridPane.setConstraints(phoneNumberInput, 2, 3);
		GridPane.setConstraints(carNumber, 0, 4);
		GridPane.setConstraints(carNumberInput, 1, 4);
		GridPane.setConstraints(mail, 0, 5);
		GridPane.setConstraints(mailInput, 1, 5);
		// GridPane.setConstraints(loginButton, 1, 3);
		// GridPane.setConstraints(backButton,2,3);
		GridPane.setConstraints(hbox, 1, 6);
		GridPane.setConstraints(wantLogin, 1, 7);
		GridPane.setColumnSpan(wantLogin, 2);

		hbox.getChildren().addAll(registerButton, backButton);
		grid.getChildren().addAll(title, user, nameInput, pass, passInput,
		phoneNumber, hboxPhone, carNumber, carNumberInput, mail, mailInput, 
		hbox, wantLogin);
		registerButton.setOnAction(e -> {
			AlertBox.display("Sign Up", "You Successfully Signed Up!");
			AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1).window.show();
			AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size() - 1);
			});
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));
		Scene scene = new Scene(grid, 400, 350);
		window.setScene(scene);
		window.setTitle("Register");
		window.show();
	}

}