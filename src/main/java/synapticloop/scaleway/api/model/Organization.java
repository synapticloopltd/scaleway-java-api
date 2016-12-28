package synapticloop.scaleway.api.model;

/*
 * Copyright (c) 2016 synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Organization {

	@JsonProperty("id")                        private String id;
	@JsonProperty("name")                      private String name;
	@JsonProperty("address_line1")             private String addressLine;
	@JsonProperty("address_line2")             private String addressLine2;
	@JsonProperty("address_country_code")      private String addressCountryCode;
	@JsonProperty("support_level")             private String supportLevel;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("modification_date")         private Date modificationDate;
	@JsonProperty("currency")                  private String currency;
	@JsonProperty("locale")                    private String locale;
	@JsonProperty("customer_class")            private String customerClass;
	@JsonProperty("support_id")                private String supportId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")             private Date creationDate;
	@JsonProperty("address_postal_code")       private String addressPortalCode;
	@JsonProperty("address_city_name")         private String addressCityName;
	@JsonProperty("address_subdivision_code")  private String addressSubdivisionCode;
	@JsonProperty("timezone")                  private String timezone;
	@JsonProperty("vat_number")                private String vatNumber;
	@JsonProperty("support_pin")               private String supportPin;
	@JsonProperty("warnings")                  private List<AccountWarning> warnings;
	@JsonProperty("users")                     private List<User> users;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getAddressCountryCode() {
		return addressCountryCode;
	}

	public void setAddressCountryCode(String addressCountryCode) {
		this.addressCountryCode = addressCountryCode;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getSupportLevel() {
		return supportLevel;
	}

	public void setSupportLevel(String supportLevel) {
		this.supportLevel = supportLevel;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getCustomerClass() {
		return customerClass;
	}

	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getAddressPortalCode() {
		return addressPortalCode;
	}

	public void setAddressPortalCode(String addressPortalCode) {
		this.addressPortalCode = addressPortalCode;
	}

	public String getAddressCityName() {
		return addressCityName;
	}

	public void setAddressCityName(String addressCityName) {
		this.addressCityName = addressCityName;
	}

	public String getAddressSubdivisionCode() {
		return addressSubdivisionCode;
	}

	public void setAddressSubdivisionCode(String addressSubdivisionCode) {
		this.addressSubdivisionCode = addressSubdivisionCode;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public String getSupportId() {
		return supportId;
	}

	public void setSupportId(String supportId) {
		this.supportId = supportId;
	}

	public String getSupportPin() {
		return supportPin;
	}

	public void setSupportPin(String supportPin) {
		this.supportPin = supportPin;
	}

	public List<AccountWarning> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<AccountWarning> warnings) {
		this.warnings = warnings;
	}

}
