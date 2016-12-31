package synapticloop.scaleway.api.request;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.scaleway.api.model.ServerType;

/**
 * An IP request is used to encapsulate the JSON object for creating a reserved
 * IP Address.
 */

public class ServerDefinitionRequest {
	@JsonProperty("organization")  private String organizationId;
	@JsonProperty("name")  private String serverName;
	@JsonProperty("image")  private String imageId;
	@JsonProperty("commercial_type")  private ServerType serverType;
	@JsonProperty("tags")  private List<String> tags;

	public ServerDefinitionRequest(String serverName, String imageId, String organizationId, ServerType serverType, String... tags) {
		this.serverName = serverName;
		this.imageId = imageId;
		this.organizationId = organizationId;
		this.serverType = serverType;
		this.tags = Arrays.asList(tags);
	}
}
