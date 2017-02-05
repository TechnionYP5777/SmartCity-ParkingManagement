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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
	}

	public void display(Stage primaryStage, WindowEnum prevWindow, final ArrayList<Label> ls,
			final ArrayList<Label> values) {
		//window = primaryStage;
		window.setTitle("Edit My Details");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));
		window.setWidth(350);
		window.setHeight(340);
		ArrayList<TextField> newValues = new ArrayList<TextField>();
		int i = 0, stickerIdx = 4;
		//System.out.println("DME labels.size(): " + labels.size());
		for (; i <= 2; ++i) {
			
			//System.out.println("DME newValues: " + i + " " + labels.get(i).getText());
				newValues.add(new TextField(values.get(i).getText()));
				GridPane.setConstraints(ls.get(i), 0, i);
				GridPane.setConstraints(newValues.get(i), 1, i);
				grid.getChildren().addAll(ls.get(i), newValues.get(i));
		}
		
		
		String prePN = login.getPhoneNumber().substring(0, 3), endPN = login.getPhoneNumber().substring(3, 10);
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
				for (int j = 0; j < newValues.size(); ++j)
					correctedValues.add(new Label(newValues.get(j).getText()));
				correctedValues.add(3, new Label(prefixNumber.getValue() + phoneNumberInput.getText()));
				correctedValues.add(4, new Label(stickerColor.getValue()));
				
				//printCorrectedValues(correctedValues);

				try {
					//String carNumber, String name, String phoneNumber, String email, String newCar, StickersColor type
					login.userUpdate(login.getCarNumber(), correctedValues.get(1).getText(), correctedValues.get(3).getText(),
							correctedValues.get(0).getText(), correctedValues.get(2).getText(), StickersColor.valueOf(correctedValues.get(4).getText().toUpperCase()));
					// You can only get here if the last prevWindows is 'MyDetails'!!
					window.close();
					MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
					AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
					MD.display(primaryStage, prevWindow, ls, correctedValues);
				} catch (LoginException e1) {
					(new AlertBox()).display("Sign Up", e1 + "");
				}
				/* Done */
			}
		});
		//System.out.println("MDE 2 size: " + values.size() + " : " + values);
		GridPane.setConstraints(doneButton, 0, values.size());

		Button backButton = new Button();
		backButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("back_button.png"))));
		backButton.getStyleClass().add("button-go");
		
		backButton.setOnAction(e -> {
			// move to editing my details
			this.window.close();
			MyDetails MD = (MyDetails) AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size() - 1);
			AbstractWindow.prevWindows.remove(prevWindows.size() - 1);
			MD.display(primaryStage, prevWindow, ls, values);
			/*prevWindows.get(prevWindows.size() - 1).window.show();
			prevWindows.remove(prevWindows.size() - 1);*/

		});
		GridPane.setConstraints(backButton, 1, values.size());

		grid.getChildren().addAll(doneButton, backButton, hboxPhone, phoneNumber);
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.setScene(scene);
		window.show();

	}
	
	/*private void printCorrectedValues(ArrayList<Label> correctedValues){
		System.out.println("The correctedValues are: ");
		System.out.println("0: " + correctedValues.get(0).getText());
		System.out.println("1: " + correctedValues.get(1).getText());
		System.out.println("2: " + correctedValues.get(2).getText());
		System.out.println("3: " + correctedValues.get(3).getText());
		System.out.println("4: " + correctedValues.get(4).getText());
	}
	*/
	public static boolean checkChangesLegality(ArrayList<TextField> newValues) {

		return true;
	}
}
