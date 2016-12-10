/**
 * 
 * @Author zahimizrahi
 * 
 */
package driver.zahiLearning;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

public class example extends Application {

	Stage window;
	Scene scene;
	Button button; 
	ListView<String> listView; 

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("choicebox example");
		
		listView = new ListView<> (); 
		listView.getItems().addAll ("Pizza", "Sushi", "Hamburger", "Fries");
		
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//Button
		button = new Button ("Click Me!"); 
		button.setOnAction(e -> buttonClicked());
	    VBox layout = new VBox (10); 
	    layout.setPadding(new Insets(20,20,20,20));
	    layout.getChildren().addAll(listView, button);
		scene = new Scene (layout, 300,250);
		
		window.setScene(scene);
		window.show();
	}
	
	private void buttonClicked () {
		String message = "";
		ObservableList<String> foods = listView.getSelectionModel().getSelectedItems(); 
		for (String food : foods)
			message += food + "\n";
		System.out.println(message);
		
	}

}
