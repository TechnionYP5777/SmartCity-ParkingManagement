package gui.driver.shaharTesting;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChooseAction {
	
static Stage window;
	
	public static void display(Stage primaryStage, WindowEnum prevWindow){
		String title  = "Next Action";
		String message = "What would you like to do next?";
		
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
		
		Button button1 = new Button("Get Password");
		button1.setOnAction(e-> {
			window.close();
			GetPassByMail.display(primaryStage, WindowEnum.CHOOSE_ACTION);
			
		});
		GridPane.setConstraints(button1, 0, 1);
		
		Button button2 = new Button("Close Program");
		button2.setOnAction(e-> {
			if(prevWindow == WindowEnum.NONE){
				if(ConfirmBox.display("Confirmation", "Are you sure you want to exit?"))
					window.close();
			}	
		});
		GridPane.setConstraints(button2, 1, 1);
		
		
		layout.getChildren().addAll(label, button2, button1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	

}
