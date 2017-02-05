
package gui.driver.app;
/*
 * @author zahimizrahi
 */

import java.util.ArrayList;
//import java.util.Iterator;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.*;
import javafx.concurrent.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;


public class Opening extends Application {
	public static final String SPLASH_IMAGE = "https://s23.postimg.org/ecvvaduyz/smart_parking.png";

	private Pane openingLayout;
	private ProgressBar loadProgress;
	private Label progressText;
	private Stage primaryStage;
	private static final int SPLASH_WIDTH = 274;
	private static final int SPLASH_HEIGHT = 266;
	
	
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void init() {
		ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("smart_parking_icon.png")));
		loadProgress = new ProgressBar();
		loadProgress.setPrefWidth(SPLASH_WIDTH);
		
		progressText = new Label("Opening the system . . .");
		progressText.setAlignment(Pos.CENTER);
		
		openingLayout = new VBox();
		openingLayout.getChildren().addAll(logo, loadProgress, progressText);
	}

	@Override
	public void start(final Stage initStage) throws Exception {
		final Task<ObservableList<String>> task = new Task<ObservableList<String>>() {
			@Override
			protected ObservableList<String> call() throws InterruptedException {
				ObservableList<String> $ = FXCollections.<String>observableArrayList(),
						team = FXCollections.observableArrayList("Zahi Mizrahi", "Shahar Yair", "Shay Segal",
								"Sefi Albo", "Dani Shames", "David Cohen", "Or Troyaner", "Tom Nof", "Inbal Matityahu");
				updateMessage("SmartParking - Made By ");
				for (int i = 0; i < team.size(); i++) {
					Thread.sleep(400);
					updateProgress(i + 1, team.size());
					String nextMember= team.get(i);
					$.add(nextMember);
					updateMessage("SmartParking - Made By " + nextMember);
				}
				Thread.sleep(600);
				updateMessage("The system is up.");

				return $;
			}
		};

		showSplash(initStage, task, () -> showMainMenu(task.valueProperty()));
		new Thread(task).start();
	}

	private void showMainMenu(ReadOnlyObjectProperty<ObservableList<String>> friends) {
		ChooseAction chooseActionObject = new ChooseAction();
		AbstractWindow.prevWindows = new ArrayList<AbstractWindow>(); 
		primaryStage = new Stage(); 
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
		chooseActionObject.display(primaryStage, WindowEnum.NONE);
	}

	private void showSplash(final Stage initStage, Task<?> t, InitCompletionHandler h) {
		progressText.textProperty().bind(t.messageProperty());
		loadProgress.progressProperty().bind(t.progressProperty());
		t.stateProperty().addListener((observableValue, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				loadProgress.progressProperty().unbind();
				loadProgress.setProgress(1);
				initStage.toFront();
				FadeTransition fadeSplash = new FadeTransition(Duration.seconds(1), openingLayout);
				fadeSplash.setFromValue(1.0);
				fadeSplash.setToValue(0.0);
				fadeSplash.setOnFinished(λ -> initStage.hide());
				fadeSplash.play();

				h.complete();
			} 
		});

		Scene openingScene = new Scene(openingLayout, Color.TRANSPARENT);
		final Rectangle2D rect = Screen.getPrimary().getBounds();
		initStage.setScene(openingScene);
		initStage.setX(rect.getMinX() + rect.getWidth() / 2 - SPLASH_WIDTH / 2);
		initStage.setY(rect.getMinY() + rect.getHeight() / 2 - SPLASH_HEIGHT / 2);
		initStage.initStyle(StageStyle.TRANSPARENT);
		initStage.setAlwaysOnTop(true);
		initStage.show();
	}
	
	public static ChooseAction getCAObject(ArrayList<AbstractWindow> ¢){
//		Iterator<AbstractWindow> iterator = windows.iterator();
//		while(iterator.hasNext()){
//			if(iterator.getClass().equals(ChooseAction.class)){
//				System.out.print("found CA object: "+);
//			}
//		}
		for(AbstractWindow $ : ¢)
			if ($.getClass().equals(ChooseAction.class)) {
				System.out.print("found CA object: " + $);
				return (ChooseAction) $;
			}

		return null;
	}
	public static MyDetails getMDObject(ArrayList<AbstractWindow> ¢){	
		for(AbstractWindow $ : ¢)
			if ($.getClass().equals(MyDetails.class)) {
				System.out.print("found CA object: " + $);
				return (MyDetails) $;
			}
		return null;
	}
	public static MyDetailsEdit getMDEObject(ArrayList<AbstractWindow> ¢){	
		for(AbstractWindow $ : ¢)
			if ($.getClass().equals(MyDetailsEdit.class)) {
				System.out.print("found CA object: " + $);
				return (MyDetailsEdit) $;
			}
		return null;
	}

	public interface InitCompletionHandler {
		void complete();
	}
	
	
}