package synapticloop.scaleway.api.model;

/*
 * Copyright (c) 2016 synapticloop.
 * 
 * All rights reserved.
 * 
 * This code may contain contributions from other parties which, where 
 * applicable, will be listed in the default build file for the project 
 * ~and/or~ in a file named CONTRIBUTORS.txt in the root of the project.
 * 
 * This source code and any derived binaries are covered by the terms and 
 * conditions of the Licence agreement ("the Licence").  You may not use this 
 * source code or any derived binaries except in compliance with the Licence.  
 * A copy of the Licence is available in the file named LICENSE.txt shipped with 
 * this source code or binaries.
 */

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountWarning {
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
