package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class Log {
	public static final Logger LOGGER = Logger.getLogger(Log.class.getName());
	
	public static void setup() throws IOException {
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		FileHandler fhandler = new FileHandler("src/main/java/util/Logfile.txt");
		SimpleFormatter sformatter = new SimpleFormatter();
		fhandler.setFormatter(sformatter);
		logger.addHandler(fhandler);
	}
	public static void setLevel(Level l){
        LOGGER.setLevel(l);
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
