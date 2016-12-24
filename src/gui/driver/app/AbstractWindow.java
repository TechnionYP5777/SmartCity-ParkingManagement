package gui.driver.app;

import java.util.ArrayList;

import javafx.stage.Stage;
import logic.LoginManager;

//Currently NOT in use, might use it in the future. 
public abstract class AbstractWindow {	
	WindowEnum windowEnum;
	Stage window;
	protected static LoginManager login;
	static ArrayList<AbstractWindow> prevWindows; 
	
	
}
