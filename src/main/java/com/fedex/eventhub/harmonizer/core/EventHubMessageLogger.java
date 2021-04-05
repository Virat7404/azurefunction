package com.fedex.eventhub.harmonizer.core;

import java.util.logging.Logger;

public class EventHubMessageLogger {

	private static Logger FunctionApp_Logger;

	public static void setLogger(Logger logger) {
		FunctionApp_Logger = logger;	
	}
	
	public static void info(String msg) {
		if(FunctionApp_Logger != null) FunctionApp_Logger.info(msg);
	}

}
