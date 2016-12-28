package synapticloop.scaleway.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImagesResponse {

	@JsonProperty("images")  private List<Image> images;

	public List<Image> getImages() { return images; }

	public void setImages(List<Image> images) { this.images = images; }
}
