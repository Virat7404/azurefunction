package com.fedex.eventhub.harmonizer.core.interfaces;

import java.util.List;

import com.azure.messaging.eventhubs.EventData;
import com.fedex.eventhub.harmonizer.core.message.EventHubMessage;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;
import com.fedex.eventhub.harmonizer.exception.MessageHarmonizerException;

public interface IMessageHarmonizer {

	default void validateIncomingPayload(MessageConfiguration messageConfiguration, List<EventHubMessage> eventHubMessages) throws MessageHarmonizerException {
		// Optional. Place holder for future extension
	}

	List<EventData> format(MessageConfiguration messageConfiguration, List<EventHubMessage> eventHubMessages) throws MessageHarmonizerException;

	default void validateOutGoingPayload(MessageConfiguration messageConfiguration, List<String> jsonOutputPayloads) throws MessageHarmonizerException {
		// Optional. Place holder for future extension
	}

}
