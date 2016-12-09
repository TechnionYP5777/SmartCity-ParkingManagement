package gui.driver.shaharTesting;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	Button clickMeButton, B2;
	
	/*static public void main(String[] args){
		launch(args);
	}*/

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("Starting...");
		primaryStage.setTitle("Title");
		clickMeButton = new Button();
		clickMeButton.setText("click ME");
		clickMeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent __){
				System.out.println("clickMeButton Clicked!");
			}
			
		});
		
		B2 = new Button();
		B2.setText("B2");
		B2.setOnAction(e->System.out.println("B2 clicked!"));
		
		AnchorPane layout = new AnchorPane();
		layout.getChildren().add(clickMeButton);
		layout.getChildren().add(B2);
		
		Scene scene = new Scene(layout, 300, 100);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
//	private void saveProgram(Stage primaryStage){
//		System.out.println("Saving details...");
//		primaryStage.close();
//		
//		
//	}
	

}
