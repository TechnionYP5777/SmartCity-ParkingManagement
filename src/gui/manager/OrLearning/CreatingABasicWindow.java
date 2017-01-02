package gui.manager.OrLearning;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreatingABasicWindow extends Application implements EventHandler<ActionEvent> {
	public enum Color { GRAY, BLUE, GREEN, RED }
	Stage window;
	Button myButton, switchToScene2, switchToScene1, alertBoxBtn;
	Scene scene1, scene2;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage mainStage) {
		window=mainStage;
		window.setTitle("Yearly Project is awesome");
		
		//input text field
		TextField getInput = new TextField();
		getInput.setPromptText("Input HERE!");
		getInput.setMaxWidth(340);
		
		Button inputHandler = new Button();
		inputHandler.setText("Now do something with that input");
		inputHandler.setOnAction(e -> isInt(getInput.getText()));
		
		myButton = new Button();
		myButton.setText("Click Here");
		myButton.setOnAction(this);
		
		//Change scene buttons, using lambda action event
		switchToScene2 = new Button();
		switchToScene2.setText("Switch to 2");
		switchToScene2.setOnAction(e -> window.setScene(scene2));
		
		alertBoxBtn = new Button();
		alertBoxBtn.setText("Open Alert Box");
		alertBoxBtn.setOnAction(e -> AlertBox.display("Box", "Please close this window"));
		
		switchToScene1 = new Button();
		switchToScene1.setText("Switch to 1");
		switchToScene1.setOnAction(e -> window.setScene(scene1));
		
		//Parking areas table
		TableView<parkingAreas> table;
		
		//Parking areas table - column build - parking area name
		TableColumn<parkingAreas, String> nameColumn = new TableColumn<>("Parking Area Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		//Parking areas table - column build - parking area Color
		TableColumn<parkingAreas, Color> colorColumn = new TableColumn<>("Parking Area Color");
		colorColumn.setMinWidth(150);
		colorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));

		//Parking areas table - column build - parking spots
		TableColumn<parkingAreas, String> spotsColumn = new TableColumn<>("Parking Area Spots");
		spotsColumn.setMinWidth(130);
		spotsColumn.setCellValueFactory(new PropertyValueFactory<>("spots"));
		
		//TableView for the parking areas
		table = new TableView<>();
		table.setItems(getData());
		table.getColumns().addAll(nameColumn, colorColumn, spotsColumn);
		
		
		//Layouts
		VBox layout = new VBox(10);
		layout.getChildren().addAll(getInput, inputHandler, myButton,switchToScene2, alertBoxBtn,table);
		layout.setAlignment(Pos.CENTER);
		scene1 = new Scene(layout, 500, 500);
		
		//GridPane form - for screen 2
		GridPane gridLayout = new GridPane();
		gridLayout.setPadding(new Insets(15,15,15,15));
		gridLayout.setVgap(10);
		gridLayout.setHgap(10);
		
		//labels & Text Fields
		Label usernameLabel = new Label("Username:");
		GridPane.setConstraints(usernameLabel, 0, 0);
		
		TextField usernameInput = new TextField();
		usernameInput.setPromptText("username");
		GridPane.setConstraints(usernameInput, 1, 0);
		
		Label passLabel = new Label("Password:");
		GridPane.setConstraints(passLabel, 0, 1);
		
		TextField passInput = new TextField();
		passInput.setPromptText("password");
		GridPane.setConstraints(passInput, 1, 1);
		
		ChoiceBox<String> cb = new ChoiceBox<String>();
		GridPane.setConstraints(cb, 0, 2);
		cb.getItems().addAll("red","blue","yellow");
		cb.setValue("red");
		
		//ChoiceBox listener
		cb.getSelectionModel().selectedItemProperty().addListener( (v,prev,next) -> {
			System.out.println(next);
		});
		
		//Login button
		Button loginBtn = new Button("Login");
		GridPane.setConstraints(loginBtn, 0, 3);
		
		//Taking care of switchToScene1 button from before
		GridPane.setConstraints(switchToScene1, 0, 4);

		
		gridLayout.getChildren().addAll(switchToScene1, usernameLabel, usernameInput, passLabel, passInput, loginBtn,cb);
		scene2 = new Scene(gridLayout, 500, 500);
		
		window.setScene(scene1);
		window.show();
	}
	
	//Parking areas table - getData function
	public ObservableList<parkingAreas> getData() {
		ObservableList<parkingAreas> $ = FXCollections.observableArrayList();
		$.add(new parkingAreas("Taub",Color.RED,132));
		$.add(new parkingAreas("Ezrachit",Color.GREEN,44));
		$.add(new parkingAreas("Heykefi",Color.GRAY,78));
		return $;
	}
	
	private boolean isInt(String value) {
		try {
			int number = Integer.parseInt(value);
			System.out.println(number);
			return true;
		}
		catch (NumberFormatException e) {
			System.out.println("Hi there, but " + value + " is not a number!");
			return false;
		}
	}
	
	@Override
	public void handle(ActionEvent ¢) {
		if (¢.getSource()==myButton)
			System.out.println("That's worked");
	}
	
}
