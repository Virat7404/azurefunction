package com.fedex.eventhub.harmonizer.core.message;

import java.util.Map;

import com.fedex.eventhub.harmonizer.core.EventHubMessageLogger;


public class EventHubMessage {
    
    private Map<String, Object> messageMetaDataProps;

    private String messagePayload;

    public EventHubMessage(Map<String, Object> messageMetaDataProps, String messagePayload) {
        this.messageMetaDataProps = messageMetaDataProps;
        this.messagePayload = messagePayload;
    }

    public Map<String, Object> getMessageMetaDataProps() {
		return messageMetaDataProps;
	}

	public String getMessagePayload() {
    System.out.println("Message in getMessagePayload :: "+ messagePayload);
    return messagePayload;
    }

}
