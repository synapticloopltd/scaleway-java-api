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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerBase {

	@JsonProperty("name")                public String name;
	@JsonProperty("organization")        public String organization;
	@JsonProperty("volumes")             public Map<String, Volume> volumes;
	@JsonProperty("tags")                public List<String> tags;
	@JsonProperty("commercial_type")     private ServerType serverType;
	@JsonProperty("dynamic_ip_required") private boolean dynamicIpRequired;
	@JsonProperty("enable_ipv6")         private boolean ipv6Enabled;
	@JsonProperty("public_ip")           private IP publicIp;
	@JsonProperty("security_group")      private SecurityGroup securityGroup;
	@JsonProperty("bootscript")          private BootScript bootscript;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public BootScript getBootscript() {
		return bootscript;
	}

	public void setBootscript(BootScript bootscript) {
		this.bootscript = bootscript;
	}

	public SecurityGroup getSecurityGroup() {
		return securityGroup;
	}

	public void setSecurityGroup(SecurityGroup securityGroup) {
		this.securityGroup = securityGroup;
	}

	public ServerType getServerType() {
		return serverType;
	}


	public void setServerType(ServerType serverType) {
		this.serverType = serverType;
	}

	public boolean isDynamicIpRequired() {
		return dynamicIpRequired;
	}

	public void setDynamicIpRequired(boolean dynamicIpRequired) {
		this.dynamicIpRequired = dynamicIpRequired;
	}

	public boolean isIpv6Enabled() {
		return ipv6Enabled;
	}

	public void setIpv6Enabled(boolean ipv6Enabled) {
		this.ipv6Enabled = ipv6Enabled;
	}

	public IP getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(IP publicIp) {
		this.publicIp = publicIp;
	}

	public Map<String, Volume> getVolumes() {
		return volumes;
	}

	public void setVolumes(Map<String, Volume> volumes) {
		this.volumes = volumes;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
