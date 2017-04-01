package gui.driver.app;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

//Currently NOT in use, might use it in the future. 
public abstract class AbstractWindow {
	WindowEnum windowEnum;
	public Stage window;
	public static MediaPlayer mediaPlayer;
	public static Button buttonMute;
	public static boolean isLinuxOS;
	protected static ArrayList<Button> muteButtonsAL;
	protected static ArrayList<AbstractWindow> prevWindows;

	public Stage getStage() {
		return window;
	}

}
