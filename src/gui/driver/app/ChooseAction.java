package gui.driver.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
	public Button buttonAbout;
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

	public void display(final Stage primaryStage, final WindowEnum prevWindow) {
		window.setTitle("What Would you like to do?");
		window.setMinWidth(750);
		final VBox vbox = new VBox(8);
		vbox.setPadding(new Insets(10, 10, 10, 10));
		isLinuxOS = "Linux".equals(System.getProperty("os.name"));
		if (!isLinuxOS) {
			// System.out.println("Sound the music!");

			final URL resource = getClass().getResource("sound.mp3");
			mediaPlayer = new MediaPlayer(new Media(resource + ""));
			mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
			mediaPlayer.play();
			buttonMute = new Button();
			buttonMute.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("unmute_button.png"))));
			buttonMute.getStyleClass().add("button-go");
			// buttonMute.setDisable(false);
			// buttonMute.getStyleClass().clear();

			buttonMute.setOnAction(λ -> StaticMethods.dealWithMute(mediaPlayer, AbstractWindow.muteButtonsAL));
			muteButtonsAL.add(buttonMute);
			// buttonMute.getStyleClass().add("button-muteOFF");
		}

		// TODO: get a better way to check logged in
		final HBox mute = new HBox(1);
		welcomeLabel = new Label();
		try {
			welcomeLabel.setText("Welcome " + login.getUserName() + "!");
		} catch (final Exception e) {
			welcomeLabel.setText("Welcome! You are not logged in");
		}
		welcomeLabel.getStyleClass().add("label-welcome");

		buttonAbout = new Button("About");
		buttonAbout.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("about_button.png"))));
		buttonAbout.getStyleClass().add("button-go");
		buttonAbout.setOnAction(λ -> {
			window.close();
			prevWindows.add(this);
			new About().display(primaryStage, WindowEnum.CHOOSE_ACTION, buttonMute);
		});
		// buttonAbout.getStyleClass().add("button-menu");

		buttonLogin = new Button("Login");
		buttonLogin.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("login_button.png"))));
		buttonLogin.getStyleClass().add("button-go");
		buttonLogin.setOnAction(e -> {
			window.close();
			final Login login = new Login();
			AbstractWindow.prevWindows.add(this);
			login.display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});
		// buttonLogin.getStyleClass().add("button-menu");

		buttonRegister = new Button("Register");
		buttonRegister.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("register_button.png"))));
		buttonRegister.getStyleClass().add("button-go");
		buttonRegister.setOnAction(λ -> {
			window.close();
			AbstractWindow.prevWindows.add(this);
			new Register().display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});

		// buttonRegister.getStyleClass().add("button-menu");

		buttonMap = new Button("View Map");
		buttonMap.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("map_button.png"))));
		buttonMap.getStyleClass().add("button-go");
		buttonMap.setOnAction(λ -> {
			window.close();
			AbstractWindow.prevWindows.add(this);
			new DriverMap("32.777789, 35.022054", "32.761565, 35.019438").display(primaryStage);
		});

		// buttonMap.getStyleClass().add("button-menu");

		buttonMyDetails = new Button("My Details");
		buttonMyDetails.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("details_button.png"))));
		buttonMyDetails.getStyleClass().add("button-go");
		buttonMyDetails.setOnAction(e -> {
			window.close();
			final MyDetails MD = new MyDetails();
			AbstractWindow.prevWindows.add(this);
			MD.display(primaryStage, WindowEnum.CHOOSE_ACTION, null, null);

		});
		buttonMyDetails.setDisable(true);
		// buttonMyDetails.getStyleClass().add("button-menu");

		buttonChooseDestination = new Button("Choose Destination");
		buttonChooseDestination
				.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("choose_destination_button.png"))));
		buttonChooseDestination.getStyleClass().add("button-go");
		buttonChooseDestination.setOnAction(e -> {
			window.close();
			final ChooseDestination CD = new ChooseDestination();
			AbstractWindow.prevWindows.add(this);
			CD.display(primaryStage);

		});
		buttonChooseDestination.setDisable(true);
		// buttonChooseDestination.getStyleClass().add("button-menu");

		final Button buttonClose = new Button("Exit");
		buttonClose.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("exit_button.png"))));
		buttonClose.getStyleClass().add("button-go");
		buttonClose.setOnAction(λ -> {
			if (prevWindow == WindowEnum.NONE
					&& new ConfirmBox().display("Confirmation", "Are you sure you want to exit?"))
				window.close();
		});
		// buttonClose.getStyleClass().add("button-menu");

		buttonLogOut = new Button("Log Out");
		buttonLogOut.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("logout_button.png"))));
		buttonLogOut.getStyleClass().add("button-go");
		buttonLogOut.setOnAction(λ -> {
			if (prevWindow == WindowEnum.NONE
					&& new ConfirmBox().display("Confirmation", "Are you sure you want to log out?")) {
				welcomeLabel.setText("Welcome. You are not logged in");
				login = new LoginManager();
				setButtonsDefaultValues();
				// window.close();
			}
		});
		buttonLogOut.setDisable(true);
		// buttonLogOut.getStyleClass().add("button-menu");

		if (!isLinuxOS)
			mute.getChildren().add(buttonMute);
		mute.setAlignment(Pos.TOP_RIGHT);

		final HBox hbox1 = new HBox(5), hbox2 = new HBox(5);
		hbox1.getChildren().addAll(buttonAbout, buttonLogin, buttonRegister, buttonChooseDestination);
		hbox2.getChildren().addAll(buttonMap, buttonMyDetails, buttonLogOut, buttonClose);
		vbox.getChildren().addAll(mute, welcomeLabel, hbox1, hbox2);
		vbox.setAlignment(Pos.CENTER);
		final Scene scene = new Scene(vbox);
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
