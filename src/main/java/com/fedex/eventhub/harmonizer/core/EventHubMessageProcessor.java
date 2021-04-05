package com.fedex.eventhub.harmonizer.core;

import java.util.List;
import java.util.stream.Collectors;

import com.azure.messaging.eventhubs.EventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fedex.eventhub.harmonizer.cache.ConfigStore;
import com.fedex.eventhub.harmonizer.core.interfaces.IEventDataDispatcher;
import com.fedex.eventhub.harmonizer.core.interfaces.IMessageHarmonizer;
import com.fedex.eventhub.harmonizer.core.message.EventHubMessage;
import com.fedex.eventhub.harmonizer.core.message.EventTypes;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;
import com.fedex.eventhub.harmonizer.exception.MessageHarmonizerException;

/**
 * 
 * EventHub Message Processor 
 * 
 * 
 * @author 
 *
 */
public class EventHubMessageProcessor {
	
	public EventHubMessageProcessor() {
		super();
	}

    public void processAndDispatchMessages(List<String> messages) throws MessageHarmonizerException, JsonMappingException, JsonProcessingException {
    	
    	if (messages == null || messages.size() == 0) return ;

            // peak into message & split into hearder & content payload
            // EventHubMessage contains Header & Payload details seperately
          List<EventHubMessage> eventhubMessages = EventHubMessageHarmonizerFactory.
															 getEventHubMessageRecieverInstance().
															    parseRecievedEventHubMessage(messages);
            
          // Identify the Message Type
          EventTypes messageType = EventHubMessageHarmonizerFactory.
            		 						getMessageTypeIdentifierInstance().
            		 						   getEventHubType(eventhubMessages.get(0));
            
          //Based on Message Type & version , identify the configuration file
          //Get the configuration details from Redis/CosmoDB/In-memory cache
          //FIXME: Remove this hard-coding
          String messageConfigKey = "ENS_1.0";
          
          MessageConfiguration messageConfiguration = ConfigStore.getInstance().
        		                                                     getConfigurationDetailsForEventType(messageConfigKey);
            
          // Get the Harmonizer Instance based on type 
          IMessageHarmonizer messageHarmonizer = EventHubMessageHarmonizerFactory.getHarmonizerInstance(messageConfiguration, messageType);
    	  
          // Validate the payload against the schema specified in the Message Configuration
          //  throws MessageHarmonizerException; log the error & redirect the payload to error event hub
          
          
			/*
			 * List<String> incomingPayLoads = eventhubMessages.stream() .map(msg ->
			 * msg.getMessagePayload()) .collect(Collectors.toList());
			 */
          
          messageHarmonizer.validateIncomingPayload(messageConfiguration, eventhubMessages);

          // Convert the Input Payload into new JSON format
          //  throws MessageHarmonizerException; log the error & redirect the payload to error event hub
          List<EventData> eventDataList = messageHarmonizer.format(messageConfiguration, eventhubMessages);

          // Validate the formated json payload against schema definition in the MessageConfiguration
          //  throws MessageHarmonizerException; log the error & redirect the payload to error event hub
          //messageHarmonizer.validateOutGoingPayload(messageConfiguration, jsonOutputPayloads);


    	
        // Dispatch the Payload to dispatcher event hub
    	IEventDataDispatcher eventDataDispatcher = EventHubMessageHarmonizerFactory.getEventDataDispatcherInstance();
    	eventDataDispatcher.dispatchPayload(eventDataList, messageConfiguration); 
        
    }
    
}
