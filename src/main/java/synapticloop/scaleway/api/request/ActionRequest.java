package synapticloop.scaleway.api.request;

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

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.scaleway.api.model.ServerAction;

/**
 * A token request is used to encapsulate the JSON object for creating a token.
 */

public class ActionRequest {
	@JsonProperty("action")  private String action;

	/**
	 * Create an action request for server actions Scaleway API
	 * 
	 * @param serverAction The action to perform on the server
	 */
	public ActionRequest(ServerAction serverAction) {
		this.action = serverAction.toString().toLowerCase();
	}
}
