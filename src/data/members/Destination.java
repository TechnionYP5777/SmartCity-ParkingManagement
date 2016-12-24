package data.members;

/**
 * @Author DavidCohen55
 */
public class Destination {
	private MapLocation entrance;
	private String name;

	public Destination(String name, double latitude, double longitude) {
		this.entrance = new MapLocation(latitude, longitude);
		this.name = name;
	}

	public Destination(String name, MapLocation location) {
		this.entrance = location;
		this.name = name;
	}

	public String getDestinationName() {
		return this.name;
	}

	public MapLocation getEntrance() {
		return this.entrance;
	}

	public void setDestinationName(String name) {
		this.name = name;
	}

	public void setEntrance(double latitude, double longitude) {
		this.entrance = new MapLocation(latitude, longitude);
	}

	public void setEntrance(MapLocation ¢) {
		this.entrance = ¢;
	}

}
