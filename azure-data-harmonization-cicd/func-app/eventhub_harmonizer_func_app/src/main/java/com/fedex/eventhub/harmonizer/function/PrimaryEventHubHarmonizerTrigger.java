package com.fedex.eventhub.harmonizer.function;


import java.util.List;

import com.azure.messaging.eventhubs.EventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fedex.eventhub.harmonizer.cache.ConfigStore;
import com.fedex.eventhub.harmonizer.core.EventHubMessageLogger;
import com.fedex.eventhub.harmonizer.core.EventHubMessageProcessor;
import com.fedex.eventhub.harmonizer.exception.EventHubMessageProcessFailureHandler;
import com.fedex.eventhub.harmonizer.exception.MessageHarmonizerException;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.Cardinality;
import com.microsoft.azure.functions.annotation.EventHubOutput;
import com.microsoft.azure.functions.annotation.EventHubTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

/**
 * Azure Functions with Event Hub trigger.
 */
public class PrimaryEventHubHarmonizerTrigger {

    static {
        System.out.println("Boot Up the Code here............");
        try {
            ConfigStore.bootUpConfigStore();
        } catch (Exception e) {
            // TODO Handle exception
            e.printStackTrace();
        }
    }
    /**
     * This function will be invoked when an event is received from Event Hub.
     * @throws JsonProcessingException
     * @throws JsonMappingException
     */
    @FunctionName("PrimaryEventHubHarmonizerTrigger")
    public void run(
        @EventHubTrigger(name = "message", eventHubName = "%ens_primary_eventhub_name%", connection = "ens_primary_eventhub_connectionkey", consumerGroup = "$Default", cardinality = Cardinality.MANY, dataType = "string") List<String> messages,
        @EventHubOutput(name = "errorEH", eventHubName = "%error_eventhub_name%", connection = "error_eventhub_connectionkey") OutputBinding<List<EventData>> errorEventHub_dispatch_binder,
        final ExecutionContext context
    ) throws JsonMappingException, JsonProcessingException ,MessageHarmonizerException{
        
    	EventHubMessageLogger.setLogger(context.getLogger());
    	
    	EventHubMessageLogger.info("Java Event Hub trigger function executed.");
    	EventHubMessageLogger.info("Length:" + messages.size());
    	//EventHubMessageLogger.info("Incoming Message :: *********************** "+messages.get(0));
        
        try {
        	EventHubMessageProcessor messageProcessor = new EventHubMessageProcessor();
			messageProcessor.processAndDispatchMessages(messages);
		} catch (MessageHarmonizerException e) {			
			EventHubMessageProcessFailureHandler.redirectMessagesToErrorEventHub(e, messages, context.getFunctionName(), errorEventHub_dispatch_binder);
		} catch (Exception e) {			
			EventHubMessageProcessFailureHandler.redirectMessagesToErrorEventHub(e, messages, context.getFunctionName(), errorEventHub_dispatch_binder);
	    } 
    }
}
