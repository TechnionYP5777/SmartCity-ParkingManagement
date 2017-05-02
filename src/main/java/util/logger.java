package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class logger {
	public static final Logger LOGGER = Logger.getLogger(logger.class.getName());
	
	private logger(){ // Only one instance of logger class is allowed 
		FileHandler fhandler;
		try {
			fhandler = new FileHandler("src/main/java/util/Logfile.txt");
			SimpleFormatter sformatter = new SimpleFormatter();
			fhandler.setFormatter(sformatter);
			LOGGER.addHandler(fhandler);
		} catch (IOException | SecurityException e) {
			e.printStackTrace();
		}
	} 
	
	public static void severe(String msg){
		LOGGER.severe(msg);
	}
	public static void warning(String msg){
		LOGGER.warning(msg);
	}
	public static void info(String msg){
		LOGGER.info(msg);
	}
	public static void config(String msg){
		LOGGER.config(msg);
	}
	public static void fine(String msg){
		LOGGER.fine(msg);
	}
	public static void finer(String msg){
		LOGGER.finer(msg);
	}
	public static void finest(String msg){
		LOGGER.finest(msg);
	}
}
