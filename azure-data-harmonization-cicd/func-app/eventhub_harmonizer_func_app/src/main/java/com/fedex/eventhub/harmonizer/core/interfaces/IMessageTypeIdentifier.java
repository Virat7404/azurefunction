package com.fedex.eventhub.harmonizer.core.interfaces;

import com.fedex.eventhub.harmonizer.core.message.EventHubMessage;
import com.fedex.eventhub.harmonizer.core.message.EventTypes;

public interface IMessageTypeIdentifier {

	EventTypes getEventHubType(EventHubMessage eventHubMessage);

}
