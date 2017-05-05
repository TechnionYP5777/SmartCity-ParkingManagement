package gui.driver.app;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.event.*;
import javafx.fxml.FXMLLoader;

public class LoginContorller {
	
	@FXML
	private Hyperlink createNewAccHyperLink;
	
	public void createHyperLinkClicked(ActionEvent event) throws Exception{
		 Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Registration");
			Parent root = FXMLLoader.load(getClass().getResource("RegistrationLayout.fxml")); 
			window.setScene(new Scene(root,400,400));		
			window.show();


	}
}
