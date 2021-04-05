package com.fedex.eventhub.harmonizer.base;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.azure.messaging.eventhubs.EventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.eventhub.harmonizer.base.bean.ENSData;
import com.fedex.eventhub.harmonizer.core.EventHubMessageLogger;
import com.fedex.eventhub.harmonizer.core.interfaces.IMessageHarmonizer;
import com.fedex.eventhub.harmonizer.core.message.EventHubMessage;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;
import com.fedex.eventhub.harmonizer.exception.MessageHarmonizerException;

public class ENSMessageHarmonizer implements IMessageHarmonizer {

	@Override
	public List<EventData> format(MessageConfiguration messageConfiguration, List<EventHubMessage> eventHubMessages) throws MessageHarmonizerException {
		// TODO Auto-generated method stub
		final String incomingPayloadSchema = messageConfiguration.getIncomingPayloadSchema();
		
		final String seperator = messageConfiguration.getSeperator();
		//EventHubMessageLogger.info("seperator :: ********** "+seperator);
		List<EventData> eventDataList = eventHubMessages.stream()
		               			.map(messagePayload -> parseAndConvertENSDataPayLoad(messagePayload, incomingPayloadSchema, seperator))
		               			.collect(Collectors.toList());
		
		//Optional validation:
		/*
		 * List<String> jsonOutputPayloads = eventDataList.stream() .map(eventData ->
		 * eventData.getBodyAsString()) .collect(Collectors.toList());
		 * 
		 * validateOutGoingPayload(messageConfiguration, jsonOutputPayloads);
		 */
		
		
		return eventDataList;
	}

	
	private EventData parseAndConvertENSDataPayLoad(EventHubMessage eventHubMessage, String schema, String seperator) {
		
		String ensPayLoad = eventHubMessage.getMessagePayload();
		System.out.println("ensPayLoad :: "+ensPayLoad);
		System.out.println("schema :: "+schema);
	
		String[] fields = schema.split(Pattern.quote(seperator),-1);
		
		
		//EventHubMessageLogger.info("fields :: *************** "+fields.toString() + "  ** Size ** " + fields.length);
		
		String[] values = ensPayLoad.split(Pattern.quote(seperator),-1);
		
		
		//EventHubMessageLogger.info("values :: *************** "+values.toString()+ "  ** Size ** " + values.length);
		
		String jsonPayLoad = "";
		
		EventData eventData = null;
		
		
		if(values.length != fields.length) {
			throw new RuntimeException("Parsing Failed.....Wrong Data");
		}else{
			//EventHubMessageLogger.info("values seperated :: ");
		}
		
	
		Map<String, String> dataMap = IntStream.range(0, values.length).boxed()
			    .collect(Collectors.toMap(i -> fields[i], i -> values[i]));
		
		
        ObjectMapper mapper = new ObjectMapper();
        ENSData ensData = mapper.convertValue(dataMap, ENSData.class);
        System.out.println("ENSData object :: "+ensData);
        try {
			jsonPayLoad = mapper.writeValueAsString(ensData);
			//EventHubMessageLogger.info("Message to publish to secondary eventhub :: "+jsonPayLoad);
			eventData = new EventData(jsonPayLoad);
	        eventData.getProperties().putAll(eventHubMessage.getMessageMetaDataProps());
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to harmonize");
		}
        
        return eventData;
        
	}

}
