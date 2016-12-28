package synapticloop.scaleway.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Volume {
	@JsonProperty("id")                 private String id;
	@JsonProperty("name")               private String name;
	@JsonProperty("volume_type")        private VolumeType volumeType;
	@JsonProperty("size")               private String size;
	@JsonProperty("organization")       private String organization;
	@JsonProperty("export_uri")         private String exportUri;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("modification_date")  private Date modificationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")      private Date creationDate;
	@JsonProperty("server")             private Server server;

	public String getId() { return id; }

	public Server getServer() { return server; }

	public String getName() { return name; }

	public VolumeType getVolumeType() { return volumeType; }

	public Date getModificationDate() { return modificationDate; }

	public Date getCreationDate() { return creationDate; }

	public String getSize() { return size; }

	public String getOrganization() { return organization; }

	public String getExportUri() { return exportUri; }

}
