package synapticloop.scaleway.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.scaleway.api.model.VolumeType;

/**
 * A volume request is used to encapsulate the JSON object for creating a volume.
 */

public class VolumeRequest {
	@JsonProperty("name")          private String name;
	@JsonProperty("volume_type")   private VolumeType volumeType;
	@JsonProperty("size")          private long size;
	@JsonProperty("organization")  private String organizationId;

	public VolumeRequest(String name, String organizationId, long size, VolumeType volumeType) {
		this.name = name;
		this.organizationId = organizationId;
		this.size = size;
		this.volumeType = volumeType;
	}
}
