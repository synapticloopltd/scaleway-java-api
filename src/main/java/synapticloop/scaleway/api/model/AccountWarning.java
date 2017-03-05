package synapticloop.scaleway.api.model;

/*
 * Copyright (c) 2016-2017 synapticloop.
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

/**
 * This is the Account Warning POJO which encapsulates an account warning for
 * the specified account
 */

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

	/**
	 * Return the reason for the account warning
	 * 
	 * @return the reason for the account warning
	 */
	public String getReason() { return reason; }

	/**
	 * Return the date that the account warning was loked at
	 * 
	 * @return The date that the account warning was locked at
	 */
	public Date getLockedAt() { return lockedAt; }

	/**
	 * Return the date that the account warning was opened
	 * 
	 * @return The date that the account warning was opened at
	 */
	public Date getOpenedAt() { return openedAt; }

	/**
	 * Return whether the account warning is closable by the user
	 * 
	 * @return Whether the account warning is closable by the user
	 */
	public boolean getIsClosableByUser() { return closableByUser; }

	/**
	 * Return whether the account warning is closed
	 * 
	 * @return Whether the account warning is closed
	 */
	public boolean getIsClosed() { return isClosed; }

	/**
	 * Return the date at which the account warning was closed (or null if not closed)
	 * 
	 * @return the date at which the account warning was closed (or null if not closed)
	 */
	public Date getClosedAt() { return closedAt; }

	/**
	 * Return whether this account warning is locking the account
	 * 
	 * @return Whether this account warning is loccking the account
	 */
	public boolean getIsLocking() { return isLocking; }

	/**
	 * Return the unique identifier for this account warning
	 * 
	 * @return The unique identifier for this account warning
	 */
	public String getId() { return id; }
}
