package gui.driver.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.LoginManager;

import java.net.URL;
import java.util.ArrayList;

import gui.map.DriverMap;

public class ChooseAction extends AbstractWindow {
	public
	Button buttonAbout;
	Button buttonLogin;
	Button buttonMyDetails;
	Button buttonRegister;
	Button buttonChooseDestination;
	Button buttonMap;
	Button buttonLogOut;
	Button buttonMute;
	Label welcomeLabel;

	public ChooseAction() {
		windowEnum = WindowEnum.CHOOSE_ACTION;
		muteButtonsAL = new ArrayList<Button>();
		window = new Stage();
		buttonMute = new Button();
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
		window.initModality(Modality.APPLICATION_MODAL);
	}

	public void display(Stage primaryStage, WindowEnum prevWindow) {
		// window = primaryStage;
		String title = "What Would you like to do?";
		window.setTitle(title);
		window.setMinWidth(750);
		VBox vbox = new VBox(8);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		isLinuxOS = "Linux".equals(System.getProperty("os.name"));
		if (!isLinuxOS) {
			//System.out.println("Sound the music!");
			
			URL resource = getClass().getResource("sound.mp3");
			mediaPlayer = new MediaPlayer(new Media(resource + ""));
			mediaPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					mediaPlayer.seek(Duration.ZERO);
				}
			});
			mediaPlayer.play();
			
			buttonMute = new Button(); 
			ImageView ivBack= new ImageView(new Image(getClass().getResourceAsStream("unmute_button.png")));
			buttonMute.setGraphic(ivBack);
			buttonMute.getStyleClass().add("button-go");
			//buttonMute.setDisable(false);
			//buttonMute.getStyleClass().clear();
			
			buttonMute.setOnAction(e -> {
				StaticMethods.dealWithMute(mediaPlayer, AbstractWindow.muteButtonsAL);
			});
			muteButtonsAL.add(buttonMute);
			//buttonMute.getStyleClass().add("button-muteOFF");
		}
		

		// TODO: get a better way to check logged in
		welcomeLabel = new Label();
		try {
			welcomeLabel.setText("Welcome " + login.getUserName() + "!");
		} catch (Exception e) {
			welcomeLabel.setText("Welcome! You are not logged in");
		}
		welcomeLabel.getStyleClass().add("label-welcome");


		buttonAbout = new Button("About");
		buttonAbout.setOnAction(e -> {
			window.close();
			prevWindows.add(this);
			(new About()).display(primaryStage, WindowEnum.CHOOSE_ACTION, buttonMute);
		});
		buttonAbout.getStyleClass().add("button-menu");

		buttonLogin = new Button("Login");
		buttonLogin.setOnAction(e -> {
			window.close();
			Login login = new Login();
			ChooseAction.prevWindows.add(this);
			login.display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});
		buttonLogin.getStyleClass().add("button-menu");

		buttonRegister = new Button("Register");
		buttonRegister.setOnAction(e -> {
			window.close();
			ChooseAction.prevWindows.add(this);
			(new Register()).display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});

		buttonRegister.getStyleClass().add("button-menu");

		buttonMap = new Button("View Map");
		buttonMap.setOnAction(e -> {
			window.close();
			ChooseAction.prevWindows.add(this);
			(new DriverMap("32.777789, 35.022054", "32.761565, 35.019438")).display(primaryStage);
		});

		buttonMap.getStyleClass().add("button-menu");

		buttonMyDetails = new Button("My Details");
		buttonMyDetails.setOnAction(e -> {
			window.close();
			MyDetails MD = new MyDetails();
			ChooseAction.prevWindows.add(this);
			MD.display(primaryStage, WindowEnum.CHOOSE_ACTION, null, null);

		});
		buttonMyDetails.setDisable(true);
		buttonMyDetails.getStyleClass().add("button-menu");

		buttonChooseDestination = new Button("Choose Destination");
		buttonChooseDestination.setOnAction(e -> {
			window.close();
			ChooseDestination CD = new ChooseDestination();
			ChooseAction.prevWindows.add(this);
			CD.display(primaryStage);

		});
		buttonChooseDestination.setDisable(true);
		buttonChooseDestination.getStyleClass().add("button-menu");

		Button buttonClose = new Button("Close Program");
		buttonClose.setOnAction(e -> {
			if (prevWindow == WindowEnum.NONE
					&& (new ConfirmBox()).display("Confirmation", "Are you sure you want to exit?"))
				window.close();
		});
		buttonClose.getStyleClass().add("button-menu");

		buttonLogOut = new Button("Log Out");
		buttonLogOut.setOnAction(e -> {
			if (prevWindow == WindowEnum.NONE
					&& (new ConfirmBox()).display("Confirmation", "Are you sure you want to log out?")) {
				welcomeLabel.setText("Welcome. You are not logged in");
				login = new LoginManager();
				setButtonsDefaultValues();
				// window.close();
			}
		});
		buttonLogOut.setDisable(true);
		buttonLogOut.getStyleClass().add("button-menu");

		vbox.getChildren().addAll(welcomeLabel, buttonAbout, buttonLogin, buttonRegister, buttonChooseDestination,
				buttonMap, buttonClose, buttonLogOut, buttonMyDetails);
		if (!isLinuxOS)
			vbox.getChildren().add(buttonMute);
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();
	}

	public void setButtonsDefaultValues() {
		buttonLogin.setDisable(false);
		buttonMute.setDisable(false);
		buttonMyDetails.setDisable(true);
		buttonRegister.setDisable(false);
		buttonChooseDestination.setDisable(true);
		buttonMap.setDisable(false);
		buttonLogOut.setDisable(true);
	}

}
