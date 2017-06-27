package util;

import org.parse4j.ParseGeoPoint;

public class Distance {
	public static double CalcLineDistance(ParseGeoPoint p1, ParseGeoPoint p2) {

		double lat1 = p1.getLatitude();
		double lat2 = p2.getLatitude();
		double lon1 = p1.getLongitude();
		double lon2 = p2.getLongitude();
	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);

	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return distance;
	}
}
