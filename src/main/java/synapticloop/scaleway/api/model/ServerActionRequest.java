package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerActionRequest {

	@JsonProperty("action")  private Action action;

	public Action getAction() { return action; }
}
