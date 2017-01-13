/*
 * @Author shay segal
 */
package gui.map;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.TravelModes;
import javafx.scene.control.Label;

public class DriverMap extends PmMap {
	
	public DriverMap(String fromLogic,String toLogic){
		super(fromLogic,toLogic);
	}
	//Don't want someone to create this kind of map without origin and destination
	@SuppressWarnings({ "unused" })
	private DriverMap() {
	}
	@Override
	public void mapInitialized() {
		super.mapInitialized();
		if(fromLogic!=null||toLogic!=null){
			directionsService.getRoute((new DirectionsRequest("technion", "haifa university", TravelModes.DRIVING)),
					this, new DirectionsRenderer(true, mapComponent.getMap(), directionsPane));
			tb.getItems().addAll(new Label("Origin:"+fromLogic),new Label("Destination:"+toLogic));
			scene.getWindow().sizeToScene();
		}
	}
}
