package gui.driver.app;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BackgroundWorkerDemo extends Application {

    private final static String CNTR_LBL_STR = "Counter: ";
    private int counter;

    Label counterLabel;
    Button counterButton;
    Button taskButton;

    @Override
    public void start(Stage primaryStage) {
        counter = 0;
        counterLabel = new Label(CNTR_LBL_STR + counter);
        counterButton = new Button("Increment Counter");
        counterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                counter++;
                counterLabel.setText(CNTR_LBL_STR + counter);
            }
        });
        taskButton = new Button("Long Running Task");
        taskButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               runTask();
            }
        });

        VBox mainPane = new VBox();
        mainPane.setPadding(new Insets(10));
        mainPane.setSpacing(5.0d);
        mainPane.getChildren().addAll(counterLabel, counterButton, taskButton);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.show();
    }

    private void runTask() {

        final double wndwWidth = 300.0d;
        Label updateLabel = new Label("Running tasks...");
        updateLabel.setPrefWidth(wndwWidth);
        ProgressBar progress = new ProgressBar();
        progress.setPrefWidth(wndwWidth);

        VBox updatePane = new VBox();
        updatePane.setPadding(new Insets(10));
        updatePane.setSpacing(5.0d);
        updatePane.getChildren().addAll(updateLabel, progress);

        Stage taskUpdateStage = new Stage(StageStyle.UTILITY);
        taskUpdateStage.setScene(new Scene(updatePane));
        taskUpdateStage.show();

        Task longTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max = 50;
                for (int i = 1; i <= max; i++) {
                    if (isCancelled()) {
                        break;
                    }
                    updateProgress(i, max);
                    updateMessage("Task part " + String.valueOf(i) + " complete");

                    Thread.sleep(100);
                }
                return null;
            }
        };

        longTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                taskUpdateStage.hide();
            }
        });
        progress.progressProperty().bind(longTask.progressProperty());
        updateLabel.textProperty().bind(longTask.messageProperty());

        taskUpdateStage.show();
        new Thread(longTask).start();
    }

}