package synapticloop.scaleway.api;

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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.IP;
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.model.Organization;
import synapticloop.scaleway.api.model.Organizations;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.model.ServerAction;
import synapticloop.scaleway.api.model.ServerDefinition;
import synapticloop.scaleway.api.model.ServerTask;
import synapticloop.scaleway.api.model.ServerType;
import synapticloop.scaleway.api.model.Token;
import synapticloop.scaleway.api.model.User;
import synapticloop.scaleway.api.model.Volume;
import synapticloop.scaleway.api.model.VolumeType;
import synapticloop.scaleway.api.request.ActionRequest;
import synapticloop.scaleway.api.request.IPPutRequest;
import synapticloop.scaleway.api.request.IPRequest;
import synapticloop.scaleway.api.request.TokenPatchRequest;
import synapticloop.scaleway.api.request.TokenRequest;
import synapticloop.scaleway.api.request.VolumeRequest;
import synapticloop.scaleway.api.response.IPResponse;
import synapticloop.scaleway.api.response.IPsResponse;
import synapticloop.scaleway.api.response.ImageResponse;
import synapticloop.scaleway.api.response.ImagesResponse;
import synapticloop.scaleway.api.response.ServerActionsResponse;
import synapticloop.scaleway.api.response.ServerResponse;
import synapticloop.scaleway.api.response.ServersResponse;
import synapticloop.scaleway.api.response.TaskResponse;
import synapticloop.scaleway.api.response.TokenResponse;
import synapticloop.scaleway.api.response.TokensResponse;
import synapticloop.scaleway.api.response.UserResponse;
import synapticloop.scaleway.api.response.VolumeResponse;
import synapticloop.scaleway.api.response.VolumesResponse;

/**
 * This is the Scaleway API client to interact with the cloud provider
 * 
 * https://www.scaleway.com/
 *
 */
