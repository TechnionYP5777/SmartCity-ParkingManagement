package gui.driver.shaharTesting;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	}

	public void display(final Stage primaryStage, final WindowEnum prevWindow, final ArrayList<Label> ls, final ArrayList<Label> values,
			final ArrayList<AbstractWindow> prevWindows) {
		window = primaryStage;
		window.setTitle("Edit My Details");
		final GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));

		final ArrayList<TextField> newValues = new ArrayList<TextField>();
		int i = 0;
		for (; i < ls.size(); ++i) {
			newValues.add(new TextField(values.get(i).getText()));
			GridPane.setConstraints(ls.get(i), 0, i);
			GridPane.setConstraints(newValues.get(i), 1, i);
			grid.getChildren().addAll(ls.get(i), newValues.get(i));
		}

		final Button doneButton = new Button();
		doneButton.setText("Done");
		doneButton.setOnAction(e -> {
			// Save edits
			if (checkChangesLegality(newValues)) {
				final ArrayList<Label> correctedValues = new ArrayList<Label>();
				for (int j = 0; j < newValues.size(); ++j)
					correctedValues.add(new Label(newValues.get(j).getText()));
				// You can only get here id the last prevWindows is
				// 'MyDetails'!!
				final MyDetails MD = (MyDetails) prevWindows.get(prevWindows.size() - 1);
				prevWindows.remove(prevWindows.size() - 1);
				MD.display(primaryStage, prevWindow, ls, correctedValues, prevWindows);
			}
		});

		GridPane.setConstraints(doneButton, 0, i);

		grid.getChildren().add(doneButton);
		window.setScene(new Scene(grid, 300, 150));
		window.show();

	}

	public static boolean checkChangesLegality(final ArrayList<TextField> newValues) {

		return true;
	}
}
