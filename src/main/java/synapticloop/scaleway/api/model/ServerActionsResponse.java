package synapticloop.scaleway.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerActionsResponse {

	@JsonProperty("actions") private List<Action> actions;

	public List<Action> getActions() { return actions; }

}
