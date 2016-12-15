/**
 * 
 * @Author zahimizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class example extends Application {

	Stage window;
	TableView<Product> table; 
	
	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Java Fx");

		TableColumn<Product,String> nameCol = new TableColumn<>("Name");
		nameCol.setMinWidth(200);
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<Product,Double> priceCol = new TableColumn<>("Price");
		priceCol.setMinWidth(100);
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		TableColumn<Product,Integer> quantityCol = new TableColumn<>("Quantity");
		quantityCol.setMinWidth(100);
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		
		
		table = new TableView<>(); 
		table.setItems(getProducts());
		table.getColumns().addAll(nameCol,priceCol, quantityCol);
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(table);

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
		products.add(new Product ("Keyboard", 20.50, 100)); 
		products.add(new Product("Mouse",20.00,100));
		products.add(new Product ("Earphones", 5.00, 177));
		return products;
	}

}
