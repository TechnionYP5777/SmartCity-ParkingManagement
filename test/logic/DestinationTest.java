package logic;

import data.members.*;
import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import Exceptions.*;
import manager.logic.*;

public class DestinationTest {
	
	
	// this test checks there is no destination in the db with the name taubTest123
	@Test
	public void destinationExistsTest1() {
		
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
	}
	
	// this test inserts a destination to the db and then checks that there is a dest with the name  taubTest123
	@Test
	public void destinationExistsTest2() {
		
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		Destination d = null;
		try{
			d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.assertEquals(Destination.destinationExists("taubTest123"), true);
			d.deleteParseObject();
			
		} catch( ParseException e){
			Assert.fail();
		} catch (AlreadyExists e){
			Assert.fail();
		}
	}
	
	/* this test shows that if  there is a destination with the same name, 
	 * an AlreadyExists exception will be thrown, hence preventing inserting another
	 * destination with the same name to the DB 
	 */
	@Test
	public void constructorTest1() {
		
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		Destination d = null;
		try{
			d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.assertEquals(Destination.destinationExists("taubTest123"), true);
			d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.fail();
			
		} catch( ParseException e){
			Assert.fail();
		} catch (AlreadyExists e){
			try{
				d.deleteParseObject();
			} catch(ParseException e2){
				Assert.fail();
			}
		}
	}
	
	/* this test shows that if there is a destination in the db with name X
	 * you have can initialize a Destination object with the data of X
	 */
	
	@Test
	public void constructorTest2() {
		
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		Destination d = null;
		Destination d2 = null;
		try{
			d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			d2 = new Destination("taubTest123");
			Assert.assertEquals(d.getEntrance().getLat(), d2.getEntrance().getLat(), 0);
			Assert.assertEquals(d.getEntrance().getLon(), d2.getEntrance().getLon(), 0);

			d.deleteParseObject();
			
		} catch( ParseException e){
			Assert.fail();
		} catch (NotExists e){
			Assert.fail();
		} catch (AlreadyExists e){
			Assert.fail();
		}
	}
}
