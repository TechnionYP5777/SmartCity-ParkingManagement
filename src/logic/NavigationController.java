package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.members.Faculty;
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
	
	private Faculty faculty;
	
	private ParkingArea area;
	
	private Map<String, Faculty> faculties;
	
	private Map<Integer, ParkingArea> parkingAreas;
	
	private boolean walking;
	
	private ParkingAreas allAreas;
	
	public NavigationController(User user, MapLocation currentLocation, List<Faculty> faculties,
			ParkingAreas areas, boolean walking) {
		this.user = user;
		this.faculties = new HashMap<String, Faculty>();
		this.allAreas = areas;
		this.walking = walking;
		this.currentLocation = currentLocation;
		for (Faculty ¢ : faculties) this.faculties.put(¢.getFacultyName(), ¢);
		for (ParkingArea ¢ : areas.getParkingAreas()) this.parkingAreas.put(¢.getAreaId(), ¢);
	}
	
	public ParkingSlot getClosetParkingSlot() {
		ParkingSlot result = area != null ? Navigation.parkingSlotAtParkingArea(user, area, faculty)
				: Navigation.closestParkingSlot(user, currentLocation, allAreas, faculty);
		if (result == null) showError("No free parking slots, try later.");
		return null;
	}
	
	public void chooseParkingSlot(Integer parkId) {
		if (parkId == null || !parkingAreas.containsKey(parkId)) showError("No such parking area exists.");
		this.area = parkingAreas.get(parkId);
	}
	
	public void chooseFaculty(String name) {
		if (name == null || !faculties.containsKey(name)) showError("No such Faculy exists.");
		this.faculty = faculties.get(name);
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
