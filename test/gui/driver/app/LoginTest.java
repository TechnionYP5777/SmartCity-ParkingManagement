package gui.driver.app;

import javafx.scene.Parent;
import org.loadui.testfx.GuiTest;
//import org.junit.Test;
//
//import javafx.scene.Parent;

class MyTest extends GuiTest {
	  public Parent getRootNode() {
	    return (new Login()).getRoot();
	  }

//	  @Test
//	  public static void nodeUnderTest_should_behaveInAParticularWay() {
//	    // Test logic goes here.
//	  }
	}