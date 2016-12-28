package synapticloop.scaleway.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Server extends ServerBase {

	@JsonProperty("id")                 private String id;
	@JsonProperty("private_ip")         private String privateIp;
	@JsonProperty("extra_networks")     private String[] extraNetworks;
	@JsonProperty("state")              private State state;
	@JsonProperty("arch")               private String arch;
	@JsonProperty("state_detail")       public String stateDetail;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("modification_date")  private Date modificationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")      private Date creationDate;
	@JsonProperty("location")           private Location location;
	@JsonProperty("ipv6")               private String ipv6;
	@JsonProperty("image")              public Image image;
	@JsonProperty("hostname")           private String hostname;

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Date getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPrivateIp() {
		return privateIp;
	}

	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public String getStateDetail() {
		return stateDetail;
	}

	public void setStateDetail(String stateDetail) {
		this.stateDetail = stateDetail;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getIpv6() {
		return ipv6;
	}

	public void setIpv6(String ipv6) {
		this.ipv6 = ipv6;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public String[] getExtraNetworks() {
		return extraNetworks;
	}

	public void setExtraNetworks(String[] extraNetworks) {
		this.extraNetworks = extraNetworks;
	}

}
