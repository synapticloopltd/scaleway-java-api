package synapticloop.scaleway.api.request;

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

/**
 * A Security Group Request is used to encapsulate the JSON object for creating 
 * a Security Group
 */

public class SecurityGroupRequest {
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("name")  private String name;
	@JsonProperty("description")  private String description;

	public SecurityGroupRequest(String organizationId, String name, String description) {
		this.organizationId = organizationId;
		this.name = name;
		this.description = description;
	}
}
