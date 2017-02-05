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

	private final User user;

	private MapLocation currentLocation;

	private Destination destination;

	private final Map<String, Destination> destinations;

	private Map<Integer, ParkingArea> parkingAreas;

	private boolean walking;

	private final ParkingAreas allAreas;

	public NavigationController(final User user) {
		this.user = user;
		walking = false;
		destinations = Destination.getDestinations();
		allAreas = new ParkingAreas();
	}

	public NavigationController(final User user, final MapLocation currentLocation, final List<Destination> faculties, final boolean walking) {
		this.user = user;
		destinations = new HashMap<String, Destination>();
		allAreas = new ParkingAreas();
		this.walking = walking;
		this.currentLocation = currentLocation;
		for (final Destination ¢ : faculties)
			destinations.put(¢.getDestinationName(), ¢);
		for (final ParkingArea ¢ : allAreas.getParkingAreas())
			parkingAreas.put(¢.getAreaId(), ¢);
	}

	public Set<String> getLocations() {
		return destinations.keySet();
	}

	public Destination getDestination(final String key) {
		return destinations.get(key);
	}

	public ParkingAreas getAreas() {
		return allAreas;
	}

	public ParkingSlot getClosetParkingSlot() throws ParseException {
		if (Navigation.closestParkingSlot(user, currentLocation, allAreas, destination) == null)
			showError("No free parking slots, try later.");
		return null;
	}

	public void chooseDestination(final String name) {
		if (name == null || !destinations.containsKey(name))
			showError("No such Faculy exists.");
		destination = destinations.get(name);
	}

	public void setLocation(final MapLocation newLocation) {
		currentLocation = newLocation;
	}

	public MapLocation getLocation() {
		return currentLocation;
	}

	public void setWalking(final boolean walking) {
		this.walking = walking;
	}

	public boolean getWalking() {
		return walking;
	}

	// TODO: replace all System.out.println to GUI
	private void showError(final String msg) {
		System.out.println(msg);
	}
}
