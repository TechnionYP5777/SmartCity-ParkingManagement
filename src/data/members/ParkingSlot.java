package data.members;

import javax.tools.JavaFileManager.Location;

/**
 * @author Toma
 * @since 12.11.16
 * 
 *        This class represent a parking slot
 */
public class ParkingSlot {

	// The slot's status. Can be either free, taken or unavailable
	private ParkingSlotStatus status;

	// The slot's color. Can be any sticker color
	private ParkingSlotColor color;

	// The slot's location
	private Location location;

	// The slot's area
	private ParkingArea parkingArea;

	// current user which park on this parking slot
	private User currentUser;

	public ParkingSlotStatus getStatus() {
		return status;
	}

	public void setStatus(ParkingSlotStatus status) {
		this.status = status;
	}

	public ParkingSlotColor getColor() {
		return color;
	}

	public void setColor(ParkingSlotColor color) {
		this.color = color;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ParkingArea getParkingArea() {
		return parkingArea;
	}

	public void setParkingArea(ParkingArea parkingArea) {
		this.parkingArea = parkingArea;
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
