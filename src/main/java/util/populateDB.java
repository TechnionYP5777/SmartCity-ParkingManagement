package util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.parse4j.ParseException;

import data.management.DatabaseManagerImpl;
import data.members.Area;
import data.members.MapLocation;
//import data.members.ParkingArea;
//import data.members.ParkingAreas;
//import data.members.ParkingAreasM;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

public class populateDB {

	public static void main(final String[] args) {
		System.out.println("Start Populating DB");
		try {
			insertParkingSlots();
		} catch (final Exception ¢) {
			System.out.println("Something went wrong. Could not add the parking slots");
			¢.printStackTrace();
		} /*
		try {
			insertParkingArea();
		} catch (final Exception ¢) {
			System.out.println("Something went wrong. Could not add the parking area");
			¢.printStackTrace();
		}
		try {
			insertParkingAreas();
		} catch (final Exception ¢) {
			System.out.println("Something went wrong. Could not add the parking areas");
			¢.printStackTrace();
		}*/
		System.out.println("Done Populating DB");

	}

	private static void insertParkingSlots() throws Exception {
		for (final String line : getLinesFromFile(new File("src/main/java/util/parkingSlots.txt").toPath())) {
			System.out.println("Inserting the following slot:");
			System.out.println(line);
			final String[] input = line.split(" ");
			if (input.length != 6)
				throw new IllegalArgumentException(
						"Input line should look like this: [name] [status] [currentColor] [defaultColor] [lat] [lon] [area]");
			if (!insertSlotToDB(input))
				throw new Exception("Something went wrong. Could not add the last slot");
		}
	}

	private static void insertParkingArea() throws Exception {
		for (final String line : getLinesFromFile(new File("src/main/java/util/parkingArea.txt").toPath())) {
			System.out.println("Inserting the following area:");
			System.out.println(line);
			final String[] input = line.split(" ");
			if (input.length < 5)
				throw new IllegalArgumentException(
						"Input line should look like this: [id] [name] [defaultColor] [slot0] [slot1] ... [slotn]");
			if (!insertAreaToDB(input))
				throw new Exception("Something went wrong. Could not add the last area");
		}

	}

	private static void insertParkingAreas() throws Exception {
		for (final String line : getLinesFromFile(new File("src/main/java/util/parkingAreas.txt").toPath())) {
			System.out.println("Inserting the following areas:");
			System.out.println(line);
			final String[] input = line.split(" ");
			if (input.length < 1)
				throw new IllegalArgumentException("Input line should look like this: [area0] [area1] ... [arean]");
			if (!insertAreasToDB(input))
				throw new Exception("Something went wrong. Could not add the areas");
		}

	}

	private static List<String> getLinesFromFile(final Path p) throws IOException {
		final Object[] fileArray = Files.readAllLines(p).toArray();
		final List<String> $ = new ArrayList<String>();
		for (final Object ¢ : fileArray)
			$.add((String) ¢);
		return $;
	}

	@SuppressWarnings("unused")
	private static Boolean insertSlotToDB(final String[] args) {
		final String name = args[0];
		final ParkingSlotStatus status = ParkingSlotStatus.valueOf(args[1]);
		//ADDED
		final Area area=Area.valueOf(args[6]);
		final int rating = Integer.valueOf(args[7]);
		final int numOfVoting = Integer.valueOf(args[8]);
		final StickersColor currentColor = StickersColor.valueOf(args[2]), defaultColor = StickersColor.valueOf(args[3]);
		final double lat = Double.parseDouble(args[4]), lon = Double.parseDouble(args[5]);
		try {
			final ParkingSlot slot1 = new ParkingSlot(name, status, currentColor, defaultColor, new MapLocation(lat, lon),
					new Date(),area, rating, numOfVoting,DatabaseManagerImpl.getInstance());
		} catch (final ParseException ¢) {
			¢.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private static Boolean insertAreaToDB(final String[] args) {
		final int id = Integer.parseInt(args[0]);
		final String name = args[1];
		final StickersColor defaultColor = StickersColor.valueOf(args[2]);
		final double lat = Double.parseDouble(args[3]), lon = Double.parseDouble(args[4]);
		final Area area=Area.valueOf(args[6]);
		final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		for (int nameIndex = 5; nameIndex < args.length; ++nameIndex)
			try {
				slots.add(new ParkingSlot(args[nameIndex], DatabaseManagerImpl.getInstance()));
			} catch (final ParseException ¢) {
				System.out.println("Something went wrong. Could not find the last slot in DB");
				¢.printStackTrace();
			}

		try {
			//final ParkingArea area2 = new ParkingArea(id, name, new MapLocation(lat, lon), slots, defaultColor);
		} catch (final Exception ¢) {
			¢.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private static Boolean insertAreasToDB(final String[] args) {
		/*inal Set<ParkingArea> area = new HashSet<ParkingArea>();
		for (final String arg : args)
			try {
				area.add(new ParkingArea(arg));
			} catch (final ParseException ¢) {
				System.out.println("Something went wrong. Could not find the last area in DB");
				¢.printStackTrace();
			}

		try {
			final ParkingAreas areas = new ParkingAreas(area);
		} catch (final ParseException ¢) {
			¢.printStackTrace();
			return false;
		}*/
		return true;
	}
}
