package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {

	@JsonProperty("organization")  private Organization organization;
	@JsonProperty("role")          private String role;

	public Organization getOrganization() { return organization; }

	public String getRole() { return role; }
}
