package gui.driver.app;


import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Opening extends Preloader {
    ProgressBar bar;
    Stage stage;
 
    private Scene createOpeningScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        return new Scene(p, 300, 150);        
    }
    
    public void start(Stage ¢) throws Exception {
        this.stage = ¢;
        ¢.setScene(createOpeningScene());        
        ¢.show();
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification ¢) {
        bar.setProgress(¢.getProgress());
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START)
			stage.hide();
    }    
}
