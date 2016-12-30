package synapticloop.scaleway.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An IP request is used to encapsulate the JSON object for creating a reserved
 * IP Address.
 */

public class IPRequest {
	@JsonProperty("organization")  private String organizationId;

	public IPRequest(String organizationId) {
		this.organizationId = organizationId;
	}
}
