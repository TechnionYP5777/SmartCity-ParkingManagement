/**
 * 
 * @Author zahimizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
//import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
//import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class example extends Application {

	Stage window;
	BorderPane layout;
	TextField nameInput, priceInput, quantityInput;

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Java Fx");

		Menu mainFoodMenu = new Menu("Main Food");
		
		//Menu Items
		mainFoodMenu.getItems().add(new MenuItem("Sushi"));
		mainFoodMenu.getItems().add(new MenuItem("Pizza"));
		mainFoodMenu.getItems().add(new MenuItem("Burger"));
		
		
		Menu dessertMenu = new Menu("Desserts");
		
		//Menu Items
		dessertMenu.getItems().add(new MenuItem("Chocolate Cake"));
		dessertMenu.getItems().add(new MenuItem("Cheesecake"));
		dessertMenu.getItems().add(new MenuItem("Ice Cream"));
		dessertMenu.getItems().add(new MenuItem("Apple Pie"));
		
		
		Menu helpMenu = new Menu ("Help"); 
		CheckMenuItem showLines = new CheckMenuItem ("Show Line Numbers"); 
		showLines.setOnAction(e -> {
			System.out.println(
					showLines.isSelected() ? "Program will now display numbers" : "Program won't display numbers");
		});
		CheckMenuItem autoSave = new CheckMenuItem ("Enable Autosave");
		autoSave.setSelected(true);
		helpMenu.getItems().addAll(showLines,autoSave);
	
		//Difficulty RadioMenuItem
		Menu difficultyMenu = new Menu ("Difficulty");
		ToggleGroup difficultyToggle = new ToggleGroup(); 
		
		RadioMenuItem easy = new RadioMenuItem("Easy"); 
		RadioMenuItem medium = new RadioMenuItem("Medium"); 
		RadioMenuItem hard = new RadioMenuItem("Hard"); 
		
		easy.setToggleGroup(difficultyToggle);
		medium.setToggleGroup(difficultyToggle);
		hard.setToggleGroup(difficultyToggle);
		
		difficultyMenu.getItems().addAll(easy,medium,hard);
		//Menu Bar
		MenuBar bar = new MenuBar(); 
		bar.getMenus().addAll(mainFoodMenu, dessertMenu, helpMenu, difficultyMenu); 
		BorderPane layout = new BorderPane();
		layout.setTop(bar);
		
		Scene scene = new Scene(layout,400,250);
		window.setScene(scene);
		window.show();
	}


	/**
	 * [[SuppressWarningsSpartan]]
	 */
	// get all the products
	public ObservableList<Product> getProducts() {
		ObservableList<Product> products = FXCollections.observableArrayList();
		products.add(new Product("Computer", 700.00, 10));
		products.add(new Product("Laptop", 600.00, 20));
		products.add(new Product("Keyboard", 20.50, 100));
		products.add(new Product("Mouse", 20.00, 100));
		products.add(new Product("Earphones", 5.00, 177));
		return products;
	}

}
