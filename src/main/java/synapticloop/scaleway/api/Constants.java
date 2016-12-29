package synapticloop.scaleway.api;

/*
 * Copyright (c) 2016 Synapticloop.
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

public class Constants {
	public static final String PATH_IMAGES = "/images?page=%d&per_page=%d";
	public static final String PATH_IMAGES_SLASH = "/images/%s";

	public static final String PATH_VOLUMES = "/volumes?page=%d&per_page=%d";
	public static final String PATH_VOLUMES_SLASH = "/volumes/%s";

	public static final String ACCOUNT_URL = "https://account.scaleway.com";
	public static final String COMPUTE_URL = "https://cp-%s.scaleway.com";

	public static final String HEADER_AUTH_TOKEN="X-Auth-Token";

	public static final String JSON_APPLICATION="application/json";
	public static final String USER_AGENT = "synapticloop-scaleway-java-api";

	public static final String HTTP_METHOD_PATCH = "PATCH";
	public static final String HTTP_METHOD_PUT = "PUT";
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_DELETE = "DELETE";

}
