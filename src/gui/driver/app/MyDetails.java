package gui.driver.app;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyDetails extends AbstractWindow {

	public MyDetails() {
		windowEnum = WindowEnum.MY_DETAILS;
		window = new Stage();
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
	}

	public void display(Stage primaryStage, WindowEnum prevWindow, final ArrayList<Label> newLabels,
			final ArrayList<Label> newValues) {
		final int lablesNum = 6;
		// window = primaryStage;
		window.setTitle("My Details");
		window.setWidth(350);
		window.setHeight(320);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));

		Button editDetailsButton = new Button();
		editDetailsButton.setText("Edit");
		int buttonIndex;
		ArrayList<Label> labels, values;
		if (newLabels != null) {
			labels = newLabels;
			values = newValues;
			for (int ¢ = 0; ¢ < values.size(); ++¢) {
				// System.out.println(i + " labels: " + labels.get(i) + "values:
				// " + values.get(i));
				GridPane.setConstraints(labels.get(¢), 0, ¢);
				GridPane.setConstraints(values.get(¢), 1, ¢);
				// grid.getChildren().addAll(labels.get(i), values.get(i));
			}
			if (values.size() < lablesNum)
				values.add(new Label(login.getUser().getLastLoginTime() + ""));
		} else {
			labels = new ArrayList<Label>();
			values = new ArrayList<Label>();

			Label eMail = new Label(login.getEmail());
			labels.add(new Label("eMail:"));
			values.add(eMail);

			Label username = new Label(login.getUserName());
			labels.add(new Label("Username:"));
			values.add(username);

			Label carNumber = new Label(login.getCarNumber());
			labels.add(new Label("Car Number:"));
			values.add(carNumber);

			Label phoneNumber = new Label(login.getPhoneNumber());
			labels.add(new Label("Phone Number:"));
			values.add(phoneNumber);

			Label sticker = new Label(StaticMethods.getStickerClolorFromEnum(login.getSticker()));
			labels.add(new Label("Sticker Color:"));
			values.add(sticker);

			Label lastLogin = new Label(login.getUser().getLastLoginTime() + "");
			labels.add(new Label("Last Login:"));
			values.add(lastLogin);

		}
		// System.out.println("MD size: " + values.size() + " : " + values);
		int i = 0;
		for (; i < values.size(); ++i) {
			// System.out.println(i + " labels: " + labels.get(i) + "values: " +
			// values.get(i));
			GridPane.setConstraints(labels.get(i), 0, i);
			GridPane.setConstraints(values.get(i), 1, i);
			grid.getChildren().addAll(labels.get(i), values.get(i));
		}
		buttonIndex = i;
		editDetailsButton.setOnAction(e -> {
			// move to editing my details
			MyDetailsEdit MDE = new MyDetailsEdit();
			AbstractWindow.prevWindows.add(this);
			window.close();
			MDE.display(primaryStage, prevWindow, labels, values);

		});

		// TODO: finish it
		Button backButton = new Button();
		backButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("back_button.png"))));
		backButton.getStyleClass().add("button-go");

		backButton.setOnAction(λ -> {
			// move to editing my details
			this.window.close();
			AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1).window.show();
			AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size() - 1);
		});
		GridPane.setConstraints(backButton, 1, buttonIndex);

		GridPane.setConstraints(editDetailsButton, 0, buttonIndex);

		grid.getChildren().addAll(editDetailsButton, backButton);
		Scene scene = new Scene(grid);
		window.setScene(scene);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.show();

	}

}
