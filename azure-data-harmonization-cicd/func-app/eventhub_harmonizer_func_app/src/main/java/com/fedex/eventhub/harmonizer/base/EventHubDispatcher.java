package com.fedex.eventhub.harmonizer.base;

import java.util.List;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.fedex.eventhub.harmonizer.cache.EventHubProducerClientPool;
import com.fedex.eventhub.harmonizer.core.EventHubMessageLogger;
import com.fedex.eventhub.harmonizer.core.interfaces.IEventDataDispatcher;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;

public class EventHubDispatcher implements IEventDataDispatcher {

	@Override
	public void dispatchPayload(List<EventData> eventDataList, MessageConfiguration messageConfiguration) {
		List<EventHubProducerClient> eventHubProducerClient = EventHubProducerClientPool.getEventHubProducerClient(messageConfiguration);
		
		eventHubProducerClient.stream()
					          .forEach(producerClient -> pushMessagesToEventHub(producerClient, eventDataList));
		
	}

	private void pushMessagesToEventHub(EventHubProducerClient producerClient, List<EventData> eventDataList) {
		
		final EventDataBatch batch = producerClient.createBatch();

		eventDataList.stream().forEach(eventData -> batch.tryAdd(eventData));
		
		// send the batch of events to the event hub
		producerClient.send(batch);
		EventHubMessageLogger.info("Message published successfully to secondary eventhub :: "+batch.getCount());
	}


}
