package synapticloop.scaleway.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Security Group Request is used to encapsulate the JSON object for creating 
 * a Security Group
 */

public class SecurityGroupRequest {
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("name")  private String name;
	@JsonProperty("description")  private String description;

	public SecurityGroupRequest(String organizationId, String name, String description) {
		this.organizationId = organizationId;
		this.name = name;
		this.description = description;
	}
}
