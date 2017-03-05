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

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

	@JsonProperty("platform_id") private String platformID;
	@JsonProperty("hypervisor_id") private String hypervisorID;
	@JsonProperty("node_id") private String nodeID;
	@JsonProperty("cluster_id") private String clusterID;
	@JsonProperty("chassis_id") private String chassisId;
	@JsonProperty("zone_id") private String zoneId;
	@JsonProperty("blade_id") private String bladeId;

	public String getPlatformID() { return platformID; }

	public String getHypervisorID() { return hypervisorID; }

	public String getNodeID() { return nodeID; }

	public String getClusterID() { return clusterID; }

	public String getChassisId() { return chassisId; }

	public String getZoneId() { return zoneId; }

	public String getBladeId() { return bladeId; }
}
