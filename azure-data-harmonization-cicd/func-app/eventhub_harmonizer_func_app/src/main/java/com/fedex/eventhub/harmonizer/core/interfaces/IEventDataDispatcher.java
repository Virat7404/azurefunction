package com.fedex.eventhub.harmonizer.core.interfaces;

import java.util.List;

import com.azure.messaging.eventhubs.EventData;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;

public interface IEventDataDispatcher {

	void dispatchPayload(List<EventData> eventDataList, MessageConfiguration messageConfiguration);

}
