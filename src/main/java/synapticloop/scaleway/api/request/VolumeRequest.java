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

import synapticloop.scaleway.api.model.VolumeType;

/**
 * A volume request is used to encapsulate the JSON object for creating a volume.
 */

public class VolumeRequest {
	@JsonProperty("name")          private String name;
	@JsonProperty("volume_type")   private VolumeType volumeType;
	@JsonProperty("size")          private long size;
	@JsonProperty("organization")  private String organizationId;

	public VolumeRequest(String name, String organizationId, long size, VolumeType volumeType) {
		this.name = name;
		this.organizationId = organizationId;
		this.size = size;
		this.volumeType = volumeType;
	}
}
