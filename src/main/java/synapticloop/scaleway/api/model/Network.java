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

import com.fasterxml.jackson.annotation.JsonProperty;

public class Network {

	@JsonProperty("netmask") private String netmask;
	@JsonProperty("version") private String version;
	@JsonProperty("type") private String type;
	@JsonProperty("gateway") private String gateway;
	@JsonProperty("address") private String address;

	public String getNetmask() { return this.netmask; }

	public String getVersion() { return this.version; }

	public String getType() { return this.type; }

	public String getGateway() { return this.gateway; }

	public String getAddress() { return this.address; }
}
