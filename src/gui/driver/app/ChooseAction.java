package gui.driver.app;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseAction extends AbstractWindow {
	
	public void display(Stage primaryStage, WindowEnum prevWindow, ArrayList<AbstractWindow> prevWindows){
		String title  = "Next Action";
		String message = "What Would you like to do?";
		
		GridPane layout = new GridPane();
		layout.setHgap(10);
		layout.setVgap(10);
		layout.setPadding(new Insets(10,10,10,10));
		
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		
		Label label = new Label();
		label.setText(message);
		GridPane.setConstraints(label, 0, 0);
		
		Button buttonGetPass = new Button("Get Password");
		buttonGetPass.setOnAction(e-> {
			window.close();
			GetPassByMail GPBM = new GetPassByMail();
			prevWindows.add(this);
			GPBM.display(primaryStage, WindowEnum.CHOOSE_ACTION, prevWindows);
			
		});
		GridPane.setConstraints(buttonGetPass, 0, 1);
		
		Button buttonClose = new Button("Close Program");
		buttonClose.setOnAction(e-> {
			if(prevWindow == WindowEnum.NONE && ConfirmBox.display("Confirmation", "Are you sure you want to exit?"))
				window.close();	
		});
		GridPane.setConstraints(buttonClose, 1, 1);
		
		Button buttonMyDetails = new Button("My Details");
		buttonMyDetails.setOnAction(e-> {
			window.close();
			MyDetails MD = new MyDetails();
			prevWindows.add(this);
			MD.display(primaryStage, WindowEnum.CHOOSE_ACTION, null, null, prevWindows);
			
		});
		GridPane.setConstraints(buttonMyDetails, 2, 1);
		
		
		
		Button buttonLogin = new Button ("Login");
		buttonLogin.setOnAction( e -> {
			window.close(); 
			Login login = new Login();
			prevWindows.add(this);
			login.display(primaryStage, WindowEnum.LOG_IN);
		});
		GridPane.setConstraints(buttonLogin, 3, 1); 
		
		layout.getChildren().addAll(label, buttonLogin, buttonMyDetails, buttonGetPass, buttonClose);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	

}
