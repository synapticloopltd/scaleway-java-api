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

/**
 * Constants for various things
 * 
 */
public class Constants {
	//
	// Scaleway URLs
	//
	public static final String ACCOUNT_URL = "https://account.scaleway.com";
	public static final String COMPUTE_URL = "https://cp-%s.scaleway.com";

	//
	// Header keys, values and user agent values
	//
	public static final String HEADER_KEY_AUTH_TOKEN = "X-Auth-Token";
	public static final String HEADER_VALUE_JSON_APPLICATION = "application/json";
	public static final String USER_AGENT = "synapticloop-scaleway-java-api";


	//
	// HTTP Method constants
	//
	public static final String HTTP_METHOD_PATCH = "PATCH";
	public static final String HTTP_METHOD_PUT = "PUT";
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_DELETE = "DELETE";

	//
	// Paths for the various calls
	//
	public static final String PATH_IMAGES_PAGING = "/images?page=%d&per_page=%d";
	public static final String PATH_IMAGES_SLASH = "/images/%s";

	public static final Object PATH_IPS = "/ips";
	public static final String PATH_IPS_PAGING = "/ips?page=%d&per_page=%d";
	public static final String PATH_IPS_SLASH = "/ips/%s";

	public static final String PATH_ORGANIZATIONS = "/organizations";

	public static final String PATH_SECURITY_GROUPS = "/security_groups";
	public static final String PATH_SECURITY_GROUPS_PAGING = "/security_groups?page=%d&per_page=%d";
	public static final String PATH_SECURITY_GROUPS_SLASH = "/security_groups/%s";

	public static final String PATH_SERVERS = "/servers";
	public static final String PATH_SERVERS_SLASH = "/servers/%s";
	public static final String PATH_SERVERS_SLASH_ACTION = "/servers/%s/action";

	public static final String PATH_TASKS_SLASH = "/tasks/%s";

	public static final String PATH_TOKENS = "/tokens";
	public static final String PATH_TOKENS_PAGING = "/tokens?page=%d&per_page=%d";
	public static final String PATH_TOKENS_SLASH = "/tokens/%s";

	public static final String PATH_USERS_SLASH = "/users/%s";

	public static final String PATH_VOLUMES = "/volumes";
	public static final String PATH_VOLUMES_PAGING = "/volumes?page=%d&per_page=%d";
	public static final String PATH_VOLUMES_SLASH = "/volumes/%s";

}
