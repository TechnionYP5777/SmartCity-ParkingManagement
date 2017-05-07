package util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class Log {
	public static final Logger LOGGER = Logger.getLogger(Log.class.getName());
	
//	private Log(){ // Only one instance of Log class is allowed 
//		FileHandler fhandler;
//		try {
//			fhandler = new FileHandler("src/main/java/util/Logfile.txt");
//			SimpleFormatter sformatter = new SimpleFormatter();
//			fhandler.setFormatter(sformatter);
//			LOGGER.addHandler(fhandler);
//		} catch (IOException | SecurityException e) {
//			e.printStackTrace();
//		}
//	} 
	
	static public void setup() throws IOException {

        // get the global logger to configure it
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        
        FileHandler fhandler;
        fhandler = new FileHandler("src/main/java/util/Logfile.txt");
		SimpleFormatter sformatter = new SimpleFormatter();
		fhandler.setFormatter(sformatter);
		logger.addHandler(fhandler);
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
