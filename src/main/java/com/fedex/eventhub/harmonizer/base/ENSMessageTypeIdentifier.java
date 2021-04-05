package com.fedex.eventhub.harmonizer.base;

import com.fedex.eventhub.harmonizer.core.interfaces.IMessageTypeIdentifier;
import com.fedex.eventhub.harmonizer.core.message.EventHubMessage;
import com.fedex.eventhub.harmonizer.core.message.EventTypes;

public class ENSMessageTypeIdentifier implements IMessageTypeIdentifier {

	@Override
	public EventTypes getEventHubType(EventHubMessage eventHubMessage) {
		// TODO Auto-generated method stub
		return EventTypes.Delimited;
	}

}
