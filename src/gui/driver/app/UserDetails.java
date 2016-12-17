package gui.driver.app;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserDetails {
	
	static Stage window;
	public static void display(Stage primaryStage){
		
		window = primaryStage;
		window.setTitle("Get Password By Email");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,2));
		grid.setVgap(8);
		grid.setHgap(10);
		
		
		
	}
	
	
	

}
