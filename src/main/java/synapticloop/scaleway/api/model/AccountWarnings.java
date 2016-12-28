package synapticloop.scaleway.api.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountWarnings {
	@JsonProperty("reason")            private String reason;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("locked_at")         private Date lockedAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("opened_at")         private Date openedAt;
	@JsonProperty("closable_by_user")  private boolean closableByUser;
	@JsonProperty("closed")            private boolean isClosed;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("closed_at")         private Date closedAt;
	@JsonProperty("locking")           private boolean isLocking;
	@JsonProperty("id")                private String id;

	public String getReason() { return reason; }

	public Date getLockedAt() { return lockedAt; }

	public Date getOpenedAt() { return openedAt; }

	public boolean getIsClosableByUser() { return closableByUser; }

	public boolean getIsClosed() { return isClosed; }

	public Date getClosedAt() { return closedAt; }

	public boolean getIsLocking() { return isLocking; }

	public String getId() { return id; }

	@Override
	public String toString() {
		return "AccountWarnings [reason=" + this.reason + ", lockedAt=" + this.lockedAt + ", openedAt=" + this.openedAt + ", closableByUser=" + this.closableByUser + ", isClosed=" + this.isClosed + ", closedAt=" + this.closedAt + ", isLocking=" + this.isLocking + ", id=" + this.id + "]";
	}
}
