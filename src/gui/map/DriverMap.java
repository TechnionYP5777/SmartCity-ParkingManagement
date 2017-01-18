/*
 * @Author shay segal
 */
package gui.map;


import org.parse4j.ParseException;

import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.TravelModes;

import Exceptions.NotExists;
import data.members.Destination;
import data.members.MapLocation;
import data.members.ParkingSlot;
import javafx.scene.control.Label;

public class DriverMap extends PmMap {
	// private final static String StubFrom = "32.774207, 35.029546";
	// private final static String StubTo = "32.777480, 35.021224";
	private MapLocation from;
	private MapLocation to;

	public DriverMap(MapLocation fromLogic, MapLocation toLogic) {
		this(fromLogic.getLat() + ", " + fromLogic.getLon(), toLogic.getLat() + ", " + toLogic.getLon()); // till
																											// DB
																											// works
																											// fine...

		from = fromLogic;
		to = toLogic;
		// this(StubFrom,StubTo);
	}

	public DriverMap(String fromLogic, String toLogic) {
		super(fromLogic, toLogic);
	}

	// Don't want someone to create this kind of map without origin and
	// destination
	@SuppressWarnings({ "unused" })
	private DriverMap() {
	}

	@Override
	public void mapInitialized() {
		super.mapInitialized();
		if (fromLogic == null && toLogic == null)
			return;
		if (from == null || to == null) {
			directionsService.getRoute((new DirectionsRequest("technion", "haifa university", TravelModes.DRIVING)),
					this, new DirectionsRenderer(true, mapComponent.getMap(), directionsPane));
			tb.getItems().addAll(new Label("Origin:" + fromLogic), new Label("Destination:" + toLogic));
		} else
			try {
				Destination dStart = new Destination(from);
				directionsService.getRoute((new DirectionsRequest(fromLogic, toLogic, TravelModes.DRIVING)), this,
						new DirectionsRenderer(true, mapComponent.getMap(), directionsPane));
				tb.getItems().addAll(new Label("Origin:" + dStart.getDestinationName() + " " + fromLogic),
						new Label("Destination: " + ParkingSlot.ParkingNameByLocation(to) + " " + toLogic));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotExists e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		scene.getWindow().sizeToScene();
	}
}
