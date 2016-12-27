package gui.map;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

public class MyMarker extends Marker{
	public LatLong lat;
	private String myTitle;
	private MyMarker(MarkerOptions markerOptions) {
		super(markerOptions);
		// TODO Auto-generated constructor stub
	}
	public MyMarker(MarkerOptions markerOptions,String title, LatLong lat){
		this(markerOptions);
		myTitle=title;
		this.lat=lat;
	}
	public boolean isTitle(String title){
		if(myTitle.equals(title)){
			return true;
		}
		return false;
	}
	

}
