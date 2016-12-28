package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ServerType {
	@JsonProperty("VC1S") VC1S,
	@JsonProperty("VC1M") VC1M,
	@JsonProperty("VC1L") VC1L,
	@JsonProperty("C2S")  C2S,
	@JsonProperty("C2M")  C2M,
	@JsonProperty("C2L")  C2L;
}
