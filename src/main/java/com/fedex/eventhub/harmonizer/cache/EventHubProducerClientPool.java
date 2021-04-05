package com.fedex.eventhub.harmonizer.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;

public class EventHubProducerClientPool {

	private static EventHubProducerClientPool instance;

	private static Object lockObject = new Object();

	private Map<String, List<EventHubProducerClient>> eventHubProducerClientMap;

	private EventHubProducerClientPool() {
		eventHubProducerClientMap = new ConcurrentHashMap<>();
	}

	private static EventHubProducerClientPool getInstance() {
		EventHubProducerClientPool result = instance;
		if (result == null) {
			synchronized (lockObject) {
				result = instance;
				if (result == null)
					instance = result = new EventHubProducerClientPool();
			}
		}
		return result;
	}

	public static List<EventHubProducerClient> getEventHubProducerClient(MessageConfiguration messageConfiguration) {
		EventHubProducerClientPool eventHubProducerClientPoolInstance = getInstance();
		String messageConfigKey = messageConfiguration.getDatasource() + "_" + messageConfiguration.getVersion();
		List<EventHubProducerClient> producerList = null;
		producerList = eventHubProducerClientPoolInstance.getEventHubProducerClientList(messageConfigKey);

		if (producerList == null) {
			producerList = eventHubProducerClientPoolInstance.createAndCacheProducerClientPool(messageConfiguration);
		}

		return producerList;

	}

	private List<EventHubProducerClient> createAndCacheProducerClientPool(MessageConfiguration messageConfiguration) {

		/*
		 * List<EventHubProducerClient> producerList = new ArrayList<>(); for
		 * (Dispatcher dispatcher : messageConfiguration.getDispacher()) {
		 * System.out.println("connectionstring: ***" +
		 * dispatcher.getEventHubConnectionString());
		 * System.out.println("eventname: ***" + dispatcher.getEventHubNameString());
		 * producerList.add(createEventHubProducerClient(dispatcher.
		 * getEventHubConnectionString(), dispatcher.getEventHubNameString())); }
		 */
		
		List<EventHubProducerClient> producerList = messageConfiguration.getDispacher()
																		.stream()
				                                                        .map(dispatcher -> createEventHubProducerClient(
				                                                        		                 dispatcher.getEventHubConnectionString(),
					                                                                             dispatcher.getEventHubNameString()))
				                                                        .collect(Collectors.toList());

		String messageConfigKey = messageConfiguration.getDatasource() + "_" + messageConfiguration.getVersion();
		eventHubProducerClientMap.put(messageConfigKey, producerList);

		return producerList;
	}

	private EventHubProducerClient createEventHubProducerClient(String eventHubConnectionString, String eventHubName) {
		return new EventHubClientBuilder().connectionString(eventHubConnectionString, eventHubName)
				.buildProducerClient();

	}

	private List<EventHubProducerClient> getEventHubProducerClientList(String messageConfigKey) {
		return eventHubProducerClientMap.get(messageConfigKey);
	}

}
