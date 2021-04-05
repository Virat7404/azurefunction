package com.fedex.eventhub.harmonizer.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fedex.eventhub.harmonizer.core.AbstractEventHubMessageReceiver;
import com.fedex.eventhub.harmonizer.core.interfaces.IEventHubMessageReceiver;

public class ENSMessageReceiver extends AbstractEventHubMessageReceiver implements IEventHubMessageReceiver {
	
	private static final String ENS_PAYLOAD_NODE = "/properties/metricsStr/string";
	

	protected String getPayloadNode(JsonNode rootNode) {
		return ENS_PAYLOAD_NODE;
	}

}
