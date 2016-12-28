package synapticloop.scaleway.api.model;

import java.util.ArrayList;

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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class Image {
	@JsonProperty("id")       private String id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("modification_date")   private Date modificationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")       private Date creationDate;
	@JsonProperty("default_bootscript")  private BootScript defaultbootscript;
	@JsonProperty("organization")        private String organization;
	@JsonProperty("arch")                private String arch;
	@JsonProperty("root_volume")         private Volume rootVolume;
	@JsonProperty("name")                private String name;
	@JsonDeserialize(contentAs = Volume.class)
	@JsonProperty("extra_volumes")       private List<Volume> extraVolumes;
	@JsonProperty("public")              private boolean isPublicImage;
	@JsonProperty("from_image")          private Image fromImage;
	@JsonProperty("from_server")         private ServerBase fromServer;
	@JsonProperty("marketplace_key")     private String marketplaceKey;

	public String getId() { return id; }

	public Date getModificationDate() { return modificationDate; }

	public Date getCreationDate() { return creationDate; }

	public boolean getIsPublicImage() { return isPublicImage; }

	public String getOrganization() { return organization; }

	public String getArch() { return arch; }

	public BootScript getDefaultbootscript() { return defaultbootscript; }

	public Volume getRootVolume() { return rootVolume; }

	public String getName() { return name; }

	public List<Volume> getExtraVolumes() { return extraVolumes; }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setExtraVolumes(Object extraVolumes) {
		if(extraVolumes instanceof List) {
			this.extraVolumes = (List)extraVolumes;
		} else {
			this.extraVolumes = new ArrayList();
		}
	}

	public Image getFromImage() { return fromImage; }

	public ServerBase getFromServer() { return fromServer; }

	public String getMarketplaceKey() { return marketplaceKey; }

	@Override
	public String toString() {
		return "Image [id=" + this.id + ", modificationDate=" + this.modificationDate + ", creationDate=" + this.creationDate + ", defaultbootscript=" + this.defaultbootscript + ", organization=" + this.organization + ", arch=" + this.arch + ", rootVolume=" + this.rootVolume + ", name=" + this.name + ", extraVolumes=" + this.extraVolumes + ", isPublicImage=" + this.isPublicImage + ", fromImage=" + this.fromImage + ", fromServer=" + this.fromServer + ", marketplaceKey=" + this.marketplaceKey + "]";
	}
}
