package synapticloop.scaleway.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An IP request is used to encapsulate the JSON object for attaching a reserved
 * IP Address.
 */

public class IPPutRequest {
	@JsonProperty("address")       private String ipAddress;
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("id")            private String ipId;
	@JsonProperty("server")        private String serverId;

	public IPPutRequest(String ipAddress, String ipId, String serverId, String organizationId) {
		this.ipAddress = ipAddress;
		this.organizationId = organizationId;
		this.serverId = serverId;
		this.ipId = ipId;
	}
}
