package com.fedex.eventhub.harmonizer.base;

import java.util.HashMap;

public final class EventHubDispatchDetails {
	
	private static HashMap<String, String> DISPACHER_MAP;
	
	static {
		loadDispatcherDetailsFromFunctionAppSetting();
	}

	private static void loadDispatcherDetailsFromFunctionAppSetting() {
		DISPACHER_MAP = new HashMap<>();
		
		// populate the dispacher map from System.getEnv("dispacher details")
		
	}
	
	public static HashMap<String, String> getDispacherMap() {
		return DISPACHER_MAP;
	}

}
