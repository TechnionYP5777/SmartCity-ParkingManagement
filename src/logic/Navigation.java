package logic;

import data.members.*;
import Exceptions.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

public class Navigation {

	private static boolean canPark(User u, ParkingSlot s) {
		return u.getSticker().ordinal() <= s.getColor().ordinal();
	}

	private static JSONObject getInnerJSON(String url) {
		JSONParser parser = new JSONParser();
		try {

			return (JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) parser
					.parse(IOUtils.toString((new URL(url)), StandardCharsets.UTF_8))).get("rows")).get(0))
							.get("elements")).get(0);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String createURL(MapLocation source, MapLocation target, boolean walkingMode) {
		String $ = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins="
				+ (source.getLat() + "," + source.getLon()) + "&destinations=" + target.getLat() + "," + target.getLon()
				+ "&key=AIzaSyDw25loi0t1ms-bCuLPHS2Bm9aEIvyu9Wo";
		if (walkingMode)
			$ += "&mode=walking";

		return $;
	}

	public static long getDistance(MapLocation source, MapLocation target, boolean walkingMode) {
		String url = createURL(source, target, walkingMode);
		JSONObject element = getInnerJSON(url);
		return element == null ? 0 : (long) ((JSONObject) element.get("distance")).get("value");
	}

	public static long getDuration(MapLocation source, MapLocation target, boolean walkingMode) {
		String url = createURL(source, target, walkingMode);
		JSONObject element = getInnerJSON(url);
		if (!element.containsKey("duration") || !((JSONObject)element.get("duration")).containsKey("value"))
			return Integer.MAX_VALUE;
		return element == null ? 0 : (long) ((JSONObject) element.get("duration")).get("value");
	}

	public static int getClosestParkingArea(MapLocation currentLocation, boolean walkingMode) {

		JSONParser parser = new JSONParser();
		try {
			JSONArray a = (JSONArray) parser.parse(new FileReader("./src/logic/parkingAreas.json"));
			int $ = -1;
			long dist = Integer.MAX_VALUE;
			for (Object o : a) {

				JSONObject parkingArea = (JSONObject) o;
				int id = Integer.parseInt((String) parkingArea.get("id"));
				double targetLat = Double.parseDouble((String) parkingArea.get("locationX"));
				double targetLon = Double.parseDouble((String) parkingArea.get("locationY"));
				MapLocation target = new MapLocation(targetLat, targetLon);
				long d = getDistance(currentLocation, target, walkingMode);
				if (d < dist) {
					$ = id;
					dist = d;
				}
			}
			return $;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static ParkingSlot closestParkingSlot(User u, MapLocation currentLocation, ParkingAreas a,
			Destination d) throws org.parse4j.ParseException {

		ParkingSlot $ = null;
		long minDuration = Integer.MAX_VALUE;

		for (ParkingArea parkingArea : a.getParkingAreas()) {

			if (parkingArea.getNumOfFreeSlots() <= 0)
				continue;

			for (ParkingSlot parkingSlot : parkingArea.getFreeSlots()) {

				if (!(canPark(u, parkingSlot)))
					continue;

				long duration = getDuration(parkingSlot.getLocation(), d.getEntrance(), true);
				if (duration < minDuration) {
					$ = parkingSlot;
					minDuration = duration;
				}
			}
		}
		return $;
	}

	// returns the closest parking to the given faculty in the given parking
	// area
	public static ParkingSlot parkingSlotAtParkingArea(User u, ParkingArea a, Destination d)
			throws org.parse4j.ParseException {

		if (a.getNumOfFreeSlots() <= 0)
			return null;

		ParkingSlot $ = null;
		long minDuration = Integer.MAX_VALUE;

		for (ParkingSlot parkingSlot : a.getFreeSlots()) {

			if (!(canPark(u, parkingSlot)))
				continue;

			long duration = getDuration(parkingSlot.getLocation(), d.getEntrance(), true);
			if (duration < minDuration) {
				$ = parkingSlot;
				minDuration = duration;
			}
		}
		return $;
	}

	public static void parkAtSlot(User u, ParkingSlot s)
			throws NoSlotAvailable, org.parse4j.ParseException {
		if (s == null)
			throw new NoSlotAvailable("No Slot Available");
		s.changeStatus(ParkingSlotStatus.TAKEN);
		u.setCurrentParking(s);
	}

	public static ParkingSlot parkAtClosestSlot(User u, MapLocation currentLocation, ParkingAreas a,
			Destination d) throws NoSlotAvailable, org.parse4j.ParseException {
		ParkingSlot $ = closestParkingSlot(u, currentLocation, a, d);
		parkAtSlot(u, $);
		return $;

	}

	public static ParkingSlot parkAtArea(User u, ParkingArea a, Destination d)
			throws NoSlotAvailable, org.parse4j.ParseException {
		ParkingSlot $ = parkingSlotAtParkingArea(u, a, d);
		parkAtSlot(u, $);
		return $;
	}

}