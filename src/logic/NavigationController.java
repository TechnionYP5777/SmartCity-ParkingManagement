package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseException;

import data.members.Destination;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.User;
import manager.logic.ParkingAreas;

/**
 * @Author sefialbo
 */

public class NavigationController {
	
	private User user;
	
	private MapLocation currentLocation;
	
	private Destination destination;
	
	private ParkingArea area;
	
	private Map<String, Destination> faculties;
	
	private Map<Integer, ParkingArea> parkingAreas;
	
	private boolean walking;
	
	private ParkingAreas allAreas;
	
	public NavigationController(User user, MapLocation currentLocation, List<Destination> faculties,
			ParkingAreas areas, boolean walking) {
		this.user = user;
		this.faculties = new HashMap<String, Destination>();
		this.allAreas = areas;
		this.walking = walking;
		this.currentLocation = currentLocation;
		for (Destination ¢ : faculties) this.faculties.put(¢.getDestinationName(), ¢);
		for (ParkingArea ¢ : areas.getParkingAreas()) this.parkingAreas.put(¢.getAreaId(), ¢);
	}
	
	public ParkingSlot getClosetParkingSlot() throws ParseException {
		ParkingSlot result = area != null ? Navigation.parkingSlotAtParkingArea(user, area, destination)
				: Navigation.closestParkingSlot(user, currentLocation, allAreas, destination);
		if (result == null) showError("No free parking slots, try later.");
		return null;
	}
	
	public void chooseParkingSlot(Integer parkId) {
		if (parkId == null || !parkingAreas.containsKey(parkId)) showError("No such parking area exists.");
		this.area = parkingAreas.get(parkId);
	}
	
	public void chooseDestination(String name) {
		if (name == null || !faculties.containsKey(name)) showError("No such Faculy exists.");
		this.destination = faculties.get(name);
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
