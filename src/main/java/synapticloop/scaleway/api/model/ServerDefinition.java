package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerDefinition extends ServerBase {

	@JsonProperty("image")  public String imageId;

	public String getImageId() { return imageId; }

	public void setImage(String imageId) { this.imageId = imageId; }

}
