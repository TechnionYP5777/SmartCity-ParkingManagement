/*
 * @Author shay segal
 */
package gui.map;

import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.TravelModes;

public class DriverMap extends PmMap {
	@Override
	public void mapInitialized() {
		super.mapInitialized();
		if(fromLogic==null||toLogic==null)
			directionsService.getRoute((new DirectionsRequest("technion", "haifa university", TravelModes.DRIVING)),
					this, new DirectionsRenderer(true, mapComponent.getMap(), directionsPane));
	}
}
