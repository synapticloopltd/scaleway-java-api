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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Volume {
	@JsonProperty("id")                 private String id;
	@JsonProperty("name")               private String name;
	@JsonProperty("volume_type")        private VolumeType volumeType;
	@JsonProperty("size")               private long size;
	@JsonProperty("organization")       private String organizationId;
	@JsonProperty("export_uri")         private String exportUri;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("modification_date")  private Date modificationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")      private Date creationDate;
	@JsonProperty("server")             private Server server;

	public Volume(String name, String organizationId, long size, VolumeType volumeType) {
		this.name = name;
		this.organizationId = organizationId;
		this.size = size;
		this.volumeType = volumeType;
	}

	public String getId() { return id; }

	public Server getServer() { return server; }

	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	public VolumeType getVolumeType() { return volumeType; }

	public Date getModificationDate() { return modificationDate; }

	public Date getCreationDate() { return creationDate; }

	public long getSize() { return size; }

	public void setSize(long size) { this.size = size; }

	public String getOrganizationId() { return organizationId; }

	public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

	public String getExportUri() { return exportUri; }

	public void setVolumeType(VolumeType volumeType) { this.volumeType = volumeType; }

}
