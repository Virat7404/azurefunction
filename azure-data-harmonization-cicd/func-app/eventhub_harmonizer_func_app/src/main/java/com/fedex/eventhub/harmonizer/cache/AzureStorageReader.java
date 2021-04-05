package com.fedex.eventhub.harmonizer.cache;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedex.eventhub.harmonizer.core.EventHubMessageLogger;
import com.fedex.eventhub.harmonizer.core.message.MessageConfiguration;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlobDirectory;
import com.microsoft.azure.storage.blob.ListBlobItem;

public class AzureStorageReader {

	public static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=sahrmzconfig;AccountKey=mzkygm/x+WXqDzzrh4P9wvUInkUbGL2Px+o9Gf7yjqY/Y+ugCcvk75OAPGcXl1LuMd2xg1p54oN9DEhhk8RGcg==;EndpointSuffix=core.windows.net";
	public static HashMap<String, String> configmap = new HashMap<String, String>();

	public static List<MessageConfiguration> getMessageConfiguration() throws JsonProcessingException {
		List<MessageConfiguration> msgConfigList = new ArrayList<>();
		List<String> messageConfigString = readAzureStorage();
		//EventHubMessageLogger.info("messageConfigString+++++++++: " + messageConfigString);
		/*
		 * msgConfigList= messageConfigString.stream().map(msgConfig ->
		 * createMessageConfiguration(msgConfig)).collect(Collectors.toList());
		 */
		try {
			for (String msg : messageConfigString) {
				msgConfigList.add(createMessageConfiguration(msg));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return msgConfigList;
		// return createMessageConfiguration(messageConfigString);
	}

	private static List<String> readAzureStorage() {
		List<String> msgList = new ArrayList<>();
		try {
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

			CloudBlobContainer container = blobClient.getContainerReference("ens");

			Iterable<ListBlobItem> blobs = container.listBlobs();
			for (ListBlobItem fileBlob : blobs) {
				if (fileBlob instanceof CloudBlobDirectory) {
					listSubDirectories(fileBlob);
				} else {
					String readmeassage = readFileBlob(fileBlob);
					msgList.add(readmeassage);
					//EventHubMessageLogger.info("readmeassage :: " + readmeassage);
				}
			}

			//EventHubMessageLogger.info("msgList count :: " + msgList.size());

		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
		return msgList;
	}

	private static MessageConfiguration createMessageConfiguration(String messageConfigString)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		MessageConfiguration messageConfiguration = om.readValue(messageConfigString, MessageConfiguration.class);
		//EventHubMessageLogger.info("***messageConfiguration*** :" + messageConfiguration.toString());
		return messageConfiguration;
	}

	public static String listSubDirectories(ListBlobItem blob) {

		try {
			CloudBlobDirectory directory = (CloudBlobDirectory) blob;
			Iterable<ListBlobItem> fileBlobs = directory.listBlobs();

			for (ListBlobItem fileBlob : fileBlobs) {
				if (fileBlob instanceof CloudBlobDirectory) {
					listSubDirectories(fileBlob);
				} else {
					return readFileBlob(fileBlob);
				}
			}
		} catch (Exception msgExp) {
			// Output the stack trace.
			msgExp.printStackTrace();
		}
		return null;
	}

	public static String readFileBlob(ListBlobItem fileBlob) {

		try {
			CloudBlob cloudBlob = (CloudBlob) fileBlob;

			InputStreamReader readfile = new InputStreamReader(cloudBlob.openInputStream(), "UTF-8");
			String messageConfigString = IOUtils.toString(readfile);
			//EventHubMessageLogger.info("MessageConfigString: : " + messageConfigString);
			//EventHubMessageLogger.info("MessageConfigUri : " + cloudBlob.getUri().toString());
			return messageConfigString;
		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
		return null;

	}

}
