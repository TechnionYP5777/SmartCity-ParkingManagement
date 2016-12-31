package logic;

import data.members.*;
import org.junit.Assert;
import org.junit.Test;
import org.parse4j.ParseException;
import Exceptions.*;
import manager.logic.*;

public class DestinationTest {
	
	
	@Test
	public void destinationExistsTest1() {
		
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
	}
	
	@Test
	public void destinationExistsTest2() {
		
		Assert.assertEquals(Destination.destinationExists("taubTest123"), false);
		Destination d = null;
		try{
			d = new Destination("taubTest123", new MapLocation(32.778069, 35.021935));
			Assert.assertEquals(Destination.destinationExists("taubTest123"), true);
			d.deleteParseObject();
			
		} catch( ParseException e){
			Assert.assertEquals(true, false);
		} catch (AlreadyExists e){
			Assert.assertEquals(true, false);
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
			Assert.assertEquals(true, false);	
			
		} catch( ParseException e){
			Assert.assertEquals(true, false);
		} catch (AlreadyExists e){
			Assert.assertEquals(true, true);
			try{
				d.deleteParseObject();
			} catch(ParseException e2){
				System.out.print("parse ecxeption");
			}
		}
	}
	
}
