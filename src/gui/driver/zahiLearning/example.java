/**
 * 
 * @Author zahimizrahi
 * 
 */
package gui.driver.zahiLearning;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class example extends Application {

	Stage window;
	TreeView <String> tree; 

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Java Fx");
		
		TreeItem<String> root = new TreeItem<>(), br1, br2; 
		
		root.setExpanded(true);
		
		//br1
		br1 = makeBranch("br1", root); 
		makeBranch ("christmas tree", br1); 
		makeBranch ("apple tree", br1); 
		makeBranch ("dark tree", br1); 
		
		//br2
		br2 = makeBranch ("br2", root); 
		makeBranch ("dark tree", br2);
		makeBranch ("palm tree", br2);
		
		tree = new TreeView<String> (root);
		tree.setShowRoot(false);
		tree.getSelectionModel().selectedItemProperty()
		.addListener((v,oldValue,newValue)->{
			if (newValue!=null)  System.out.println(newValue.getValue());
		});
		
		StackPane layout = new StackPane();
		layout.getChildren().add(tree);
		Scene scene = new Scene (layout, 300,250);
		window.setScene(scene);
		window.show();
	}

	private TreeItem<String> makeBranch(String title, TreeItem<String> parent) {
		TreeItem<String> $ = new TreeItem<String> (title); 
		$.setExpanded(true);
		parent.getChildren().add($); 
		return $; 
	}
	
}
