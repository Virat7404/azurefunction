package com.fedex.harmonizer.rawtransferfunction;

import com.microsoft.azure.functions.annotation.*;

import java.util.List;

import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Event Hub trigger.
 */
public class EventHubRawTransferFunction {
    /**
     * This function will be invoked when an event is received from Event Hub.
     */
    @FunctionName("EventHubRawTransferFunction")
    public void run(
            @EventHubTrigger(name = "primaryEvh", eventHubName = "", connection = "PrimaryEventHubConnectionString", consumerGroup = "$Default", cardinality = Cardinality.MANY, dataType = "string") List<String> messages,
            @EventHubOutput(name = "secondaryEvh", eventHubName = "", connection = "SecondaryEventHubConnectionString", dataType = "string") OutputBinding<List<String>> secondaryEvhBinding,
            final ExecutionContext context) {

        context.getLogger().info("Processing started for EventHubRawTransferFunction :: ");
        context.getLogger().info("Length:: " + messages.size());

        secondaryEvhBinding.setValue(messages);

    }
}
