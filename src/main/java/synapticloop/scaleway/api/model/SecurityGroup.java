package synapticloop.scaleway.api.model;

import java.util.List;

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

public class SecurityGroup {
	@JsonProperty("id")    private String id;
	@JsonProperty("name")  private String name;
	@JsonProperty("description")  private String description;
	@JsonProperty("enable_default_security")  private boolean enableDefaultSecurity;
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("organization_default")  private boolean organizationDefault;
	@JsonProperty("servers")  private List<Server> servers;

	public String getId() { return this.id; }

	public String getName() { return this.name; }

	public String getDescription() { return this.description; }

	public boolean getEnableDefaultSecurity() { return this.enableDefaultSecurity; }

	public String getOrganizationId() { return this.organizationId; }

	public boolean getOrganizationDefault() { return this.organizationDefault; }

	public List<Server> getServers() { return this.servers; }


}
