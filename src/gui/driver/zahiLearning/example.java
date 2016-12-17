/**
 * 
 * @Author zahimizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class example extends Application {

	Stage window;
	TableView<Product> table;
	TextField nameInput, priceInput, quantityInput;

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Java Fx");

		TableColumn<Product, String> nameCol = new TableColumn<>("Name");
		nameCol.setMinWidth(200);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

		TableColumn<Product, Integer> quantityCol = new TableColumn<>("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		// Name Value
		TextField nameInput = new TextField();
		nameInput.setPromptText("name");
		nameInput.setMinWidth(100);

		// Price Value
		TextField priceInput = new TextField();
		priceInput.setPromptText("price");
		
		// Quantity Value
		TextField quantityInput = new TextField();
		quantityInput.setPromptText("quantity");

		//Button
		Button add = new Button("Add"); 
		Button delete = new Button("Delete");
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(10,10,10,10));
		hbox.setSpacing(10);
		hbox.getChildren().addAll(nameInput,priceInput,quantityInput,add,delete);

		table = new TableView<>();
		table.setItems(getProducts());
		table.getColumns().addAll(nameCol, priceCol, quantityCol);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(table, hbox);
		

		Scene scene = new Scene(vbox);
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
