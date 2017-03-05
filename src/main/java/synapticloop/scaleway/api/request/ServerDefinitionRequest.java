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

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.scaleway.api.model.ServerType;

/**
 * An IP request is used to encapsulate the JSON object for creating a reserved
 * IP Address.
 */

public class ServerDefinitionRequest {
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("name")  private String serverName;
	@JsonProperty("image")  private String imageId;
	@JsonProperty("commercial_type")  private ServerType serverType;
	@JsonProperty("tags")  private List<String> tags;

	public ServerDefinitionRequest(String serverName, String imageId, String organizationId, ServerType serverType, String... tags) {
		this.serverName = serverName;
		this.imageId = imageId;
		this.organizationId = organizationId;
		this.serverType = serverType;
		this.tags = Arrays.asList(tags);
	}
}
