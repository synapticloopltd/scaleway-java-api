package synapticloop.scaleway.api.request;

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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A token request is used to encapsulate the JSON object for creating a token.
 */

public class TokenRequest {
	@JsonProperty("email")     private String emailAddress;
	@JsonProperty("password")  private String password;
	@JsonProperty("expires")   private boolean expires = true;

	/**
	 * Create a token request for access to the Scaleway API
	 * 
	 * @param emailAddress The email address for the person
	 * @param password The password for the person
	 * @param expires whether the token will expire
	 */
	public TokenRequest(String emailAddress, String password, boolean expires) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.expires = expires;
	}
}
