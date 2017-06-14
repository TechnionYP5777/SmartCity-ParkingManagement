package logic;

public class PresentParkingSlot {
	private double lat;
	private double lon;
	private String name;
	private double price;
	private double distance;
	private double rating;
	
	public PresentParkingSlot(String name, double lat, double lon, double price, double distance, double rating){
		this.lat = lat;
		this.lon = lon;
		this.name = name;
		this.price = price;
		this.distance = distance;
		this.rating = rating;
	}
	public double getLat(){
		return this.lat;
	}
	public double getLon(){
		return this.lon;
	}
	public String getName(){
		return this.name;
	}
	public double getPrice(){
		return this.price;
	}
	public double getDistance(){
		return this.distance;
	}
	public double getRating(){
		return this.rating;
	}
	public void setLat(double lat){
		this.lat = lat;
	}
	public void setLon(double lon){
		this.lon = lon;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setPrice(double price){
		this.price = price;
	}
	public void setDistance(double distance){
		this.distance = distance;
	}
	public void setRating(double rating){
		this.rating = rating;
	}
}
