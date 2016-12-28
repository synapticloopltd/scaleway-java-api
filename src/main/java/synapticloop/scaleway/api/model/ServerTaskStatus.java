package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ServerTaskStatus {
	@JsonProperty("pending") PENDING,
	@JsonProperty("running") RUNNING,
	@JsonProperty("started") STARTED,
	@JsonProperty("success") SUCCESS;
}
