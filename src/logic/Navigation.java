package logic;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Exceptions.NoSlotAvailable;
import data.members.Destination;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingAreas;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.User;

public class Navigation {

	private static boolean canPark(final User u, final ParkingSlot s) {
		return u.getSticker().ordinal() <= s.getColor().ordinal();
	}

	private static JSONObject getInnerJSON(final String url) {
		final JSONParser $ = new JSONParser();
		try {
			return (JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) $
					.parse(IOUtils.toString(new URL(url), StandardCharsets.UTF_8))).get("rows")).get(0))
							.get("elements")).get(0);
		} catch (ParseException | IOException ¢) {
			¢.printStackTrace();
		}
		return null;
	}

	private static String createURL(final MapLocation source, final MapLocation target, final boolean walkingMode) {
		String $ = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins="
				+ source.getLat() + "," + source.getLon() + "&destinations=" + target.getLat() + "," + target.getLon()
				+ "&key=AIzaSyDw25loi0t1ms-bCuLPHS2Bm9aEIvyu9Wo";
		if (walkingMode)
			$ += "&mode=walking";

		return $;
	}

	public static long getDistance(final MapLocation source, final MapLocation target, final boolean walkingMode) {
		final JSONObject $ = getInnerJSON(createURL(source, target, walkingMode));
		return $ == null ? 0 : (long) ((JSONObject) $.get("distance")).get("value");
	}

	public static long getDuration(final MapLocation source, final MapLocation target, final boolean walkingMode) {
		final JSONObject $ = getInnerJSON(createURL(source, target, walkingMode));
		return !$.containsKey("duration") || !((JSONObject) $.get("duration")).containsKey("value") ? Integer.MAX_VALUE
				: $ != null ? (long) ((JSONObject) $.get("duration")).get("value") : 0;
	}

	public static int getClosestParkingArea(final MapLocation currentLocation, final boolean walkingMode) {

		final JSONParser parser = new JSONParser();
		try {
			final JSONArray a = (JSONArray) parser.parse(new FileReader("./src/logic/parkingAreas.json"));
			int $ = -1;
			long dist = Integer.MAX_VALUE;
			for (final Object o : a) {
				final JSONObject parkingArea = (JSONObject) o;
				final int id = Integer.parseInt((String) parkingArea.get("id"));
				final double targetLat = Double.parseDouble((String) parkingArea.get("locationX")),
						targetLon = Double.parseDouble((String) parkingArea.get("locationY"));
				final long d = getDistance(currentLocation, new MapLocation(targetLat, targetLon), walkingMode);
				if (d < dist) {
					$ = id;
					dist = d;
				}
			}
			return $;
		} catch (ParseException | IOException ¢) {
			¢.printStackTrace();
		}
		return -1;
	}

	public static ParkingSlot closestParkingSlot(final User u, final MapLocation currentLocation, final ParkingAreas a, final Destination d)
			throws org.parse4j.ParseException {

		ParkingSlot $ = null;
		long minDuration = Integer.MAX_VALUE;

		for (final ParkingArea parkingArea : a.getParkingAreas())
			if (parkingArea.getNumOfFreeSlots() > 0)
				for (final ParkingSlot parkingSlot : parkingArea.getFreeSlots()) {
					if (!canPark(u, parkingSlot))
						continue;
					final long duration = getDuration(parkingSlot.getLocation(), d.getEntrance(), true);
					if (duration < minDuration) {
						$ = parkingSlot;
						minDuration = duration;
					}
				}
		return $;
	}

	// returns the closest parking to the given faculty in the given parking
	// area
	public static ParkingSlot parkingSlotAtParkingArea(final User u, final ParkingArea a, final Destination d)
			throws org.parse4j.ParseException {

		if (a.getNumOfFreeSlots() <= 0)
			return null;

		ParkingSlot $ = null;
		long minDuration = Integer.MAX_VALUE;

		for (final ParkingSlot parkingSlot : a.getFreeSlots()) {

			if (!canPark(u, parkingSlot))
				continue;

			final long duration = getDuration(parkingSlot.getLocation(), d.getEntrance(), true);
			if (duration < minDuration) {
				$ = parkingSlot;
				minDuration = duration;
			}
		}
		return $;
	}

	public static void parkAtSlot(final User u, final ParkingSlot s) throws NoSlotAvailable, org.parse4j.ParseException {
		if (s == null)
			throw new NoSlotAvailable("No Slot Available");
		s.changeStatus(ParkingSlotStatus.TAKEN);
		u.setCurrentParking(s);
	}

	public static ParkingSlot parkAtClosestSlot(final User u, final MapLocation currentLocation, final ParkingAreas a, final Destination d)
			throws NoSlotAvailable, org.parse4j.ParseException {
		final ParkingSlot $ = closestParkingSlot(u, currentLocation, a, d);
		parkAtSlot(u, $);
		return $;

	}

	public static ParkingSlot parkAtArea(final User u, final ParkingArea a, final Destination d)
			throws NoSlotAvailable, org.parse4j.ParseException {
		final ParkingSlot $ = parkingSlotAtParkingArea(u, a, d);
		parkAtSlot(u, $);
		return $;
	}

}