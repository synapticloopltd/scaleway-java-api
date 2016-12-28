package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerInstance {
	@JsonProperty("server")  public Server server;

	public Server getServer() { return server; }

}
