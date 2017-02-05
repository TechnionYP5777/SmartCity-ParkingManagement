package gui.driver.shaharTesting;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GetPassByMail extends AbstractWindow {

	public GetPassByMail() {
		windowEnum = WindowEnum.GET_PASS_BY_MAIL;
	}

	public void display(Stage primaryStage, WindowEnum prevWindow, ArrayList<AbstractWindow> prevWindows) {
		window = primaryStage;
		window.setTitle("Get Password By Email");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));

		Label instruction = new Label("Please enter your eMail address in order to get your password");
		GridPane.setConstraints(instruction, 0, 0);

		TextField eMailInput = new TextField();
		String defaultMail = "user@gmail.com";
		eMailInput.setText(defaultMail);
		GridPane.setConstraints(eMailInput, 0, 1);

		Button sendButton = new Button();
		sendButton.setText("Send Mail");

		sendButton.setOnAction(e -> {
			if (eMailInput.getText().equals(defaultMail))
				AlertBox.display("Bad Input", "The mail you entered is the default! " + "\nPlease try again.");
			else if (!isValidMailAddress(eMailInput))
				AlertBox.display("Bad Input", "Illegal address entered! " + "\nPlease try again.");
			else {
				try {
					sendPassword(eMailInput);
				} catch (Exception eMailException) {
					System.out.println(e);
				}
				AlertBox.display("Password Sent", "The password was sent to your eMail account");
			}

		});
		GridPane.setConstraints(sendButton, 0, 2);

		grid.getChildren().addAll(instruction, eMailInput, sendButton);
		window.setScene(new Scene(grid, 420, 150));
		window.show();

	}

	public static boolean isValidMailAddress(TextField eMailInput) {
		// A regular expression, translated into code by regex101.com
		return eMailInput.getText()
				.matches("[\\d\\w\\.]+@(campus|gmail|walla|hotmail|t2)(\\.(technion|ac|il|net|com)+)+");
	}

	public static void sendPassword(TextField eMailInput) {
		// Send password to the mail entered, display error message if there is
		// a problem.

	}

}
