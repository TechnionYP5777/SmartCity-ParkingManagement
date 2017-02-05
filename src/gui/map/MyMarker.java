package gui.map;

import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;

/*
 * @Autor Shay Segal
 */
public class MyMarker extends Marker {
	public LatLong lat;
	private String myTitle;

	private MyMarker(final MarkerOptions markerOptions) {
		super(markerOptions);
		// TODO Auto-generated constructor stub
	}

	public MyMarker(final MarkerOptions markerOptions, final String title, final LatLong lat) {
		this(markerOptions);
		myTitle = title;
		this.lat = lat;
	}

	public boolean isTitle(final String title) {
		return myTitle.equals(title);
	}

}
