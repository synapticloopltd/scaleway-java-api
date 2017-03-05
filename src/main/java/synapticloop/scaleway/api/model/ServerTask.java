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

	public String getDescription() { return description; }

	public String getHrefFrom() { return hrefFrom; }

	public String getId() { return id; }

	public String getProgress() { return progress; }

	public ServerTaskStatus getStatus() { return status; }

	public Date getTerminatedAt() { return terminatedAt; }

	public Date getStartedAt() { return startedAt; }

}
