package com.fedex.eventhub.harmonizer.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;
import com.fedex.eventhub.harmonizer.exception.MessageHarmonizerException;

public class ConfigStore {
	
	private static long Cache_Refresh_Frequency = 60 * 60 * 24 * 1000L;/* Once per day */

    private static ConfigStore instance;

    private static Object lockObject = new Object();

    private Map<String, MessageConfiguration> configMap;
    
    //FIXME: Need to re-work on this logic
    static {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          public void run() {
            synchronized(ConfigStore.class) {
              instance = null;
            }
          }
        }, Cache_Refresh_Frequency /* Once per day */);
      }

    private ConfigStore() throws JsonMappingException, JsonProcessingException {
        this.configMap = this.loadConfigMapFromAzureStorage();
    }

    public static ConfigStore getInstance() throws JsonMappingException, JsonProcessingException {
		ConfigStore result = instance;
		if (result == null) {
			synchronized (lockObject) {
				result = instance;
				if (result == null)
					instance = result = new ConfigStore();
			}
		}
		return result;
	}

    public static void bootUpConfigStore() throws Exception {
        ConfigStore _inst = getInstance();
        if (_inst == null) throw new Exception("Config Store Boot Up Failed");
    }


    private Map<String, MessageConfiguration> loadConfigMapFromAzureStorage() throws JsonMappingException, JsonProcessingException {
        Map<String, MessageConfiguration> configMapForCache = new ConcurrentHashMap<>();

        System.out.println("Load the Config files into the In-memory cache");
        // Implementation to read the configuration file based on Azure Function Setting
        // Folders/File to load into cache will define in the Azure Function setting files
        
        //FIXME : Remove this temp & replace with Azure Storage Reader

        List<MessageConfiguration> messageConfigurationList =  AzureStorageReader.getMessageConfiguration();
        messageConfigurationList.stream().forEach(msgConfig -> {
            String key= msgConfig.getDatasource()+"_"+msgConfig.getVersion();
            //EventHubMessageLogger.info("************************ Key "+ key);
            //EventHubMessageLogger.info("************************ seperator "+ msgConfig.getSeperator());
            configMapForCache.put(key,msgConfig);
        }     
        );
       // String key= messageConfiguration.getDatasource()+"_"+messageConfiguration.getVersion();
       // configMapForCache.put("ENS_1.0", temporaryHardCodedENSMessageConfig());
      // configMapForCache.put(key,messageConfiguration);
        //
        return configMapForCache;
    }

    public MessageConfiguration getConfigurationDetailsForEventType(String messageConfigKey) throws MessageHarmonizerException {
        if (this.configMap != null && this.configMap.containsKey(messageConfigKey)) {
            System.out.println("this.configMap.get(messageConfigKey) :: "+this.configMap.get(messageConfigKey));
            return this.configMap.get(messageConfigKey);
        }

        //TODO: Implement custom exception
        throw new MessageHarmonizerException("Event Type not yet supported" + messageConfigKey);
    }
    
    public static MessageConfiguration temporaryHardCodedENSMessageConfig() {
    	MessageConfiguration msgConfig = new MessageConfiguration();
    	String schema = "createdatetime|uniqueid|trackingnumber|destinationaddress|servicetype|languagecode|opco|channel|clientrequestednotification|clientsendingnotification|emailtype|emailsubtype|shipmentorigincountry|shipmentdestinationcountry|addresstype|notificationsuccess|edtwdisplayed|signatureservicetype|shipperaccountnumber|standardshipmentdate|edtwstartdatetime|edtwenddatetime|edd";
    	msgConfig.setIncomingPayloadSchema(schema);
    	msgConfig.setSeperator("\\|");
    	msgConfig.setDatasource("ENS");
    	msgConfig.setVersion("1.0");
    	List<String> connStrList = new ArrayList<String>();
    	connStrList.add("Endpoint=sb://evhn-fxs-daw-hrmz-easx-001.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=VodJZKpkv+IN6tMtxygm6SJDmKIpAmherBRhZJZ8QWM=");
    	
    	List<String> ehNameList = new ArrayList<String>();
    	ehNameList.add("fxs.daw.hrmz.ens.dev");
    	
    	//msgConfig.setDispacher(connStrList);
    	//msgConfig.setEventHubNames(ehNameList);
    	return msgConfig;
    }
    
}
