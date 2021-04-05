package com.fedex.eventhub.harmonizer.core.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageConfiguration {

	public String datasource;
    public String version;
    public String type;
    public String seperator;
    @JsonProperty("incomingPayload-schema") 
    public String incomingPayloadSchema;
    @JsonProperty("outgoingPayload-schema") 
    public String outgoingPayloadSchema;
    public List<Dispatcher> dispacher;

	

	

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public String getIncomingPayloadSchema() {
		return incomingPayloadSchema;
	}

	public void setIncomingPayloadSchema(String incomingPayloadSchema) {
		this.incomingPayloadSchema = incomingPayloadSchema;
	}

	public String getOutgoingPayloadSchema() {
		return outgoingPayloadSchema;
	}

	public void setOutgoingPayloadSchema(String outgoingPayloadSchema) {
		this.outgoingPayloadSchema = outgoingPayloadSchema;
	}

	public List<Dispatcher> getDispacher() {
		return dispacher;
	}

	public void setDispacher(List<Dispatcher> dispacher) {
		this.dispacher = dispacher;
	}

	@Override
	public String toString() {
		return "MessageConfiguration [datasource=" + datasource + ", dispacher=" + dispacher
				+ ", incomingPayloadSchema=" + incomingPayloadSchema + ", outgoingPayloadSchema="
				+ outgoingPayloadSchema + ", seperator=" + seperator + ", type=" + type + ", version=" + version + "]";
	}

	


}
