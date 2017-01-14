package util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.parse4j.ParseException;

import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingAreas;
//import data.members.ParkingAreasM;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

public class populateDB {

	public static void main(String[] args) {
		System.out.println("Start Populating DB");
		try {
			//insertParkingSlots();
		} catch (Exception e) {
			System.out.println("Something went wrong. Could not add the parking slots");
			e.printStackTrace();
		}
		try {
			//insertParkingArea();
		} catch (Exception e) {
			System.out.println("Something went wrong. Could not add the parking area");
			e.printStackTrace();
		}
		try {
			insertParkingAreas();
		} catch (Exception e) {
			System.out.println("Something went wrong. Could not add the parking areas");
			e.printStackTrace();
		}
		System.out.println("Done Populating DB");

	}
	
	private static void insertParkingSlots() throws Exception{
		for (String line : getLinesFromFile((new File("src/util/parkingSlots.txt")).toPath())) {
			System.out.println("Inserting the following slot:");
			System.out.println(line);
			String[] input = line.split(" ");
			if (input.length != 6)
				throw new IllegalArgumentException(
						"Input line should look like this: [name] [status] [currentColor] [defaultColor] [lat] [lon]");
			if (!insertSlotToDB(input))
				throw new Exception("Something went wrong. Could not add the last slot");
		}
	}
	
	private static void insertParkingArea() throws Exception{
		for (String line : getLinesFromFile((new File("src/util/parkingArea.txt")).toPath())) {
			System.out.println("Inserting the following area:");
			System.out.println(line);
			String[] input = line.split(" ");
			if (input.length < 3)
				throw new IllegalArgumentException(
						"Input line should look like this: [id] [name] [defaultColor] [slot0] [slot1] ... [slotn]");
			if (!insertAreaToDB(input))
				throw new Exception("Something went wrong. Could not add the last area");
		}
		
	}
	
	private static void insertParkingAreas() throws Exception{
		for (String line : getLinesFromFile((new File("src/util/parkingAreas.txt")).toPath())) {
			System.out.println("Inserting the following areas:");
			System.out.println(line);
			String[] input = line.split(" ");
			if (input.length < 1)
				throw new IllegalArgumentException(
						"Input line should look like this: [area0] [area1] ... [arean]");
			if (!insertAreasToDB(input))
				throw new Exception("Something went wrong. Could not add the areas");
		}
		
	}
	
	private static List<String> getLinesFromFile(Path p) throws IOException{
		Object[] fileArray =  Files.readAllLines(p).toArray();
		List<String> lines = new ArrayList<String>();
		for (Object o : fileArray)
			lines.add((String) o);		
		return lines;
	}
	
	@SuppressWarnings("unused")
	private static Boolean insertSlotToDB(String[] args){
		String name = args[0];
		ParkingSlotStatus status = ParkingSlotStatus.valueOf(args[1]);
		StickersColor currentColor = StickersColor.valueOf(args[2]);
		StickersColor defaultColor = StickersColor.valueOf(args[3]);
		double lat = Double.parseDouble(args[4]);
		double lon = Double.parseDouble(args[5]);
		try {
			ParkingSlot slot1 = new ParkingSlot(name, status, currentColor, defaultColor, new MapLocation(lat, lon), new Date());
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	private static Boolean insertAreaToDB(String[] args){
		int id = Integer.parseInt(args[0]);
		String name = args[1];
		StickersColor defaultColor = StickersColor.valueOf(args[2]);

		Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
		for(int nameIndex = 3; nameIndex<args.length; ++nameIndex)
			try {
				slots.add((new ParkingSlot(args[nameIndex])));
			} catch (ParseException e) {
				System.out.println("Something went wrong. Could not find the last slot in DB");
				e.printStackTrace();
			}
		
		try {
			ParkingArea area = new ParkingArea(id, name, slots, defaultColor);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	private static Boolean insertAreasToDB(String[] args){
		Set<ParkingArea> area = new HashSet<ParkingArea>();
		for(int idIndex = 0; idIndex<args.length; ++idIndex)
			try {
				area.add((new ParkingArea(args[idIndex])));
			} catch (ParseException e) {
				System.out.println("Something went wrong. Could not find the last area in DB");
				e.printStackTrace();
			}
		
		try {
			ParkingAreas areas = new ParkingAreas(area);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
