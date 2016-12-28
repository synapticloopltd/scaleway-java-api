package synapticloop.scaleway.api.model;

import java.util.Objects;

public class SshPublicKey {

	private String key;

	public SshPublicKey(String key) {
		this.key = key;
	}

	public SshPublicKey() {

	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 67 * hash + Objects.hashCode(this.key);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final SshPublicKey other = (SshPublicKey) obj;
		if (!Objects.equals(this.key, other.key)) {
			return false;
		}
		return true;
	}

}
