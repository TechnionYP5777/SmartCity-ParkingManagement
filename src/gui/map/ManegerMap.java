package gui.map;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.MapStateEventType;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapShape;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

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
		map.fitBounds(new LatLongBounds(new LatLong(32.781605, 35.016952),new LatLong(32.774531, 35.028263)));
		mapComponent.addMapReadyListener(() -> {
			Circle taub=new Circle(new CircleOptions().center(new LatLong(32.777469, 35.021177)).fillColor("red")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true));
			map.addMapShape(taub);
			Circle StudentHous=new Circle(new CircleOptions().center(new LatLong(32.776009, 35.022816)).fillColor("blue")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true));
			map.addMapShape(StudentHous);
			Circle pool=new Circle(new CircleOptions().center(new LatLong(32.779639, 35.019509)).fillColor("green")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true));
			map.addMapShape(pool);
			Circle makak=new Circle(new CircleOptions().center(new LatLong(32.780694, 35.022191)).fillColor("blue")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true));
			map.addMapShape(makak);
		});


	}
    public void SetMapComponent(GoogleMapView mapComponent){
		mapComponent.addMapInializedListener(this);
		this.mapComponent=mapComponent;
	}
    public void focusOnParkingArea(String AreaID){
    	switch (AreaID){
    	case "taub":
    		map.setCenter(new LatLong(32.777469, 35.021177));
    	case "StudentHous":
    		map.setCenter(new LatLong(32.776009, 35.022816));
    	case "pool":
    		map.setCenter(new LatLong(32.779639, 35.019509));
    	case "makak":
    		map.setCenter(new LatLong(32.780694, 35.022191));
    	default:
    		System.out.println("dont have this label");
    	}
    }
}
