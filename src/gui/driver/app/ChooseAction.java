package gui.driver.app;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseAction extends AbstractWindow {

	public ChooseAction() {
		windowEnum = WindowEnum.CHOOSE_ACTION;
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
	}

	public void display(Stage primaryStage, WindowEnum prevWindow) {
		String title = "Next Action";
		String message = "What Would you like to do?";

		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(100);
		layout.setPadding(new Insets(10, 10, 10, 10));

		// window = new Stage();
		// window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);

		Label label = new Label();
		label.setText(message);
		GridPane.setConstraints(label, 0, 0);

		// Button buttonGetPass = new Button("Get Password");
		// buttonGetPass.setOnAction(e-> {
		// window.close();
		// GetPassByMail GPBM = new GetPassByMail();
		// prevWindows.add(this);
		// GPBM.display(primaryStage, WindowEnum.CHOOSE_ACTION, prevWindows);
		//
		// });
		// GridPane.setConstraints(buttonGetPass, 0, 1);
		Button buttonNavigate = new Button ("Choose Destination");
		GridPane.setConstraints(buttonNavigate, 0, 1); 

		Button buttonLogin = new Button("Login");
		buttonLogin.setOnAction(e -> {
			window.close();
			Login login = new Login();
			ChooseAction.prevWindows.add(this);
			login.display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});
		GridPane.setConstraints(buttonLogin, 1, 1);
		
		Button buttonRegister = new Button("Register");
		buttonRegister.setOnAction(e -> {
			window.close();
			ChooseAction.prevWindows.add(this);
			(new Register()).display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});
		GridPane.setConstraints(buttonRegister, 2, 1);

		Button buttonMyDetails = new Button("My Details");
		buttonMyDetails.setOnAction(e -> {
			window.close();
			MyDetails MD = new MyDetails();
			ChooseAction.prevWindows.add(this);
			MD.display(primaryStage, WindowEnum.CHOOSE_ACTION, null, null);

		});
		GridPane.setConstraints(buttonMyDetails,3, 1);

		Button buttonClose = new Button("Close Program");
		buttonClose.setOnAction(e -> {
			if (prevWindow == WindowEnum.NONE && ConfirmBox.display("Confirmation", "Are you sure you want to exit?"))
				window.close();
		});
		GridPane.setConstraints(buttonClose, 4, 1);

		layout.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));
		layout.getChildren().addAll(label, buttonNavigate, buttonLogin, buttonRegister, buttonMyDetails, buttonClose);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 500, 300);
		window.setScene(scene);
		window.showAndWait();
	}

}
