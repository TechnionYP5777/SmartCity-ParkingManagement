package gui.driver.app;

import java.util.ArrayList;

import javafx.stage.Stage;
import logic.LoginManager;

//Currently NOT in use, might use it in the future. 
public abstract class AbstractWindow {
	WindowEnum windowEnum;
	public Stage window;
	protected static LoginManager login = new logic.LoginManager();
	protected static ArrayList<AbstractWindow> prevWindows;

}
