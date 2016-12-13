package gui.driver.shaharTesting;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	Button button;
	Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("JavaFX - Window Title");
		window.setOnCloseRequest(e -> closeProgram());
		
		
		button = new Button("Close Program");
		button.setOnAction(e -> {
			e.consume(); //Hey, I'm gonna take care of this event manually.
			closeProgram();});
		
		
		StackPane layout = new StackPane();
		layout.getChildren().add(button);
		Scene scene = new Scene(layout, 300, 150);
		window.setScene(scene);
		window.show();
	}
	
	private void closeProgram(){
		if (!ConfirmBox.display("Question", "Are you sure?"))
			System.out.println("Did Not Exit");
		else {
			System.out.println("Saving File...");
			window.close();
		}
	}
	
//	private void saveProgram(Stage primaryStage){
//		System.out.println("Saving details...");
//		primaryStage.close();
//		
//		
//	}
	

}




//Might be good for later use:
//Button clickMeButton, B2;
//
///*static public void main(String[] args){
//	launch(args);
//}*/
//
//@Override
//public void start(Stage primaryStage) throws Exception {
//	System.out.println("Starting...");
//	primaryStage.setTitle("Title");
//	clickMeButton = new Button();
//	clickMeButton.setText("click ME");
//	clickMeButton.setOnAction(new EventHandler<ActionEvent>() {
//		@Override
//		public void handle(ActionEvent __){
//			System.out.println("clickMeButton Clicked!");
//		}
//		
//	});
//	
//	B2 = new Button();
//	B2.setText("B2");
//	B2.setOnAction(e->System.out.println("B2 clicked!"));
//	
//	AnchorPane layout = new AnchorPane();
//	layout.getChildren().add(clickMeButton);
//	layout.getChildren().add(B2);
//	
//	Scene scene = new Scene(layout, 300, 100);
//	
//	primaryStage.setScene(scene);
//	primaryStage.show();





