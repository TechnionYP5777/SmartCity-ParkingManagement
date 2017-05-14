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
}
