package data.members;

public class MapLocation {

	private double lat;
	private double lon;

	public MapLocation(final double latitude, final double longitude) {
		lat = latitude;
		lon = longitude;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLat(final double lat) {
		this.lat = lat;
	}

	public void setLon(final double lon) {
		this.lon = lon;
	}
}
