package manager.logic;

import java.util.ArrayList;
import java.util.List;

import data.members.ParkingArea;
import data.members.StickersColor;

/**
 * @author Inbal Matityahu
 * @since 12.13.16
 * 
 *        This class represent an info about a given area. we will use this
 *        class, when clicking on a parking area in the map
 */

public class SelectAnArea {
	private Queries queries;

	public SelectAnArea() {
		this.queries = new Queries();
	}

	public Queries getQueries() {
		return queries;
	}

	public void setQueries(Queries ¢) {
		this.queries = ¢;
	}

	// return the number of free slots in a given area
	public int getNumOfFreeSlotsPerArea(int areaId) {
		ParkingArea $ = this.queries.returnArea(areaId);
		return $ == null ? -1 : this.queries.getNumOfFreeByArea($);
	}

	// return the number of taken slots in a given area
	public int getNumOfTakenSlotsPerArea(int areaId) {
		ParkingArea $ = this.queries.returnArea(areaId);
		return $ == null ? -1 : this.queries.getNumOfTakenByArea($);
	}

	// return the total number of slots in a given area
	public int getTotalNumOfSlotsPerArea(int areaId) {
		ParkingArea $ = this.queries.returnArea(areaId);
		return $ == null ? -1 : $.getNumOfParkingSlots();
	}

	// return the color of slots in a given area
	public StickersColor getColorOfArea(int areaId) {
		ParkingArea $ = this.queries.returnArea(areaId);
		return $ == null ? null : $.getColor();
	}

	public List<String> getAllPossibleColors() {
		List<String> $ = new ArrayList<String>();
		for (StickersColor ¢ : StickersColor.values())
			$.add(¢.name());
		return $;
	}
}
