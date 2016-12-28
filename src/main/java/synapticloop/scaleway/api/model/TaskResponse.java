package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskResponse {
	@JsonProperty("task")  private ServerTask task;

	public ServerTask getTask() { return task; }

}
