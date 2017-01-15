package gui.map;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapShape;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;


import data.members.MapLocation;

/*
 * @Author shay segal
 */
public class ManegerMap extends PmMap {
	HashMap<String, MapLocation> parkingToLocation;
	HashMap<String, String> parkingToColor;
	HashMap<String, MapShape> shapes= new HashMap<String,MapShape>();
	public ManegerMap(HashMap<String, MapLocation> locations,HashMap<String, String> colors) {
		this.parkingToLocation=locations;
		this.parkingToColor=colors;
	}
	public ManegerMap() {
		this.parkingToLocation=new HashMap<>();
		this.parkingToColor=new HashMap<>();
	}
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
			for(String key : parkingToLocation.keySet()){
				MapLocation ml=parkingToLocation.get(key);
				Circle c=(new Circle(new CircleOptions().center(new LatLong(ml.getLat(), ml.getLat()))
						.fillColor(parkingToColor.get(key)).radius(20.0).fillOpacity(0.40).editable(false)
						.clickable(true)));
				shapes.put(key, c);
				map.addMapShape(c);
			}
			if(parkingToLocation.isEmpty()){
			Circle taub=new Circle(new CircleOptions().center(new LatLong(32.777469, 35.021177)).fillColor("red")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true));
			map.addMapShape(taub);
			Circle StudentHous=new Circle(new CircleOptions().center(new LatLong(32.776009, 35.022816)).fillColor("blue")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true));
			map.addMapShape(StudentHous);
			Circle pool=new Circle(new CircleOptions().center(new LatLong(32.779639, 35.019509)).fillColor("green")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true));
			map.addMapShape(pool);
			map.addMapShape((new Circle(new CircleOptions().center(new LatLong(32.780694, 35.022191)).fillColor("blue")
					.radius(20.0).fillOpacity(0.40).editable(false).clickable(true))));
			}
		});
		ArrayList<String> debug = new ArrayList<String>();
		map.addUIEventHandler(UIEventType.click, e -> {
			debug.add("taub");
			debug.add("studentHous");
			debug.add("pool");
			debug.add("makak");
			focusOnParkingArea(debug.get((new Random()).nextInt(debug.size() - 1)));
		});
	

	}
    public void SetMapComponent(GoogleMapView mapComponent){
		mapComponent.addMapInializedListener(this);
		this.mapComponent=mapComponent;
	}
    public void focusOnParkingArea(String AreaID){    	
    	if (!parkingToLocation.isEmpty()) {
			MapLocation ml = parkingToLocation.get(AreaID);
			map.setCenter(new LatLong(ml.getLat(), ml.getLon()));
			map.setZoom(17);
		} else
			switch (AreaID) {
			case "taub":
				map.setCenter(new LatLong(32.777469, 35.021177));
				map.setZoom(17);
				System.out.println("taub");
				break;
			case "studentHous":
				System.out.println("StudentHous");
				map.setCenter(new LatLong(32.776009, 35.022816));
				map.setZoom(17);
				break;
			case "pool":
				map.setCenter(new LatLong(32.779639, 35.019509));
				System.out.println("pool");
				map.setZoom(17);
				break;
			case "makak":
				map.setCenter(new LatLong(32.780694, 35.022191));
				System.out.println("makak");
				map.setZoom(17);
				break;
			default:
				System.out.println("dont have this label");
			}
    }
    public void ChangeColorOfParking(String id ,String color){
    	parkingToColor.put(id, color);
    	map.removeMapShape(shapes.get(id));
    	MapLocation ml = parkingToLocation.get(id);
    	Circle c=(new Circle(new CircleOptions().center(new LatLong(ml.getLat(), ml.getLat()))
				.fillColor(parkingToColor.get(id)).radius(20.0).fillOpacity(0.40).editable(false)
				.clickable(true)));
		shapes.put(id, c);
		map.addMapShape(c);
    }
}
