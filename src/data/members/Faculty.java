package data.members;

/**
 * @Author DavidCohen55
 */
public class Faculty {
	private MapLocation entrance;
	private String facultyName;

	public Faculty(String name, double latitude, double longitude) {
		this.entrance = new MapLocation(latitude, longitude);
		this.facultyName = name;
	}

	public Faculty(String name, MapLocation location) {
		this.entrance = location;
		this.facultyName = name;
	}

	public String getFacultyName() {
		return this.facultyName;
	}

	public MapLocation getEntrance() {
		return this.entrance;
	}

	public void setFacultyName(String name) {
		this.facultyName = name;
	}

	public void setEntrance(double latitude, double longitude) {
		this.entrance = new MapLocation(latitude, longitude);
	}

	public void setEntrance(MapLocation location) {
		this.entrance = location;
	}

}
