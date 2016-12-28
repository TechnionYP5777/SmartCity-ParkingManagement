package gui.driver.app;

import java.util.ArrayList;

import Exceptions.LoginException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyDetailsEdit extends AbstractWindow {

	public MyDetailsEdit() {
		windowEnum = WindowEnum.MY_DETAILS_EDIT;
		window = new Stage();
	}

	public void display(Stage primaryStage, WindowEnum prevWindow, final ArrayList<Label> labels, final ArrayList<Label> values) {
		window = primaryStage;
		window.setTitle("Edit My Details");
		GridPane grid = new GridPane();
		System.out.println("MDE begin. prevWindows:				" + prevWindows);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));

		ArrayList<TextField> newValues = new ArrayList<TextField>();
		int i = 0;
		int stickerIdx=0;
		for (; i < labels.size(); i++) {
			if(!labels.get(i).getText().equals("Sticker Color:")){
				newValues.add(new TextField(values.get(i).getText()));
				GridPane.setConstraints(labels.get(i), 0, i);
				GridPane.setConstraints(newValues.get(i), 1, i);
				grid.getChildren().addAll(labels.get(i), newValues.get(i));
			}
			else stickerIdx = i;
		}
		
		Label sticker = new Label("Sticker Color:");
		ChoiceBox<String> stickerColor = new ChoiceBox<>();
		stickerColor.getItems().addAll("Blue", "Green", "White", "Red", "Bordeaux", "Yellow");
		stickerColor.setValue("Blue");
		stickerColor.getValue();
		GridPane.setConstraints(stickerColor, 1, stickerIdx);
		GridPane.setConstraints(sticker, 0, stickerIdx);
		grid.getChildren().add(sticker);
		grid.getChildren().add(stickerColor);
		
		Button doneButton = new Button();
		doneButton.setText("Done");
		doneButton.setOnAction(e -> {
			// Save edits
			if (checkChangesLegality(newValues)) {
				ArrayList<Label> correctedValues = new ArrayList<Label>();
				for (int j = 0; j < newValues.size(); j++) {
					correctedValues.add(new Label(newValues.get(j).getText()));
				}
				correctedValues.add(4, new Label(stickerColor.getValue()));
				
				/*David Edit*/
				try {
					login.userUpdate(login.getCarNumber(), newValues.get(1).getText(), null, newValues.get(0).getText(), newValues.get(2).getText());
				} catch (LoginException e1) {
					// TODO check what to do in case of error
				}
				/*Done*/
				
				// You can only get here if the last prevWindows is
				// 'MyDetails'!!
				MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
				AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
				MD.display(primaryStage, prevWindow, labels, correctedValues);
			}
		});
		GridPane.setConstraints(doneButton, 0, i);

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> {
			// move to editing my details
			System.out.println("MDE back begin. prevWindows:			" + AbstractWindow.prevWindows);
			this.window.close();
			System.out.println(AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1));
			MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
			AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
			MD.display(primaryStage, prevWindow, labels, values);
			//System.out.println("MDE back end. prevWindows:			" + AbstractWindow.prevWindows);

		});
		GridPane.setConstraints(backButton, 1, i);

		grid.getChildren().addAll(doneButton, backButton);
		Scene scene = new Scene(grid, 300, 250);
		window.setScene(scene);
		window.show();

	}

	public static boolean checkChangesLegality(ArrayList<TextField> newValues) {

		return true;
	}
}
