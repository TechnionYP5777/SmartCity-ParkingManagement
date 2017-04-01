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
		queries = new Queries();
	}

	public Queries getQueries() {
		return queries;
	}

	public void setQueries(final Queries ¢) {
		queries = ¢;
	}

	// return the number of free slots in a given area
	public int getNumOfFreeSlotsPerArea(final int areaId) {
		final ParkingArea $ = queries.returnArea(areaId);
		return $ == null ? -1 : queries.getNumOfFreeByArea($);
	}

	// return the number of taken slots in a given area
	public int getNumOfTakenSlotsPerArea(final int areaId) {
		final ParkingArea $ = queries.returnArea(areaId);
		return $ == null ? -1 : queries.getNumOfTakenByArea($);
	}

	// return the total number of slots in a given area
	public int getTotalNumOfSlotsPerArea(final int areaId) {
		final ParkingArea $ = queries.returnArea(areaId);
		return $ == null ? -1 : $.getNumOfParkingSlots();
	}

	// return the color of slots in a given area
	public StickersColor getColorOfArea(final int areaId) {
		final ParkingArea $ = queries.returnArea(areaId);
		return $ == null ? null : $.getColor();
	}

	public List<String> getAllPossibleColors() {
		final List<String> $ = new ArrayList<String>();
		for (final StickersColor ¢ : StickersColor.values())
			$.add(¢.name());
		return $;
	}
}
