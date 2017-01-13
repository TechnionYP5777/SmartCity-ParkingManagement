/*
 * 
 * @author zahimizrahi
 * 
 */

package gui.driver.app;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class About extends AbstractWindow{
	
	private static final String TEXT =
            "Smart Parking system is a dynamic management system of parking slots within the Technion. On every parking slot there will \n" +
            "be placed a detection sensor that will update in real time the parking slot status. Note that a parking slot's status is defined as ,\n" +
            "a tuple of : color , location and taken/not taken.\n\n" +
            "Given the location and the customer details, the system will provide location of the nearest available parking slot and will \n" +
            "update in real time in case of nearer parking slot became available. The system will present a map of the Technion with \n" +
            "mapping of the parking slots and navigates the customer toward the chosen parking slot, in addition there will be a \n" +
            "possibility to communicate with other car owners.\n"; 
	
	public static final String IMAGE = "https://s23.postimg.org/ecvvaduyz/smart_parking.png";
	public About() {
		windowEnum = WindowEnum.ABOUT;
		window = new Stage(); 
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
	}
	
	public void display(Stage primaryStage, WindowEnum __) { 
		window = primaryStage;
		window.setTitle("About");
		 final Label label = new Label(TEXT);
	     label.setGraphic(new ImageView(new Image(IMAGE)));
	  // create 3D rotation transform on label.
	        Rotate rotate = new Rotate(-30, Rotate.Y_AXIS);
	        label.getTransforms().setAll(
	            rotate
	        );
	        VBox vbox = new VBox (10);
	        
	        Button backButton = new Button("Back");
			backButton.setOnAction(e -> {
				// move to editing my details
				this.window.close();
				AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size()-1).window.show();
				AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size()-1);
			});
	        
	        vbox.getChildren().addAll(label, backButton); 
	        Scene scene = new Scene(vbox);
			window.setScene(scene);
			window.setTitle("Login");
			window.show();
	}

}
