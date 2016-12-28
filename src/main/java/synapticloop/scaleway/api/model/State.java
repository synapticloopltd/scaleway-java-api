package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum State {
	@JsonProperty("running")  RUNNING,
	@JsonProperty("stopped")  STOPPED,
	@JsonProperty("stopping") STOPPING,
	@JsonProperty("starting") STARTING;
}
