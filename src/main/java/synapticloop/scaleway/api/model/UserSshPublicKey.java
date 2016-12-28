package synapticloop.scaleway.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserSshPublicKey extends SshPublicKey {

	@JsonProperty(access = Access.WRITE_ONLY)
	private String fingerprint;


	public String getFingerprint() { return fingerprint; }

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
}
