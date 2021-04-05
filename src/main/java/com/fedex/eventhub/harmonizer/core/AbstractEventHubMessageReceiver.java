package com.fedex.eventhub.harmonizer.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.eventhub.harmonizer.core.interfaces.IEventHubMessageReceiver;
import com.fedex.eventhub.harmonizer.core.message.EventHubMessage;

public abstract class AbstractEventHubMessageReceiver implements IEventHubMessageReceiver {

	@Override
	public List<EventHubMessage> parseRecievedEventHubMessage(List<String> eventHubMessages) {
		List<EventHubMessage> eventHubMessagesList = null;

		eventHubMessagesList = eventHubMessages.stream()
											   .map(eventHubMessage -> parseENSMessage(eventHubMessage))
											   .collect(Collectors.toList());
		
		return eventHubMessagesList;
	}
	
	protected abstract String getPayloadNode(JsonNode rootNode);
	
	private EventHubMessage parseENSMessage(String eventHubMessage) {
		String ens_payload = null;
		Map<String, Object> messageMetaDataMap;
		try {
			//System.out.println(eventHubMessage);

			JsonNode rootNode = new ObjectMapper().readTree(eventHubMessage);
			
			messageMetaDataMap = populateMetaDataMap(rootNode);
			
			ens_payload = pupulateMessageBody(rootNode);
			
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error parsing the incoming message", e);
		}
		
		return new EventHubMessage(messageMetaDataMap, ens_payload);
		
	}
	
	private String pupulateMessageBody(JsonNode rootNode) throws JsonMappingException, JsonProcessingException {

		JsonNode dataNode = rootNode.at(getPayloadNode(rootNode));
		
		return dataNode.asText();
	}
	
}
