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
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")        private Date creationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("expires")              private Date expiresDate;
	@JsonProperty("id")                   private String id;
	@JsonProperty("description")          private String description;
	@JsonProperty("inherits_user_perms")  private boolean inheritsUserPerms;
	@JsonProperty("permissions")          private List<Permission> permissions;
	@JsonProperty("roles")                private Role role;
	@JsonProperty("user_id")              private String userId;

	public Date getCreationDate() { return this.creationDate; }

	public Date getExpiresDate() { return this.expiresDate; }

	public String getId() { return this.id; }

	public boolean getInheritsUserPerms() { return this.inheritsUserPerms; }

	public List<Permission> getPermissions() { return this.permissions; }

	public Role getRole() { return this.role; }

	public String getUserId() { return this.userId; }

	public String getDescription() { return this.description; }

	@Override
	public String toString() {
		return "Token [creationDate=" + this.creationDate + ", expires=" + this.expiresDate + ", id=" + this.id + ", description=" + this.description + ", inheritsUserPerms=" + this.inheritsUserPerms + ", permissions=" + this.permissions + ", role=" + this.role + ", userId=" + this.userId + "]";
	}
}
