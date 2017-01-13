package gui.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;

/*
 * @Author shay segal
 */
public class ManegerMap extends PmMap {
	@Override
	public void mapInitialized() {
		LatLong center = new LatLong(32.777, 35.0225);
		MapOptions options = new MapOptions();
		options.center(center).zoom(12).overviewMapControl(false).panControl(false).rotateControl(false)
				.scaleControl(true).streetViewControl(true).zoomControl(true).mapType(MapTypeIdEnum.ROADMAP);
		
		map = mapComponent.createMap(options, false);
		map.setHeading(123.2);
		map.fitBounds(new LatLongBounds(center, new LatLong(32.779032, 35.024663)));
		mapComponent.addMapReadyListener(() -> {
			map.addMapShape((new Circle(new CircleOptions().center(new LatLong(32.777469, 35.021177)).fillColor("red")
					.radius(20.0).fillOpacity(0.40))));
		});
		

	}
    public void SetMapComponent(GoogleMapView mapComponent){
		mapComponent.addMapInializedListener(this);
		this.mapComponent=mapComponent;
	}
}
