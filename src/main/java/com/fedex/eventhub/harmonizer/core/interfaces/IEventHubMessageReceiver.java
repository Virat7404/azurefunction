package com.fedex.eventhub.harmonizer.core.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fedex.eventhub.harmonizer.core.message.EventHubMessage;

public interface IEventHubMessageReceiver {
	
	static final String MESSAGE_SOURCE_NODE = "/properties/MsgSource/string";
	
	static final String CREATE_TIMESTAMP_NODE = "/timestamp";

	public List<EventHubMessage> parseRecievedEventHubMessage(List<String> eventHubMessage);
	
	default Map<String, Object> populateMetaDataMap(JsonNode rootNode) {
				
		Map<String, Object> messageMetaDataMap = new HashMap<String, Object>();
		//schema-version
        String schemaVersion = "";
        messageMetaDataMap.put("schema-version", schemaVersion);
		
		//msg-create-time
		String timeStampStr = rootNode.at(CREATE_TIMESTAMP_NODE).asText();
		Date date =new Date(Long.parseLong(timeStampStr));
		String msgCreateTimeStamp = date.toString();
		messageMetaDataMap.put("msg-create-time", msgCreateTimeStamp);
		
		//hrmz-create-time
		String hrmzCreateTime = new Date(System.currentTimeMillis()).toString();
		messageMetaDataMap.put("hrmz-create-time", hrmzCreateTime);

		//msg-source
		String msgSource = rootNode.at(MESSAGE_SOURCE_NODE).asText();
		messageMetaDataMap.put("msg-source", msgSource);		
		
		return messageMetaDataMap;
	}
	
}
