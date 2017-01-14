package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.parse4j.ParseException;

import data.members.Destination;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingAreas;
import data.members.ParkingSlot;
import data.members.User;

/**
 * @Author sefialbo
 */

public class NavigationController {
	
	private User user;
	
	private MapLocation currentLocation;
	
	private Destination destination;
	
	private Map<String, Destination> destinations;
	
	private Map<Integer, ParkingArea> parkingAreas;
	
	private boolean walking;
	
	private ParkingAreas allAreas;
	
	public NavigationController(User user) {
		this.user = user;
		this.walking = false;
		this.destinations = Destination.getDestinations();
		this.allAreas = new ParkingAreas();
	}
	
	public NavigationController(User user, MapLocation currentLocation, List<Destination> faculties, boolean walking) {
		this.user = user;
		this.destinations = new HashMap<String, Destination>();
		this.allAreas = new ParkingAreas();
		this.walking = walking;
		this.currentLocation = currentLocation;
		for (Destination ¢ : faculties) this.destinations.put(¢.getDestinationName(), ¢);
		for (ParkingArea ¢ : allAreas.getParkingAreas()) this.parkingAreas.put(¢.getAreaId(), ¢);
	}
	
	public Set<String> getLocations() {
		return destinations.keySet();
	}
	
	public Destination getDestination(String key) {
		return destinations.get(key);
	}
	
	public ParkingAreas getAreas() {
		return allAreas;
	}
	
	public ParkingSlot getClosetParkingSlot() throws ParseException {
		ParkingSlot result = Navigation.closestParkingSlot(user, currentLocation, allAreas, destination);
		if (result == null) showError("No free parking slots, try later.");
		return null;
	}
	
	public void chooseDestination(String name) {
		if (name == null || !destinations.containsKey(name)) showError("No such Faculy exists.");
		this.destination = destinations.get(name);
	}
	
	public void setLocation(MapLocation newLocation) {
		this.currentLocation = newLocation;
	}
	
	public MapLocation getLocation() {
		return currentLocation;
	}
	
	public void setWalking(boolean walking) {
		this.walking = walking;
	}
	
	public boolean getWalking() {
		return walking;
	}
	
	//TODO: replace all System.out.println to GUI
	private void showError(String msg) {
		System.out.println(msg);
	}
}
