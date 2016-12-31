package gui.driver.app;

import java.util.ArrayList;

import Exceptions.LoginException;
import data.members.StickersColor;
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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyDetailsEdit extends AbstractWindow {

	public MyDetailsEdit() {
		windowEnum = WindowEnum.MY_DETAILS_EDIT;
		window = new Stage();
	}

	public void display(Stage primaryStage, WindowEnum prevWindow, final ArrayList<Label> labels,
			final ArrayList<Label> values) {
		window = primaryStage;
		window.setTitle("Edit My Details");
		GridPane grid = new GridPane();
		System.out.println("MDE begin. prevWindows:				" + prevWindows);
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));
		window.setWidth(350);
		window.setHeight(300);
		ArrayList<TextField> newValues = new ArrayList<TextField>();
		int i = 0;
		int stickerIdx = 0;
		for (; i < labels.size(); i++) {
			if (!labels.get(i).getText().equals("Sticker Color:") && i!=3) {
				newValues.add(new TextField(values.get(i).getText()));
				GridPane.setConstraints(labels.get(i), 0, i);
				GridPane.setConstraints(newValues.get(i), 1, i);
				grid.getChildren().addAll(labels.get(i), newValues.get(i));
			} else
				stickerIdx = i;
		}
		
		
		String prePN = login.getPhoneNumber().substring(0, 3);
		String endPN = login.getPhoneNumber().substring(3, 10);
		System.out.println(prePN + " - " + endPN);
		HBox hboxPhone = new HBox();
		Label phoneNumber = new Label("Phone Number:");
		ChoiceBox<String> prefixNumber = new ChoiceBox<>();
		prefixNumber.getItems().addAll("050", "052", "053", "054", "057");
		prefixNumber.setValue(prePN);
		prefixNumber.getStyleClass().add("cb");
		TextField phoneNumberInput = new TextField();
		phoneNumberInput.setMaxWidth(95);
		// phoneNumberInput.setMaxWidth(50);
		phoneNumberInput.setText(endPN);
		hboxPhone.getChildren().addAll(prefixNumber, phoneNumberInput);
		GridPane.setConstraints(hboxPhone, 1, 3);
		//GridPane.setConstraints(phoneNumberInput, 1, 3);
		GridPane.setColumnSpan(hboxPhone, 2);
		GridPane.setConstraints(phoneNumber, 0, 3);
		
		
		Label sticker = new Label("Sticker Color:");
		ChoiceBox<String> stickerColor = new ChoiceBox<>();
		stickerColor.getItems().addAll("Blue", "Green", "White", "Red", "Bordeaux", "Yellow");
		stickerColor.setValue(StaticMethods.getStickerClolorFromEnum(login.getSticker()));
		stickerColor.getValue();
		stickerColor.getStyleClass().add("cb");
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
				correctedValues.add(3, new Label(prefixNumber.getValue() + phoneNumberInput.getText()));
				correctedValues.add(4, new Label(stickerColor.getValue()));
				
				printCorrectedValues(correctedValues);

				try {
					StickersColor type = StickersColor.valueOf(correctedValues.get(4).getText().toUpperCase());
					//String carNumber, String name, String phoneNumber, String email, String newCar, StickersColor type
					login.userUpdate(login.getCarNumber(), correctedValues.get(1).getText(), correctedValues.get(3).getText(),
							correctedValues.get(0).getText(), correctedValues.get(2).getText(), type);
					// You can only get here if the last prevWindows is
					// 'MyDetails'!!
					MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
					AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
					MD.display(primaryStage, prevWindow, labels, correctedValues);
				} catch (LoginException e1) {
					(new AlertBox()).display("Sign Up", (e1 + ""));
				}
				/* Done */
			}
		});
		GridPane.setConstraints(doneButton, 0, i);

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> {
			// move to editing my details
			//System.out.println("MDE back begin. prevWindows:			" + AbstractWindow.prevWindows);
			this.window.close();
			System.out.println(AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1));
			MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
			AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
			MD.display(primaryStage, prevWindow, labels, values);
			// System.out.println("MDE back end. prevWindows: " +
			// AbstractWindow.prevWindows);

		});
		GridPane.setConstraints(backButton, 1, i);

		grid.getChildren().addAll(doneButton, backButton, hboxPhone, phoneNumber);
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.setScene(scene);
		window.show();

	}
	
	private void printCorrectedValues(ArrayList<Label> correctedValues){
		System.out.println("The correctedValues are: ");
		System.out.println("0: " + correctedValues.get(0).getText());
		System.out.println("1: " + correctedValues.get(1).getText());
		System.out.println("2: " + correctedValues.get(2).getText());
		System.out.println("3: " + correctedValues.get(3).getText());
		System.out.println("4: " + correctedValues.get(4).getText());
	}
	
	public static boolean checkChangesLegality(ArrayList<TextField> newValues) {

		return true;
	}
}
