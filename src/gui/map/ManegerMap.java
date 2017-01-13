package gui.map;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;

/*
 * @Author shay segal
 */
public class ManegerMap extends PmMap {
	@Override
	public void mapInitialized() {
		super.mapInitialized();
		CircleOptions c_ops=new CircleOptions().center(new LatLong(32.777469, 35.021177)).fillColor("red").radius(20.0).fillOpacity(0.40);
		Circle c =new Circle(c_ops);
		map.addMapShape(c);
		scene.getWindow().sizeToScene();
	}
}
