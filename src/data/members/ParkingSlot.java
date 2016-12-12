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
	ParkingSlotStatus status;

	// The slot's color. Can be any sticker color
	ParkingSlotColor color;

	// The slot's location
	Location location;

	// The slot's area
	ParkingArea parkingArea;

	// current user which park on this parking slot
	User currentUser;
}
