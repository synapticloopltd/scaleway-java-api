package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

	@JsonProperty("platform_id") private String platformID;
	@JsonProperty("hypervisor_id") private String hypervisorID;
	@JsonProperty("node_id") private String nodeID;
	@JsonProperty("cluster_id") private String clusterID;
	@JsonProperty("chassis_id") private String chassisId;
	@JsonProperty("zone_id") private String zoneId;


	public String getPlatformID() {
		return platformID;
	}

	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}

	public String getHypervisorID() {
		return hypervisorID;
	}

	public void setHypervisorID(String hypervisorID) {
		this.hypervisorID = hypervisorID;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}

	public String getClusterID() {
		return clusterID;
	}

	public void setClusterID(String clusterID) {
		this.clusterID = clusterID;
	}

	public String getChassisId() {
		return chassisId;
	}

	public void setChassisId(String chassisId) {
		this.chassisId = chassisId;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}    
}
