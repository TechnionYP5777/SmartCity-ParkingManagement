package gui.driver.app;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import logic.LoginManager;
import logic.NavigationController;

//Currently NOT in use, might use it in the future. 
public abstract class AbstractWindow {
	WindowEnum windowEnum;
	public Stage window;
	public static MediaPlayer mediaPlayer;
	public static Button buttonMute;
	public static boolean isLinuxOS;
	protected static LoginManager login = new logic.LoginManager();
	protected static NavigationController navigate;
	protected static ArrayList<AbstractWindow> prevWindows;
	
	public Stage getStage() {
		return window; 
	}

}
