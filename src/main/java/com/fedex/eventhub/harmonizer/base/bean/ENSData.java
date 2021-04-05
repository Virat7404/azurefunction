package com.fedex.eventhub.harmonizer.base.bean;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ENSData {
	
	@JsonProperty("createdatetime")
	private String createdatetime;
	
	@JsonProperty("uniqueid")
	private String uniqueid;
	
	@JsonProperty("trackingnumber")
	private String trackingnumber;
	
	@JsonProperty("destinationaddress")
	private String destinationaddress;
	
	@JsonProperty("servicetype")
	private String servicetype;
	
	@JsonProperty("languagecode")
	private String languagecode;
	
	@JsonProperty("opco")
	private String opco;
	
	@JsonProperty("channel")
	private String channel;
	
	@JsonProperty("clientrequestednotification")
	private String clientrequestednotification;
	
	@JsonProperty("clientsendingnotification")
	private String clientsendingnotification;
	
	@JsonProperty("emailtype")
	private String emailtype;
	
	@JsonProperty("emailsubtype")
	private String emailsubtype;
	
	@JsonProperty("shipmentorigincountry")
	private String shipmentorigincountry;
	
	@JsonProperty("shipmentdestinationcountry")
	private String shipmentdestinationcountry;
	
	@JsonProperty("addresstype")
	private String addresstype;
	
	@JsonProperty("notificationsuccess")
	private String notificationsuccess;
	
	@JsonProperty("edtwdisplayed")
	private String edtwdisplayed;
	
	@JsonProperty("signatureservicetype")
	private String signatureservicetype;
	
	@JsonProperty("shipperaccountnumber")
	private String shipperaccountnumber;
	
	@JsonProperty("standardshipmentdate")
	private String standardshipmentdate;
	
	@JsonProperty("edtwstartdatetime")
	private String edtwstartdatetime;
	
	@JsonProperty("edtwenddatetime")
	private String edtwenddatetime;
	
	@JsonProperty("edd")
	private String edd;
	
	public ENSData() {
		super();
	}
	

	public String getCreatedatetime() {
		return createdatetime;
	}

	public void setCreatedatetime(String createdatetime) {
		this.createdatetime = createdatetime;
	}

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getTrackingnumber() {
		return trackingnumber;
	}

	public void setTrackingnumber(String trackingnumber) {
		this.trackingnumber = trackingnumber;
	}

	public String getDestinationaddress() {
		return destinationaddress;
	}

	public void setDestinationaddress(String destinationaddress) {
		this.destinationaddress = destinationaddress;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getLanguagecode() {
		return languagecode;
	}

	public void setLanguagecode(String languagecode) {
		this.languagecode = languagecode;
	}

	public String getOpco() {
		return opco;
	}

	public void setOpco(String opco) {
		this.opco = opco;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getClientrequestednotification() {
		return clientrequestednotification;
	}

	public void setClientrequestednotification(String clientrequestednotification) {
		this.clientrequestednotification = clientrequestednotification;
	}

	public String getClientsendingnotification() {
		return clientsendingnotification;
	}

	public void setClientsendingnotification(String clientsendingnotification) {
		this.clientsendingnotification = clientsendingnotification;
	}

	public String getEmailtype() {
		return emailtype;
	}

	public void setEmailtype(String emailtype) {
		this.emailtype = emailtype;
	}

	public String getEmailsubtype() {
		return emailsubtype;
	}

	public void setEmailsubtype(String emailsubtype) {
		this.emailsubtype = emailsubtype;
	}

	public String getShipmentorigincountry() {
		return shipmentorigincountry;
	}

	public void setShipmentorigincountry(String shipmentorigincountry) {
		this.shipmentorigincountry = shipmentorigincountry;
	}

	public String getShipmentdestinationcountry() {
		return shipmentdestinationcountry;
	}

	public void setShipmentdestinationcountry(String shipmentdestinationcountry) {
		this.shipmentdestinationcountry = shipmentdestinationcountry;
	}

	public String getAddresstype() {
		return addresstype;
	}

	public void setAddresstype(String addresstype) {
		this.addresstype = addresstype;
	}

	public String getNotificationsuccess() {
		return notificationsuccess;
	}

	public void setNotificationsuccess(String notificationsuccess) {
		this.notificationsuccess = notificationsuccess;
	}

	public String getEdtwdisplayed() {
		return edtwdisplayed;
	}

	public void setEdtwdisplayed(String edtwdisplayed) {
		this.edtwdisplayed = edtwdisplayed;
	}

	public String getSignatureservicetype() {
		return signatureservicetype;
	}

	public void setSignatureservicetype(String signatureservicetype) {
		this.signatureservicetype = signatureservicetype;
	}

	public String getShipperaccountnumber() {
		return shipperaccountnumber;
	}

	public void setShipperaccountnumber(String shipperaccountnumber) {
		this.shipperaccountnumber = shipperaccountnumber;
	}

	public String getStandardshipmentdate() {
		return standardshipmentdate;
	}

	public void setStandardshipmentdate(String standardshipmentdate) {
		this.standardshipmentdate = standardshipmentdate;
	}

	public String getEdtwstartdatetime() {
		return edtwstartdatetime;
	}

	public void setEdtwstartdatetime(String edtwstartdatetime) {
		this.edtwstartdatetime = edtwstartdatetime;
	}

	public String getEdtwenddatetime() {
		return edtwenddatetime;
	}

	public void setEdtwenddatetime(String edtwenddatetime) {
		this.edtwenddatetime = edtwenddatetime;
	}

	public String getEdd() {
		return edd;
	}

	public void setEdd(String edd) {
		this.edd = edd;
	}
	
	
	
}
