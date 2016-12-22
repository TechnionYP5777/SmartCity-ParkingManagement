package logic;

import data.members.*;
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
import java.util.Set;


public class Navigation {
	
	public static boolean canPark(User u, ParkingArea a){
		return a.getNumOfFreeSlots() > 0 && u.getSticker().ordinal() <= a.getColor().ordinal();
	}
	
	private static JSONObject getInnerJSON(String url){
		JSONParser parser = new JSONParser();
		try{
			
		 	return (JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) parser
					.parse(IOUtils.toString((new URL(url)), StandardCharsets.UTF_8))).get("rows")).get(0))
							.get("elements")).get(0);
            
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return null;
	}
	
	public static String createURL(MapLocation source, MapLocation target, boolean walkingMode){
		String $ = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins="
				+ (source.getLat() + "," + source.getLon()) + "&destinations=" + target.getLat() + "," + target.getLon()
				+ "&key=AIzaSyDw25loi0t1ms-bCuLPHS2Bm9aEIvyu9Wo";
		if(walkingMode)
			$ += "&mode=walking";
		
		return $;
	}
	
	public static long getDistance(MapLocation source, MapLocation target, boolean walkingMode){
		String url = createURL(source, target, walkingMode);
		JSONObject element = getInnerJSON(url);
		return element == null ? 0 : (long) ((JSONObject) element.get("distance")).get("value");
	}
	
	public static long getDuration(MapLocation source, MapLocation target, boolean walkingMode){
		String url = createURL(source, target, walkingMode);
		JSONObject element = getInnerJSON(url);
		return element == null ? 0 : (long) ((JSONObject) element.get("duration")).get("value");
	}
		
	public static int getClosestParkingArea(MapLocation currentLocation, boolean walkingMode){
		
		JSONParser parser = new JSONParser();
		try{
			JSONArray a = (JSONArray) parser.parse(new FileReader("./src/logic/parkingAreas.json"));
			int $ = -1;
			long dist = Integer.MAX_VALUE;
			for (Object o : a)
			{
				
				JSONObject parkingArea = (JSONObject) o;
				int id = Integer.parseInt((String) parkingArea.get("id"));
				double targetLat = Double.parseDouble((String) parkingArea.get("locationX"));
				double targetLon =  Double.parseDouble((String) parkingArea.get("locationY"));
				MapLocation target = new MapLocation(targetLat, targetLon);
				long d = getDistance(currentLocation, target, walkingMode);
				if (d < dist){
					$ = id;
					dist = d;
				}
			}
			return $;
			
		}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return -1; 
	}

	public static ParkingSlot closestParkingSlot(User u,MapLocation currentLocation, ParkingAreas a, Faculty f){

		Set<ParkingArea> areasSet = a.getParkingAreas();
		ParkingSlot $ = null;
		long duration = Integer.MAX_VALUE;
		
		for (ParkingArea parkingArea : areasSet) {
			if(!canPark(u, parkingArea))
				continue;
			
			for (ParkingSlot parkingSlot : parkingArea.getFreeSlots()) {
				long durationToSlot = getDuration(currentLocation, parkingSlot.getLocation(), false);
				long durationToFaculty = getDuration(parkingSlot.getLocation(), f.getEntrance(), true);
				if (durationToSlot + durationToFaculty < duration) {
					$ = parkingSlot;
					duration = durationToSlot + durationToFaculty;
				}
			}
		}
		
		return $;
	}
	
	// returns the closest parking to the given faculty in the given parking area
	public static ParkingSlot parkingSlotAtParkingArea(User u, ParkingArea a, Faculty f){
		if(!canPark(u, a))
			return null;
		
		ParkingSlot $ = null;
		long duration = Integer.MAX_VALUE;
		
		
		Set<ParkingSlot> freeSlots = a.getFreeSlots();
		for(ParkingSlot parkingSlot : freeSlots){
			long durationToFaculty = getDuration(parkingSlot.getLocation(), f.getEntrance() , true);
			if(durationToFaculty < duration){
				$ = parkingSlot;
				duration = durationToFaculty;
			}
		}
		return $;
	}
	
	public boolean parkAtSlot(ParkingSlot s){
		if (s.getStatus() != ParkingSlotStatus.FREE)
			return false;
		s.changeStatus(ParkingSlotStatus.TAKEN);
		return true;
	}
}




