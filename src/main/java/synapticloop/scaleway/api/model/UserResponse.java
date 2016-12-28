package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResponse {

	@JsonProperty("user")  private User user;

	public User getUser() { return user; }

	public void setUser(User user) { this.user = user; }

}
