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

	public String getId() { return id; }

	public Server getServer() { return server; }

	public String getName() { return name; }
	
	public VolumeType getVolumeType() { return volumeType; }

	public Date getModificationDate() { return modificationDate; }

	public Date getCreationDate() { return creationDate; }

	public long getSize() { return size; }

	public String getOrganizationId() { return organizationId; }

	public String getExportUri() { return exportUri; }

}
