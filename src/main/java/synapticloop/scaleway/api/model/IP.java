package synapticloop.scaleway.api.model;

/*
 * Copyright (c) 2016-2017 synapticloop.
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

public class IP {
	@JsonProperty("id")            private String id;
	@JsonProperty("address")       private String ipAddress;
	@JsonProperty("reverse")       private String ipAddressReverse;
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("server")        private Server server;
	@JsonProperty("dynamic")       private boolean isDynamic = false;

	public String getId() { return id; }

	public String getIpAddress() { return ipAddress; }

	public String getOrganizationId() { return this.organizationId; }

	public Server getServer() { return this.server; }

	public String getIpAddressReverse() { return this.ipAddressReverse; }
	
	public boolean getIsDynamic() { return this.isDynamic; }
}
