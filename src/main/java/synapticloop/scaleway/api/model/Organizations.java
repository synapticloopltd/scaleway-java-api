package synapticloop.scaleway.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Organizations {

	@JsonProperty("organizations")  private List<Organization> organizations;

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

}