public class ScalewayApiClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScalewayApiClient.class);

	private final String accessToken;
	private final Region region;
	private final String computeUrl;
	private final CloseableHttpClient httpclient;

	/**
	 * Instantiate a new API Client for the Scaleway API Provider
	 * 
	 * @param accessToken the access token
	 * @param region the region that this should point to
	 */
	public ScalewayApiClient(String accessToken, Region region) {
		this.accessToken = accessToken;
		this.region = region;
		this.computeUrl = String.format(Constants.COMPUTE_URL, region);

		HttpClientBuilder httpBuilder = HttpClients.custom();
		httpBuilder.setUserAgent(Constants.USER_AGENT);
		this.httpclient = httpBuilder.build();
	}

	/**
	 * get the region that this API is pointing to
	 * 
	 * @return
	 */
	public Region getRegion() {
		return region;
	}


	/**
	 * List all of the organizations 
	 * 
	 * @return a list of all of the organizations
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public List<Organization> getAllOrganizations() throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				Constants.ACCOUNT_URL, 
				Constants.PATH_ORGANIZATIONS,
				200, 
				Organizations.class).getOrganizations());
	}

	/**
	 * Get the user information 
	 * 
	 * @param userId the ID of the user to retrieve
	 * 
	 * @return the user identified by the ID
	 * 
	 * @throws ScalewayApiException If there was an error in the API call
	 */
	public User getUser(String userId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				Constants.ACCOUNT_URL, 
				String.format(Constants.PATH_USERS_SLASH, userId),
				200, 
				UserResponse.class).getUser());
	}

	/**
	 * A convenience method to create a server, which has a dynamic IP attached to 
	 * it. 
	 * 
	 * @param serverName The name of the server
	 * @param imageId the ID of the image to use as the base
	 * @param organizationToken the organization token
	 * @param serverType the Type of Server
	 * @param tags The tags to apply to this server
	 * 
	 * @return The newly created server
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Server createServer(String serverName, String imageId, String organizationToken, ServerType serverType, String... tags) throws ScalewayApiException {
		ServerDefinition serverDefinition = new ServerDefinition();
		serverDefinition.setName(serverName);
		serverDefinition.setImage(imageId);
		serverDefinition.setOrganization(organizationToken);
		serverDefinition.setDynamicIpRequired(true);
		serverDefinition.setTags(Arrays.asList(tags));
		serverDefinition.setServerType(serverType);
		return createServer(serverDefinition);
	}


	/**
	 * Create a server
	 * 
	 * @param serverDefinition The server definition to create
	 * 
	 * @return The created server
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Server createServer(ServerDefinition serverDefinition) throws ScalewayApiException {
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, 
				new StringBuilder(computeUrl).append(Constants.PATH_SERVERS).toString(), 
				serverDefinition);

		return(executeAndGetResponse(request, 201, ServerResponse.class).getServer());
	}

	/**
	 * Get the server details with the passed in server ID
	 * 
	 * @param serverId The ID of the server to retrieve the details for
	 * 
	 * @return The server object
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Server getServer(String serverId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				computeUrl, 
				String.format(Constants.PATH_SERVERS_SLASH, serverId), 
				200, 
				ServerResponse.class).getServer());
	}

	/**
	 * Get a list of all of the servers paginated, pages start at 1, maximum 
	 * number of results per page is 100.
	 * 
	 * @param numPage the page number that you are requesting (starts at 1)
	 * @param numPerPage the number of results per page - (maximum value of 100)
	 * 
	 * @return The list of all avilable servers
	 *  
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public ServersResponse getAllServers(int numPage, int numPerPage) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, new StringBuilder(computeUrl).append(String.format(Constants.PATH_SERVERS, numPage, numPerPage)).toString());
		HttpResponse response = executeRequest(request);
		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			ServersResponse serversResponse = parseResponse(response, ServersResponse.class);
			serversResponse.parsePaginationHeaders(allHeaders);
			return(serversResponse);
		} else {
			try {
				throw new ScalewayApiException(IOUtils.toString(response.getEntity().getContent()));
			} catch (UnsupportedOperationException | IOException ex) {
				throw new ScalewayApiException(ex);
			}
		}
	}

	/*
	 * Update a server
	 * 
	 * @param server The server with the updated information
	 * 
	 * @return The returned server object
	 * 
	 * @throws ScalewayApiException If there ws an error with the lAPI call
	 *
	public Server updateServer(Server server) throws ScalewayApiException {
		HttpPut request = (HttpPut) buildRequest(Constants.HTTP_METHOD_PUT, computeUrl, String.format(PATH_SERVERS_SLASH, server.getId()));

		try {
			StringEntity entity = new StringEntity(serializeObject(server));
			request.setEntity(entity);
			return(executeAndGetResponse(request, 200, ServerResponse.class).getServer());
		} catch (UnsupportedEncodingException | JsonProcessingException ex) {
			throw new ScalewayApiException(ex);
		}
	}
	 */

	/**
	 * Get a list of all of the available images - with the results coming back 
	 * paginated, pages start at 1, maximum number of results per page is 100.
	 * 
	 * @param numPage the page number that you are requesting (starts at 1)
	 * @param numPerPage the number of results per page - (maximum value of 100)
	 * 
	 * @return The list of all available images
	 * 
	 * @throws ScalewayApiException If there was an error with the call
	 */
	public ImagesResponse getAllImages(int numPage, int numPerPage) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, new StringBuilder(computeUrl).append(String.format(Constants.PATH_IMAGES_PAGING, numPage, numPerPage)).toString());
		HttpResponse response = executeRequest(request);
		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			ImagesResponse imagesResponse = parseResponse(response, ImagesResponse.class);
			imagesResponse.parsePaginationHeaders(allHeaders);
			return(imagesResponse);
		} else {
			try {
				throw new ScalewayApiException(IOUtils.toString(response.getEntity().getContent()));
			} catch (UnsupportedOperationException | IOException ex) {
				throw new ScalewayApiException(ex);
			}
		}
	}

	/**
	 * Get the image details with the specified id
	 * 
	 * @param imageId The ID of the image
	 * 
	 * @return The image
	 * 
	 * @throws ScalewayApiException If there was an error calling the API
	 */
	public Image getImage(String imageId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				computeUrl, 
				String.format(Constants.PATH_IMAGES_SLASH, imageId), 
				200, 
				ImageResponse.class).getImage());
	}



	/**
	 * Delete a server - this will only delete the actual server instance, but 
	 * not any of the underlying resources.  If you wish to kill all resources
	 * (apart from reserved IP addresses) try:
	 * 
	 * scalewayApiClient.executeServerAction(server.getId(), ServerAction.TERMINATE);
	 * 
	 * @param serverId The ID of the server to delete
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public void deleteServer(String serverId) throws ScalewayApiException {
		execute(Constants.HTTP_METHOD_DELETE, 
				computeUrl, 
				String.format(Constants.PATH_SERVERS_SLASH, serverId), 
				204, 
				null);
	}

	/**
	 * Get the actions that are available for the server
	 * 
	 * @param serverId The id of the server to get the actions
	 * 
	 * @return The list of available actions for the server
	 * 
	 * @throws ScalewayApiException If there was an error with the API calls
	 */
	public List<ServerAction> getServerActions(String serverId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				computeUrl, 
				String.format(Constants.PATH_SERVERS_SLASH_ACTION, serverId),
				200, 
				ServerActionsResponse.class).getServerActions());
	}

	/**
	 * Create a volume 
	 * 
	 * @param name The name of the volume to create
	 * @param organizationId The organization ID
	 * @param size the size of the volume which must be more than 1000000000 (1GB) 
	 *     and less than 150000000000 (150GB).
	 * @param volumeType the type of the volume
	 * 
	 * @return The newly created volume
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Volume createVolume(String name, String organizationId, long size, VolumeType volumeType) throws ScalewayApiException {
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, 
				new StringBuilder(computeUrl).append(Constants.PATH_VOLUMES).toString(),
				new VolumeRequest(name, organizationId, size, volumeType));

		return(executeAndGetResponse(request, 201, VolumeResponse.class).getVolume());
	}

	/**
	 * Get a list of all of the available volumes - with the results coming back 
	 * paginated, pages start at 1, maximum number of results per page is 100.
	 * 
	 * @param numPage the page number that you are requesting (starts at 1)
	 * @param numPerPage the number of results per page - (maximum value of 100)
	 * 
	 * @return The list of all available volumes
	 * 
	 * @throws ScalewayApiException If there was an error with the call
	 */
	public VolumesResponse getAllVolumes(int numPage, int numPerPage) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, new StringBuilder(computeUrl).append(String.format(Constants.PATH_VOLUMES_PAGING, numPage, numPerPage)).toString());
		HttpResponse response = executeRequest(request);

		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			VolumesResponse volumesResponse = parseResponse(response, VolumesResponse.class);
			volumesResponse.parsePaginationHeaders(allHeaders);
			return(volumesResponse);
		} else {
			try {
				throw new ScalewayApiException(IOUtils.toString(response.getEntity().getContent()));
			} catch (UnsupportedOperationException | IOException ex) {
				throw new ScalewayApiException(ex);
			}
		}

	}

	/**
	 * Get a volume from the passed in volume ID
	 * 
	 * @param volumeId The ID of the volume
	 * 
	 * @return the volume for the passed in ID
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Volume getVolume(String volumeId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				computeUrl, 
				String.format(Constants.PATH_VOLUMES_SLASH, volumeId), 
				200, 
				VolumeResponse.class).getVolume());

	}

	/**
	 * Delete a volume by its ID
	 * 
	 * @param volumeId the id of the volume to delete
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public void deleteVolume(String volumeId) throws ScalewayApiException {
		execute(Constants.HTTP_METHOD_DELETE, 
				computeUrl, 
				String.format(Constants.PATH_VOLUMES_SLASH, volumeId), 
				204, 
				null);
	}



	/**
	 * Execute the server action for the server identified by the ID
	 * 
	 * @param serverId The ID of the server to execute the action on
	 * @param serverAction the server action to perform
	 * 
	 * @return The task associated with the action
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public ServerTask executeServerAction(String serverId, ServerAction serverAction) throws ScalewayApiException {

		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, 
				new StringBuilder(computeUrl).append(String.format(Constants.PATH_SERVERS_SLASH_ACTION, serverId)).toString(),
				new ActionRequest(serverAction));

		return(executeAndGetResponse(request, 202, TaskResponse.class).getServerTask());
	}

	/**
	 * Get the status of a task
	 * 
	 * @param taskId The id of the task
	 * 
	 * @return The server task which includes the status
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public ServerTask getTaskStatus(String taskId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				computeUrl, 
				String.format(Constants.PATH_TASKS_SLASH, taskId),
				200, 
				TaskResponse.class).getServerTask());
	}

	/**
	 * Create an access token for the Scaleway API
	 * 
	 * @param emailAddress the email address
	 * @param password the password
	 * @param expires whether this token expires
	 * @return 
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Token createToken(String emailAddress, String password, boolean expires) throws ScalewayApiException {
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, 
				new StringBuilder(Constants.ACCOUNT_URL).append(Constants.PATH_TOKENS).toString(), 
				new TokenRequest(emailAddress, password, expires));

		return(executeAndGetResponse(request, 201, TokenResponse.class).getToken());
	}

	/**
	 * Get the details for a token identified by its token ID
	 * 
	 * @param tokenId The unique identifier for the token
	 * 
	 * @return The token details
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Token getToken(String tokenId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				Constants.ACCOUNT_URL, 
				String.format(Constants.PATH_TOKENS_SLASH, tokenId),
				200, 
				TokenResponse.class).getToken());
	}

	/**
	 * Delete a token with the specified ID
	 * 
	 * @param tokenId The token id
	 * 
	 * @throws ScalewayApiException If there was an error in with the API call
	 */
	public void deleteToken(String tokenId) throws ScalewayApiException {
		execute(Constants.HTTP_METHOD_DELETE, 
				Constants.ACCOUNT_URL, 
				String.format(Constants.PATH_TOKENS_SLASH, tokenId), 
				204, 
				null);
	}

	/**
	 * Update a token to extend its expiration time by 30 minutes
	 * 
	 * @param tokenId The ID of the token to update
	 * @return The updated token with the new expiry date/time
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public Token updateToken(String tokenId) throws ScalewayApiException {
		HttpPatch request = (HttpPatch) buildRequest(Constants.HTTP_METHOD_PATCH, 
				new StringBuilder(Constants.ACCOUNT_URL).append(String.format(Constants.PATH_TOKENS_SLASH, tokenId)).toString(), 
				new TokenPatchRequest());

		return(executeAndGetResponse(request, 200, TokenResponse.class).getToken());
	}

	/**
	 * Retrieve a list of all tokens with pagination, pages start at 1 and the 
	 * maximum number of results per page is 100.
	 * 
	 * <strong>WARNING</strong> - you will get a list of tokens far longer than
	 * the number of available tokens - some, if not all of the tokens will no
	 * longer be valid and will return a '410' Gone if you use this token with 
	 * the getTokens(String tokenId) call.
	 * 
	 * @param numPage the page number to retrieve (starting at 1)
	 * @param numPerPage The number of results per page (maximum 100)
	 * 
	 * @return The list of tokens
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public TokensResponse getAllTokens(int numPage, int numPerPage) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, 
				new StringBuilder(Constants.ACCOUNT_URL).append(String.format(Constants.PATH_TOKENS_PAGING, numPage, numPerPage)).toString());

		HttpResponse response = executeRequest(request);

		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			TokensResponse tokensResponse = parseResponse(response, TokensResponse.class);
			tokensResponse.parsePaginationHeaders(allHeaders);
			return(tokensResponse);
		} else {
			try {
				throw new ScalewayApiException(IOUtils.toString(response.getEntity().getContent()));
			} catch (UnsupportedOperationException | IOException ex) {
				throw new ScalewayApiException(ex);
			}
		}
	}

	/**
	 * Create a new reserved IP Address
	 * 
	 * @param organizationId The ID of the organization to attach this IP address to
	 * 
	 * @return The newly created IP Address
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public IP createIP(String organizationId) throws ScalewayApiException {
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, 
				new StringBuilder(computeUrl).append(Constants.PATH_IPS).toString(),
				new IPRequest(organizationId));

		return(executeAndGetResponse(request, 201, IPResponse.class).getIP());

	}

	/**
	 * Return a paginated list of the reserved IP addresses associated with the 
	 * account
	 * 
	 * @param numPage the page number to retrieve (starting at 1)
	 * @param numPerPage The number of results per page (maximum 100)
	 * 
	 * @return The list of reserved IP address associated with the account
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public IPsResponse getAllIPs(int numPage, int numPerPage) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, 
				new StringBuilder(computeUrl).append(String.format(Constants.PATH_IPS_PAGING, numPage, numPerPage)).toString());

		HttpResponse response = executeRequest(request);

		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			IPsResponse ipsResponse = parseResponse(response, IPsResponse.class);
			ipsResponse.parsePaginationHeaders(allHeaders);
			return(ipsResponse);
		} else {
			try {
				throw new ScalewayApiException(IOUtils.toString(response.getEntity().getContent()));
			} catch (UnsupportedOperationException | IOException ex) {
				throw new ScalewayApiException(ex);
			}
		}
	}

	/**
	 * Get the reserved IP address details 
	 * 
	 * @param ipId the unique identifier for the IP address
	 * 
	 * @return The reserved IP address details
	 * 
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	public IP getIP(String ipId) throws ScalewayApiException {
		return(execute(Constants.HTTP_METHOD_GET, 
				computeUrl, 
				String.format(Constants.PATH_IPS_SLASH, ipId),
				200, 
				IPResponse.class).getIP());
	}

	/**
	 * Attach an existing reserved IP address to a server
	 * 
	 * @param ipId The id of the IP Address
	 * @param organizationId The ID of the organization
	 * @param ipAddress The IP Address
	 * @param serverId The ID of the Server
	 * 
	 * @return 
	 * @throws ScalewayApiException
	 */
	public IP attachIP(String ipId, String organizationId, String ipAddress, String serverId) throws ScalewayApiException {
		HttpPut request = (HttpPut) buildRequest(Constants.HTTP_METHOD_PUT, 
				new StringBuilder(computeUrl).append(String.format(Constants.PATH_IPS_SLASH, ipId)).toString(), 
				new IPPutRequest(ipAddress, ipId, serverId, organizationId));

		return(executeAndGetResponse(request, 200, IPResponse.class).getIP());
	}


	public void deleteIP(String ipId) throws ScalewayApiException {
		execute(Constants.HTTP_METHOD_DELETE, 
				computeUrl, 
				String.format(Constants.PATH_IPS_SLASH, ipId), 
				204, 
				null);
	}










	/**
	 * Serialize an object to JSON
	 * 
	 * @param object The object to serialize
	 * 
	 * @return The object serialized as a JSON String
	 * 
	 * @throws JsonProcessingException if there was an error serializing
	 */
	private String serializeObject(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return(objectMapper.writeValueAsString(object));
	}

	/**
	 * Execute the request, returning the parsed response object
	 * 
	 * @param httpMethod The HTTP method to execute (GET/POST/PATCH/DELETE/PUT)
	 * @param url The URL to hit
	 * @param path the path to request
	 * @param allowableStatusCode the allowable return HTTP status code
	 * @param returnClass the return class type
	 * 
	 * @return The returned and parsed response
	 * 
	 * @throws ScalewayApiException If there was an error calling the api
	 */
	private <T> T execute(String httpMethod, String url, String path, int allowableStatusCode, Class<T> returnClass) throws ScalewayApiException {
		String requestPath = new StringBuilder(url).append(path).toString();

		HttpRequestBase request = buildRequest(httpMethod, requestPath);

		request.setHeader(Constants.HEADER_KEY_AUTH_TOKEN, accessToken);
		request.setHeader(HttpHeaders.CONTENT_TYPE, Constants.HEADER_VALUE_JSON_APPLICATION);

		HttpResponse response;
		try {
			LOGGER.debug("Executing '{}' for url '{}'", httpMethod, requestPath);
			response = httpclient.execute(request);
		} catch (IOException ex) {
			throw new ScalewayApiException(ex);
		}

		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == allowableStatusCode) {
			LOGGER.debug("Status code received: {}, wanted: {}.", statusCode, allowableStatusCode);
			if(null != returnClass) {
				return parseResponse(response, returnClass);
			} else {
				return(null);
			}
		} else {
			LOGGER.error("Invalid status code received: {}, wanted: {}.", statusCode, allowableStatusCode);
			try {
				throw new ScalewayApiException(IOUtils.toString(response.getEntity().getContent()));
			} catch (UnsupportedOperationException | IOException ex) {
				throw new ScalewayApiException(ex);
			}
		}
	}

	/**
	 * Build the request for the URI - which just returns a HttpRequestBase Object 
	 * of the correct matching type for the passed in httpMethod
	 * 
	 * @param httpMethod  The HTTP method to build for
	 * @param requestPath The full URI to build
	 * 
	 * @return the base HTTP object
	 * @throws ScalewayApiException If there was an error with the API call
	 */
	private HttpRequestBase buildRequest(String httpMethod, String requestPath) throws ScalewayApiException {
		return(buildRequest(httpMethod, requestPath, null));
	}

	private HttpRequestBase buildRequest(String httpMethod, String requestPath, Object entityContent) throws ScalewayApiException {
		LOGGER.debug("Building request for method '{}' and URL '{}'", httpMethod, requestPath);

		HttpRequestBase request = null;
		switch (httpMethod) {
		case Constants.HTTP_METHOD_GET:
			request = new HttpGet(requestPath);
			break;
		case Constants.HTTP_METHOD_POST:
			request = new HttpPost(requestPath);
			break;
		case Constants.HTTP_METHOD_DELETE:
			request = new HttpDelete(requestPath);
			break;
		case Constants.HTTP_METHOD_PATCH:
			request = new HttpPatch(requestPath);
			break;
		case Constants.HTTP_METHOD_PUT:
			request = new HttpPut(requestPath);
			break;
		}

		request.setHeader(Constants.HEADER_KEY_AUTH_TOKEN, accessToken);
		request.setHeader(HttpHeaders.CONTENT_TYPE, Constants.HEADER_VALUE_JSON_APPLICATION);

		if(null != entityContent) {
			if(request instanceof HttpEntityEnclosingRequestBase) {
				try {
					StringEntity entity = new StringEntity(serializeObject(entityContent));
					((HttpEntityEnclosingRequestBase)request).setEntity(entity);
				} catch (UnsupportedEncodingException | JsonProcessingException ex) {
					throw new ScalewayApiException(ex);
				}
			} else {
				LOGGER.error("Attempting to set entity on non applicable base class of '{}'", request.getClass());
			}
		}
		return request;
	}

	private <T> T executeAndGetResponse(HttpRequestBase request, int allowableStatusCode, Class<T> returnClass) throws ScalewayApiException {
		HttpResponse response = executeRequest(request);
		if (response.getStatusLine().getStatusCode() == allowableStatusCode) {
			if(null != returnClass) {
				return parseResponse(response, returnClass);
			} else {
				return(null);
			}
		} else {
			try {
				throw new ScalewayApiException(IOUtils.toString(response.getEntity().getContent()));
			} catch (UnsupportedOperationException | IOException ex) {
				throw new ScalewayApiException(ex);
			}
		}
	}

	private HttpResponse executeRequest(HttpRequestBase request) throws ScalewayApiException {
		try {
			return httpclient.execute(request);
		} catch (IOException ex) {
			throw new ScalewayApiException(ex);
		}
	}

	private <T> T parseResponse(HttpResponse response, Class<T> entityClass) throws ScalewayApiException {
		try {
			return parseJson(response.getEntity(), entityClass);
		} catch (IOException ex) {
			throw new ScalewayApiException(ex);
		}
	}

	private ObjectMapper initializeObjectMapperJson() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.enable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
		return mapper;
	}

	private <T> T parseJson(HttpEntity responseEntity, Class<T> type) throws IOException {
		String encoding = responseEntity.getContentEncoding() != null ? responseEntity.getContentEncoding().getValue() : "UTF-8";
		String jsonString = IOUtils.toString(responseEntity.getContent(), encoding);
		try {
			return initializeObjectMapperJson().readValue(jsonString, type);
		} catch (Exception ex) {
			LOGGER.error("%s", jsonString);
			throw ex;
		}
	}
}
