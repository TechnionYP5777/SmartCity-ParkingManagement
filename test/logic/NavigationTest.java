package logic;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;

import Exceptions.AlreadyExists;
import data.members.Destination;
import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingAreas;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;
import data.members.User;

public class NavigationTest {

	@Test
	public void getDistanceTest() {
		Assert.assertEquals(532, Navigation.getDistance(new MapLocation(32.777552, 35.020578),
				new MapLocation(32.778761, 35.016469), false));
	}

	@Test
	public void getDurationTest() {
		Assert.assertEquals(397, Navigation.getDuration(new MapLocation(32.777552, 35.020578),
				new MapLocation(32.778761, 35.016469), true));
	}

	@Test
	public void parkingSlotAtParkingAreaTest() {
		try {
			MapLocation location = new MapLocation(32.777408, 35.020332); // farest
			final ParkingSlot taubSlot1 = new ParkingSlot("upperTaub-slot1", ParkingSlotStatus.FREE, StickersColor.RED,
					StickersColor.RED, location, new Date());
			location = new MapLocation(32.777223, 35.020890); // middle
			final ParkingSlot taubSlot2 = new ParkingSlot("upperTaub-slot2", ParkingSlotStatus.FREE, StickersColor.RED,
					StickersColor.RED, location, new Date());
			location = new MapLocation(32.777195, 35.021281); // closest
			final ParkingSlot taubSlot3 = new ParkingSlot("upperTaub-slot3", ParkingSlotStatus.FREE, StickersColor.RED,
					StickersColor.RED, location, new Date());

			final Set<ParkingSlot> taubSlots = new HashSet<ParkingSlot>();
			taubSlots.add(taubSlot1);
			taubSlots.add(taubSlot2);
			taubSlots.add(taubSlot3);

			location = new MapLocation(32.777466, 35.021094);
			Destination destination = null;
			try {
				destination = new Destination("Taub-NavigationTest", location);
			} catch (final AlreadyExists ¢) {
				¢.printStackTrace();
				Assert.fail();
			}

			final ParkingArea upperTaubArea = new ParkingArea(100, "t1", new MapLocation(0, 0), taubSlots, StickersColor.RED);
			try {

				final User user = new User("Navigation Tester", "1234", "0547456382", "1188999", " tester@gmail.com",
						StickersColor.RED, null);
				ParkingSlot result = Navigation.parkingSlotAtParkingArea(user, upperTaubArea, destination);
				Assert.assertEquals(taubSlot3.getName(), result.getName());

				taubSlot3.changeStatus(ParkingSlotStatus.TAKEN);

				// now result 2 is the closest

				result = Navigation.parkingSlotAtParkingArea(user, upperTaubArea, destination);
				Assert.assertEquals(taubSlot2.getName(), result.getName());

				upperTaubArea.deleteParseObject();
				taubSlot1.deleteParseObject();
				taubSlot2.deleteParseObject();
				taubSlot3.deleteParseObject();
				destination.deleteParseObject();
				user.deleteParseObject();

			} catch (final ParseException ¢) {
				¢.printStackTrace();
				Assert.fail();
			}

		} catch (final ParseException ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void closestParkingSlotTest() {

		try {

			// upper taub area + slots

			MapLocation location = new MapLocation(32.777408, 35.020332); // farest
			final ParkingSlot taubSlot1 = new ParkingSlot("upperTaub-slot1", ParkingSlotStatus.FREE, StickersColor.RED,
					StickersColor.RED, location, new Date());
			location = new MapLocation(32.777223, 35.020890); // middle
			final ParkingSlot taubSlot2 = new ParkingSlot("upperTaub-slot2", ParkingSlotStatus.FREE, StickersColor.RED,
					StickersColor.RED, location, new Date());
			location = new MapLocation(32.777195, 35.021281); // closest
			final ParkingSlot taubSlot3 = new ParkingSlot("upperTaub-slot3", ParkingSlotStatus.FREE, StickersColor.RED,
					StickersColor.RED, location, new Date());

			final Set<ParkingSlot> taubSlots = new HashSet<ParkingSlot>();
			taubSlots.add(taubSlot1);
			taubSlots.add(taubSlot2);
			taubSlots.add(taubSlot3);

			final ParkingArea upperTaubArea = new ParkingArea(100, "t1", new MapLocation(0, 0), taubSlots, StickersColor.RED);

			// nesher area + slots

			location = new MapLocation(32.774596, 35.029031);
			final ParkingSlot nesherSlot1 = new ParkingSlot("nesher-slot1", ParkingSlotStatus.FREE, StickersColor.WHITE,
					StickersColor.WHITE, location, new Date());

			final Set<ParkingSlot> nesherSlots = new HashSet<ParkingSlot>();
			nesherSlots.add(nesherSlot1);

			final ParkingArea nesherArea = new ParkingArea(101, "t1", new MapLocation(0, 0), nesherSlots,
					StickersColor.WHITE);

			// pool area + slots

			location = new MapLocation(32.778782, 35.016993); // farest
			final ParkingSlot poolSlot1 = new ParkingSlot("pool-slot1", ParkingSlotStatus.FREE, StickersColor.BLUE,
					StickersColor.BLUE, location, new Date());
			location = new MapLocation(32.778818, 35.019418); // closest
			final ParkingSlot poolSlot2 = new ParkingSlot("pool-slot2", ParkingSlotStatus.FREE, StickersColor.BLUE,
					StickersColor.BLUE, location, new Date());

			final Set<ParkingSlot> poolSlots = new HashSet<ParkingSlot>();
			poolSlots.add(poolSlot1);
			poolSlots.add(poolSlot2);

			final ParkingArea poolArea = new ParkingArea(102, "t1", new MapLocation(0, 0), poolSlots, StickersColor.BLUE);

			final Set<ParkingArea> areas = new HashSet<ParkingArea>();
			areas.add(upperTaubArea);
			areas.add(nesherArea);
			areas.add(poolArea);

			final ParkingAreas parkingAreas = new ParkingAreas(areas);

			location = new MapLocation(32.777466, 35.021094);
			Destination destination = null;
			try {
				destination = new Destination("Taub-NavigationTest", location);
			} catch (final AlreadyExists ¢) {
				¢.printStackTrace();
				Assert.fail();
			}

			try {

				final User user = new User("Navigation Tester", "1234", "0547456382", "1188999", " tester@gmail.com",
						StickersColor.BLUE, null);

				// taub slots are the closest but since the area is RED and
				// user'S sticker is BLUE taub slots won't be checked
				Assert.assertEquals(poolSlot2.getName(),
						Navigation.closestParkingSlot(user, location, parkingAreas, destination).getName());

				upperTaubArea.deleteParseObject();
				taubSlot1.deleteParseObject();
				taubSlot2.deleteParseObject();
				taubSlot3.deleteParseObject();

				nesherArea.deleteParseObject();
				nesherSlot1.deleteParseObject();

				poolArea.deleteParseObject();
				poolSlot1.deleteParseObject();
				poolSlot2.deleteParseObject();
				parkingAreas.deleteParseObject();

				destination.deleteParseObject();
				user.deleteParseObject();

			} catch (final ParseException ¢) {
				¢.printStackTrace();
				Assert.fail();
			}

		} catch (final ParseException ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}

	// the parkAtClosestSlot test is similar so I'll check only one parkAtArea
	@Test
	public void parkAtAreaTest() {
		try {
			MapLocation location = new MapLocation(32.777408, 35.020332);
			final ParkingSlot taubSlot1 = new ParkingSlot("upperTaub-slot1", ParkingSlotStatus.FREE, StickersColor.RED,
					StickersColor.RED, location, new Date());

			final Set<ParkingSlot> slots = new HashSet<ParkingSlot>();
			slots.add(taubSlot1);

			final ParkingArea upperTaubArea = new ParkingArea(100, "t1", new MapLocation(0, 0), slots, StickersColor.RED);

			location = new MapLocation(32.777466, 35.021094);
			Destination destination = null;
			try {
				destination = new Destination("Taub-NavigationTest", location);
			} catch (final AlreadyExists ¢) {
				¢.printStackTrace();
				Assert.fail();
			}
			User user = null;
			try {
				user = new User("Navigation Tester", "1234", "0547456382", "1188999", " tester@gmail.com",
						StickersColor.RED, null);
				Navigation.parkAtArea(user, upperTaubArea, destination);
				Assert.assertEquals(user.getCurrentParking().getLocation().getLat(), taubSlot1.getLocation().getLat(),
						0);
				Assert.assertEquals(user.getCurrentParking().getLocation().getLon(), taubSlot1.getLocation().getLon(),
						0);
				Assert.assertEquals(user.getCurrentParking().getStatus().ordinal(), taubSlot1.getStatus().ordinal());
				Assert.assertEquals(user.getCurrentParking().getStatus(), ParkingSlotStatus.TAKEN);
			} catch (final Exception ¢) {
				¢.printStackTrace();
				Assert.fail();
			}

			try {
				// the NoSlotAvailable exception should be thrown
				Navigation.parkAtArea(user, upperTaubArea, destination);
				Assert.fail();
			} catch (final Exception ¢) {
				Assert.assertEquals(¢.getClass().getSimpleName(), "NoSlotAvailable");
				user.setCurrentParking(null);
				destination.deleteParseObject();
				upperTaubArea.deleteParseObject();
				taubSlot1.deleteParseObject();
				user.deleteParseObject();
			}

		} catch (final ParseException ¢) {
			¢.printStackTrace();
			Assert.fail();
		}
	}
}
