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

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerBase {
	@JsonProperty("name")                public String name;
	@JsonProperty("organization")        public String organizationId;
	@JsonProperty("volumes")             public Map<String, Volume> volumes;
	@JsonProperty("tags")                public List<String> tags;
	@JsonProperty("commercial_type")     protected ServerType serverType;
	@JsonProperty("dynamic_ip_required") protected Boolean isDynamicIpRequired;
	@JsonProperty("enable_ipv6")         protected Boolean isIPv6Enabled;
	@JsonProperty("security_group")      protected SecurityGroup securityGroup;
	@JsonProperty("bootscript")          protected BootScript bootscript;

	public String getName() { return name; }

	public String getOrganizationId() { return organizationId; }

	public BootScript getBootscript() { return bootscript; }

	public SecurityGroup getSecurityGroup() { return securityGroup; }

	public ServerType getServerType() { return serverType; }

	public Boolean getIsDynamicIpRequired() { return isDynamicIpRequired; }

	public Boolean getIsIpv6Enabled() { return isIPv6Enabled; }

	public Map<String, Volume> getVolumes() { return volumes; }

	public List<String> getTags() { return tags; }

}
