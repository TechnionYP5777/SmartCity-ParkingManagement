/*
 * 
 * @author zahimizrahi
 * 
 */

package gui.driver.app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class About extends AbstractWindow {

	private static final String TEXT = "Smart Parking system is a dynamic management system of parking slots within the Technion. On every parking \n"
			+ "slot there will be placed a detection sensor that will update in real time the parking slot status. Note that \n"
			+ "a parking slot's status is defined as a tuple of : color , location and taken/not taken.\n\n"
			+ "Given the location and the customer details, the system will provide location of the nearest available parking slot \n"
			+ "and will update in real time in case of nearer parking slot became available. The system will present \n"
			+ "a map of the Technion withmapping of the parking slots and navigates the customer toward the chosen parking slot \n"
			+ "in addition there will be a possibility to communicate with other car owners. \n\n"
			+ "This project is made under the course 'Yearly Project in Software' in Technion Institute of Technology \n"
			+ "by the skilled students: David Cohen, Zahi Mizrahi, Or Troyaner, Shahar Yair, Inbal Matityahu \n"
			+ "Dani Shames, Tom Nof, Sefi Albo and Shay Segal.\n";

	public static final String IMAGE = "https://s23.postimg.org/ecvvaduyz/smart_parking.png";

	public About() {
		windowEnum = WindowEnum.ABOUT;
		window = new Stage();
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
	}

	public void display(Stage primaryStage, WindowEnum __, Button buttonMute) {
		window = primaryStage;
		window.setTitle("About");
		window.setWidth(1350);
		window.setHeight(450);
		final Label label = new Label(TEXT);
		label.setAlignment(Pos.CENTER);
		label.setGraphic(new ImageView(new Image(IMAGE)));
		label.setStyle("-fx-background-color: white;\n" + "-fx-text-fill: black;\n" + "-fx-background-radius: 10px;\n"
				+ "-fx-padding: 10px;\n" + "-fx-graphic-text-gap: 10px;\n" + "-fx-font-family: 'Arial';\n"
				+ "-fx-font-size: 18px;");
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-background-color: null; -fx-padding: 10px;");
		// System.out.println("HERE IS BUTTONMUTE: "+ buttonMute.toString());
		
		if (!isLinuxOS) {
			Button AboutMute = StaticMethods.cloneButton(buttonMute);
			muteButtonsAL.add(AboutMute);
			vbox.getChildren().add(AboutMute);
		}
		
		Button backButton = new Button();
		backButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("back_button.png"))));
		backButton.getStyleClass().add("button-go");
		backButton.setOnAction(e -> {
			this.window.close();
//			if (!isLinuxOS)
//				StaticMethods.dealWithMute(mediaPlayer, buttonMute, true);
			prevWindows.get(prevWindows.size() - 1).window.show();
			prevWindows.remove(prevWindows.size() - 1);
		});
		vbox.getChildren().addAll(label, backButton);
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox);
		window.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.show();
	}
}
