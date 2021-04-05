package com.fedex.eventhub.harmonizer.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.azure.messaging.eventhubs.EventData;
import com.microsoft.azure.functions.OutputBinding;

public class EventHubMessageProcessFailureHandler {

	public static void redirectMessagesToErrorEventHub(Throwable error, List<String> messages,
			String functionName, OutputBinding<List<EventData>> errorEventHub_dispatch_binder) {
		
		String errorStackTrace = getErrorStackTrace(error);
		
		List<EventData> eventDataList = messages.stream()
		        			.map(message -> createEventData(message, functionName, errorStackTrace))
		        			.collect(Collectors.toList());
		        
		
		errorEventHub_dispatch_binder.setValue(eventDataList);
		
	}
	
	private static EventData createEventData(String message, String functionName, String errorStackTrace) {
		EventData eventData = new EventData(message);
		
		eventData.getProperties().put("Error-Stack", errorStackTrace);
		eventData.getProperties().put("Function-Name", functionName);
		
		String errorCreateTime = new Date(System.currentTimeMillis()).toString();
		
		eventData.getProperties().put("Error-Timestamp", errorCreateTime);
		
		return eventData;
	}

	private static String getErrorStackTrace(Throwable error) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		error.printStackTrace(pw);
		
		return sw.toString();
	}

}
