package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IP {
	@JsonProperty("dynamic")  private boolean isDynamic;
	@JsonProperty("id")       private String id;
	@JsonProperty("address")  private String address;

	public boolean getIsDynamic() {
		return isDynamic;
	}

	public void setIsDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
