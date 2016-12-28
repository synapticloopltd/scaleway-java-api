package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Action {
	@JsonProperty("poweron")  POWER_ON,
	@JsonProperty("poweroff")  POWER_OFF,
	@JsonProperty("reboot")    REBOOT,
	@JsonProperty("terminate") TERMINATE;
}
