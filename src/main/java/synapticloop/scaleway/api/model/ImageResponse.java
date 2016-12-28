package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageResponse {

	@JsonProperty("image")  private Image image;

	public Image getImage() { return image; }

	public void setImage(Image image) { this.image = image; }
}
