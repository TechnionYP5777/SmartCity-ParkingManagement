package gui.driver.app;

import java.util.Set;

import gui.map.DriverMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import logic.Navigation;
import org.parse4j.ParseException;

public class ChooseDestination extends AbstractWindow {

	ChooseDestination() {
		windowEnum = WindowEnum.CHOOSE_DESTINATION;
		window = new Stage();
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
	}

	public void display(final Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Choose Destination");
		final Label title = new Label();
		final DropShadow shadow = new DropShadow();
		shadow.setOffsetY(4.0);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
		title.setEffect(shadow);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setText("Navigate");
		title.setFont(Font.font(null, FontWeight.BOLD, 48));
		title.getStyleClass().add("label-title");
		final GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(15);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));

		final Set<String> locationsList = navigate.getLocations();

		GridPane.setConstraints(title, 0, 0);
		GridPane.setColumnSpan(title, 2);
		int currIdx = 1;
		final Label from = new Label("From:");
		final ChoiceBox<String> fromValue = new ChoiceBox<>();
		fromValue.getItems().addAll(locationsList);
		fromValue.setValue("Taub");
		fromValue.getStyleClass().add("cb");
		GridPane.setConstraints(fromValue, 1, currIdx);
		GridPane.setConstraints(from, 0, currIdx++);

		final Label to = new Label("To:");
		final ChoiceBox<String> toValue = new ChoiceBox<>();
		toValue.getItems().addAll(locationsList);
		toValue.setValue("Nosh Gate");
		toValue.getStyleClass().add("cb");
		GridPane.setConstraints(toValue, 1, currIdx);
		GridPane.setConstraints(to, 0, currIdx++);

		final Button buttonBack = new Button();
		buttonBack.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("back_button.png"))));

		final Button buttonGO = new Button();
		buttonGO.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("go_button.png"))));

		buttonBack.setOnAction(λ -> {
			window.close();
			AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1).window.show();
			AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size() - 1);

		});
		GridPane.setConstraints(buttonBack, 0, currIdx);
		// buttonBack.setDisable(true);

		buttonGO.setOnAction(e -> {
			// ************Add navigation functionality************//
			window.close();
			AbstractWindow.prevWindows.add(this);
			// (new DriverMap("", "")).display(window);

			// TILL GET FIXED!!!
			try {
				new DriverMap(navigate.getDestination(fromValue.getValue()).getEntrance(),
						Navigation.closestParkingSlot(login.getUser(),
								navigate.getDestination(fromValue.getValue()).getEntrance(), navigate.getAreas(),
								navigate.getDestination(toValue.getValue())).getLocation(),
						navigate.getDestination(toValue.getValue()).getEntrance()).display(window);
			} catch (final ParseException e1) {
				e1.printStackTrace();
			}
			// TILL HERE
		});
		GridPane.setConstraints(buttonGO, 1, currIdx++);
		grid.getChildren().addAll(title, from, fromValue, to, toValue, buttonBack, buttonGO);

		buttonGO.getStyleClass().add("button-go");
		buttonBack.getStyleClass().add("button-go");
		final Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());

		window.setScene(scene);
		window.show();
	}

}
