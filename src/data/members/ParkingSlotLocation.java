package data.members;

public class ParkingSlotLocation {
	
	private double lat;
	private double lon;

	public ParkingSlotLocation(double latitude, double longitude){
		this.lat = latitude;
		this.lon = longitude;
	}
	public double getLat(){
		return lat;
	}
	public double getLon(){
		return lon;
	}
	public void setLat(double lat){
		this.lat = lat;
	}
	public void setLon(double lon){
		this.lon = lon;
	}
}
