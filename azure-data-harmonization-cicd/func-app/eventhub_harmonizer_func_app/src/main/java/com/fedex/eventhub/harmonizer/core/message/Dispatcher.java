package com.fedex.eventhub.harmonizer.core.message;

public class Dispatcher {
    public String eventHubConnectionString;
    public String eventHubNameString;
    public String getEventHubConnectionString() {
        return eventHubConnectionString;
    }
    public void setEventHubConnectionString(String eventHubConnectionString) {
        this.eventHubConnectionString = eventHubConnectionString;
    }
    public String getEventHubNameString() {
        return eventHubNameString;
    }
    public void setEventHubNameString(String eventHubNameString) {
        this.eventHubNameString = eventHubNameString;
    }
    @Override
    public String toString() {
        return "Dispatcher [eventHubConnectionString=" + eventHubConnectionString + ", eventHubNameString="
                + eventHubNameString + "]";
    }

    
}
