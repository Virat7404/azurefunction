package com.fedex.eventhub.harmonizer.boot;

import com.fedex.eventhub.harmonizer.cache.ConfigStore;
import com.fedex.eventhub.harmonizer.core.EventHubMessageLogger;

public class FunctionAppBootUp {

    static {
        // create the boot up script
        try {
            System.out.println("Boot Up Start");
            ConfigStore.bootUpConfigStore();
            System.out.println("Boot Up Stop");
        } catch (Exception e) {
            // TODO Handle the exception
            e.printStackTrace();
        }

    }
    
}
