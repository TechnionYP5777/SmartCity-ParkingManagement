package gui.manager.OrLearning;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TreeMenu extends Application {
	Stage window;

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Tree Menu");

		// Tree
		final TreeItem<String> root = new TreeItem<>();
		root.setExpanded(true);

		// Parking colors
		final TreeItem<String> colors = makeBranch("Colors", root);
		makeBranch("Blue", colors);
		makeBranch("Red", colors);
		makeBranch("Green", colors);

		// Parking areas
		final TreeItem<String> areas = makeBranch("Areas", root);
		makeBranch("Dorms", areas);
		makeBranch("All around", areas);
		makeBranch("Taub", areas);
		areas.setExpanded(false);

		// Set Tree
		final TreeView<String> tree = new TreeView<>(root);
		tree.setShowRoot(false);
		tree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
			if (newValue != null)
				System.out.println(newValue);
		});

		// Layout
		final StackPane layout = new StackPane();
		layout.getChildren().add(tree);
		window.setScene(new Scene(layout, 300, 300));
		window.show();
	}

	public TreeItem<String> makeBranch(final String title, final TreeItem<String> parent) {
		final TreeItem<String> $ = new TreeItem<>(title);
		$.setExpanded(true);
		parent.getChildren().add($);
		return $;
	}
}
