package util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UseLogger {
  	 private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	 public void doSomeThingAndLog() {
				 
         // set the LogLevel to Severe, only severe Messages will be written
         LOGGER.setLevel(Level.SEVERE);
         LOGGER.severe("Info Log");
         LOGGER.warning("Info Log");
         LOGGER.info("Info Log");
         LOGGER.finest("Really not important");

         // set the LogLevel to Info, severe, warning and info will be written
         // finest is still not written
         LOGGER.setLevel(Level.INFO);
         LOGGER.severe("Info Log");
         LOGGER.warning("Info Log");
         LOGGER.info("Info Log");
         LOGGER.finest("Really not important");
         
         // set the LogLevel to Info, severe, warning and info will be written
         // all is written
         LOGGER.setLevel(Level.FINEST);
         LOGGER.severe("Info Log");
         LOGGER.warning("Info Log");
         LOGGER.info("Info Log");
         LOGGER.finest("Really not important");
	 }
	 
	 public static void main(String[] args) {
         UseLogger tester = new UseLogger();
         try {
                 Log.setup();
         } catch (IOException e) {
                 e.printStackTrace();
                 throw new RuntimeException("Problems with creating the log files");
         }
         tester.doSomeThingAndLog();
	 }
	
}
