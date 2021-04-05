package com.fedex.eventhub.harmonizer.core;

import com.fedex.eventhub.harmonizer.base.ENSMessageHarmonizer;
import com.fedex.eventhub.harmonizer.base.ENSMessageReceiver;
import com.fedex.eventhub.harmonizer.base.ENSMessageTypeIdentifier;
import com.fedex.eventhub.harmonizer.base.EventHubDispatcher;
import com.fedex.eventhub.harmonizer.core.interfaces.IEventDataDispatcher;
import com.fedex.eventhub.harmonizer.core.interfaces.IEventHubMessageReceiver;
import com.fedex.eventhub.harmonizer.core.interfaces.IMessageHarmonizer;
import com.fedex.eventhub.harmonizer.core.interfaces.IMessageTypeIdentifier;
import com.fedex.eventhub.harmonizer.core.message.EventTypes;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;

public class EventHubMessageHarmonizerFactory {
	
	private static final String Event_Data_Source = System.getenv("evnthub_datasource");

	public static IMessageHarmonizer getHarmonizerInstance(MessageConfiguration messageConfiguration,
			EventTypes messageType) {
		// Based on the message type & configuration details, create Message Harminizer instance
        return new ENSMessageHarmonizer();
	}
	
	public static IEventHubMessageReceiver getEventHubMessageRecieverInstance() {
		// Based on the message type & configuration details, create Message Harminizer instance
        return new ENSMessageReceiver();
	}
	
	public static IMessageTypeIdentifier getMessageTypeIdentifierInstance() {
		// Based on the message type & configuration details, create Message Harminizer instance
        return new ENSMessageTypeIdentifier();
	}

	public static IEventDataDispatcher getEventDataDispatcherInstance() {
		// TODO Auto-generated method stub
		return new EventHubDispatcher();
	}

}
