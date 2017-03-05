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

public class Server extends ServerBase {
	@JsonProperty("id")                 private String id;
	@JsonProperty("public_ip")          private IP publicIp;
	@JsonProperty("private_ip")         private String privateIp;
	@JsonProperty("extra_networks")     private List<Network> extraNetworks;
	@JsonProperty("state")              private State state;
	@JsonProperty("arch")               private String arch;
	@JsonProperty("state_detail")       private String stateDetail;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("modification_date")  private Date modificationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")      private Date creationDate;
	@JsonProperty("location")           private Location location;
	@JsonProperty("ipv6")               private IPv6 ipv6;
	@JsonProperty("image")              private Image image;
	@JsonProperty("hostname")           private String hostname;

	public String getHostname() { return hostname; }

	public String getId() { return id; }

	public Image getImage() { return image; }

	public Date getModificationDate() { return modificationDate; }

	public Date getCreationDate() { return creationDate; }

	public String getPrivateIp() { return privateIp; }

	public State getState() { return state; }

	public String getStateDetail() { return stateDetail; }

	public Location getLocation() { return location; }

	public IPv6 getIPv6() { return ipv6; }

	public String getArch() { return arch; }

	public List<Network> getExtraNetworks() { return extraNetworks; }

}
