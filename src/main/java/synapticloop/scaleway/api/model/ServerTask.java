package synapticloop.scaleway.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerTask {

	@JsonProperty("description")    private String description;
	@JsonProperty("href_from")      private String hrefFrom;
	@JsonProperty("id")             private String id;
	@JsonProperty("progress")       private String progress;
	@JsonProperty("status")         private ServerTaskStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("terminated_at")  private Date terminatedAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("started_at")     private Date startedAt;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHrefFrom() {
		return hrefFrom;
	}

	public void setHrefFrom(String hrefFrom) {
		this.hrefFrom = hrefFrom;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public ServerTaskStatus getStatus() {
		return status;
	}

	public void setStatus(ServerTaskStatus status) {
		this.status = status;
	}

	public Date getTerminatedAt() {
		return terminatedAt;
	}

	public void setTerminatedAt(Date terminatedAt) {
		this.terminatedAt = terminatedAt;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

}
