package synapticloop.scaleway.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerInstances {

	@JsonProperty("servers")  private List<Server> servers;

	public List<Server> getServers() { return servers; }
}
