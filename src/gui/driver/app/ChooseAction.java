package gui.driver.app;


import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.LoginManager;
import gui.map.DriverMap;  

public class ChooseAction extends AbstractWindow {
	Button buttonAbout;
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
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
		window.initModality(Modality.APPLICATION_MODAL);
	}

	public void display(Stage primaryStage, WindowEnum prevWindow) {
		// window = primaryStage;
		//System.out.println(System.getProperty("os.name"));
		if(!"Linux".equals(System.getProperty("os.name"))){
		//Turn on the music!
		final Task<Object> task = new Task<Object>() {
	        @Override
	        protected Object call() throws Exception {
	        	try{
	            int s = 100;
	            AudioClip audio = new AudioClip(getClass().getResource("sound.mp3").toExternalForm());
	            audio.setVolume(0.5f);
	            audio.setCycleCount(s);
	            audio.play();
	            return null;
	        	}
	        	catch(com.sun.media.jfxmedia.MediaException e){
	    			System.out.println("Oh Shiettttt2!");
	    		}
	        	return null;
	        }
	    };
	    Thread thread = new Thread(task);
	    thread.start();
		}
		
		
		String title = "What Would you like to do?";
		window.setTitle(title);
		window.setMinWidth(750);
		VBox vbox = new VBox(8); 
		vbox.setPadding(new Insets(10,10,10,10));
		
		//TODO: get a better way to check logged in
		welcomeLabel = new Label();
		try{
			welcomeLabel.setText("Welcome " + login.getUserName() + "!");
		}
		catch(Exception e){
			welcomeLabel.setText("Welcome! You are not logged in");
		}
		welcomeLabel.getStyleClass().add("label-welcome");

		
		buttonAbout = new Button("About");
		buttonAbout.setOnAction(e -> {
			window.close();
			ChooseAction.prevWindows.add(this);
			(new About()).display(primaryStage, WindowEnum.CHOOSE_ACTION);
		});

		buttonAbout.getStyleClass().add("button-menu");
		
		buttonLogin = new Button("Login");
		buttonLogin.setOnAction(e -> {
            window.close();
			Login login = new Login();
			ChooseAction.prevWindows.add(this);
			login.display(primaryStage, WindowEnum.CHOOSE_ACTION);
        }
	); 
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
			(new DriverMap("32.777789, 35.022054","32.761565, 35.019438")).display(primaryStage);
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
			if (prevWindow == WindowEnum.NONE && (new ConfirmBox()).display("Confirmation", "Are you sure you want to exit?"))
				window.close();
		});
		buttonClose.getStyleClass().add("button-menu");
		
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
		buttonLogOut.setDisable(true);
		buttonLogOut.getStyleClass().add("button-menu");
		
		vbox.getChildren().addAll( welcomeLabel, buttonAbout, buttonLogin, buttonRegister, buttonChooseDestination, buttonMap, 
				buttonClose, buttonLogOut, buttonMyDetails); 
		vbox.setAlignment(Pos.CENTER);

		Scene scene = new Scene(vbox);
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
