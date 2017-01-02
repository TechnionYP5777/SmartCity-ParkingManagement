package gui.driver.app;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.LoginManager;
import gui.map.PmMap;  

public class ChooseAction extends AbstractWindow {
	Button buttonLogin;
	Button buttonMyDetails;
	Button buttonRegister;
	Button buttonChooseDestination;
	Button buttonMap;
	Button buttonLogOut;
	Label welcomeLabel;
	public ChooseAction() {
		windowEnum = WindowEnum.CHOOSE_ACTION;
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
	}

	public void display(Stage primaryStage, WindowEnum prevWindow) {
		String title = "What Would you like to do?";

		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(20);
		layout.setPadding(new Insets(10, 10, 10, 10));

		// window = new Stage();
		// window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		//window.setMinWidth(750);
		
		
		
		//TODO: get a better way to check logged in
		welcomeLabel = new Label();
		try{
			welcomeLabel.setText("Welcome " + login.getUserName() + "!");
		}
		catch(Exception e){
			welcomeLabel.setText("Welcome! You are not logged in");
		}
		
		GridPane.setConstraints(welcomeLabel, 0, 0);
		GridPane.setColumnSpan(welcomeLabel, 4);
		
		int buttonIdx = 0;
		buttonLogin = new Button("Login");
		buttonLogin.setOnAction(e -> {
			window.close();
			Login login = new Login();
			ChooseAction.prevWindows.add(this);
			login.display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});
		GridPane.setConstraints(buttonLogin, buttonIdx++, 1);
		
		buttonRegister = new Button("Register");
		buttonRegister.setOnAction(e -> {
			window.close();
			ChooseAction.prevWindows.add(this);
			(new Register()).display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});
		GridPane.setConstraints(buttonRegister, buttonIdx++, 1);

		
		buttonMap = new Button("View Map");
		buttonMap.setOnAction(e -> {
			window.close();
			ChooseAction.prevWindows.add(this);
			(new PmMap()).display(primaryStage);
		});
		GridPane.setConstraints(buttonMap, buttonIdx++, 1);
		
		
		buttonMyDetails = new Button("My Details");
		buttonMyDetails.setOnAction(e -> {
			window.close();
			MyDetails MD = new MyDetails();
			ChooseAction.prevWindows.add(this);
			MD.display(primaryStage, WindowEnum.CHOOSE_ACTION, null, null);

		});
		GridPane.setConstraints(buttonMyDetails,buttonIdx++, 1);
		buttonMyDetails.setDisable(true);
		
		buttonChooseDestination = new Button("Choose Destination");
		buttonChooseDestination.setOnAction(e -> {
			window.close();
			ChooseDestination CD = new ChooseDestination();
			ChooseAction.prevWindows.add(this);
			CD.display(primaryStage);

		});
		GridPane.setConstraints(buttonChooseDestination,buttonIdx++, 1);
		buttonChooseDestination.setDisable(true);
		
		Button buttonClose = new Button("Close Program");
		buttonClose.setOnAction(e -> {
			if (prevWindow == WindowEnum.NONE && (new ConfirmBox()).display("Confirmation", "Are you sure you want to exit?"))
				window.close();
		});
		GridPane.setConstraints(buttonClose, buttonIdx++, 1);

		buttonLogOut = new Button("Log Out");
		buttonLogOut.setOnAction(e -> {
			if (prevWindow == WindowEnum.NONE && (new ConfirmBox()).display("Confirmation", "Are you sure you want to log out?"))
			{
				welcomeLabel.setText("Welcome. You are not logged in");
				login = new LoginManager();
				setButtonsDefaultValues();
				//window.close();
			}
		});
		GridPane.setConstraints(buttonLogOut, buttonIdx++, 1);
		buttonLogOut.setDisable(true);
		
		layout.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));
		layout.getChildren().addAll( buttonChooseDestination, buttonLogin, buttonRegister, buttonMap,
				buttonMyDetails, buttonClose, buttonLogOut, welcomeLabel);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}
	
	public void setButtonsDefaultValues(){
		buttonLogin.setDisable(false);
		buttonMyDetails.setDisable(true);
		buttonRegister.setDisable(false);
		buttonChooseDestination.setDisable(true);
		buttonMap.setDisable(false);
		buttonLogOut.setDisable(true);
	}

}
