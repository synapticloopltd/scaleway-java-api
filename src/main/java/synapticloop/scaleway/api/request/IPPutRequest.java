package synapticloop.scaleway.api.request;

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

/**
 * An IP request is used to encapsulate the JSON object for attaching a reserved
 * IP Address.
 */

public class IPPutRequest {
	@JsonProperty("address")       private String ipAddress;
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("id")            private String ipId;
	@JsonProperty("server")        private String serverId;

	/**
	 * Instantiate an IP PUT request
	 * 
	 * @param ipAddress The ip address
	 * @param ipId The ID of the IP address
	 * @param serverId The ID of the server
	 * @param organizationId The ID of the organisation
	 */
	public IPPutRequest(String ipAddress, String ipId, String serverId, String organizationId) {
		this.ipAddress = ipAddress;
		this.organizationId = organizationId;
		this.serverId = serverId;
		this.ipId = ipId;
	}
}
