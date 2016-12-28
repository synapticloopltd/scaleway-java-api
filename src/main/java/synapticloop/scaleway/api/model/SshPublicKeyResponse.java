package synapticloop.scaleway.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SshPublicKeyResponse {

	@JsonProperty("ssh_public_keys") private List<SshPublicKey> sshPublicKeys;

	public List<SshPublicKey> getSshPublicKeys() {
		return sshPublicKeys;
	}

	public void setSshPublicKeys(List<SshPublicKey> sshPublicKeys) {
		this.sshPublicKeys = sshPublicKeys;
	}

}
