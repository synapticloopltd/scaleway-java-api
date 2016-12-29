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
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.model.ImageResponse;
import synapticloop.scaleway.api.model.ImagesResponse;
import synapticloop.scaleway.api.model.Organization;
import synapticloop.scaleway.api.model.Organizations;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.model.ServerAction;
import synapticloop.scaleway.api.model.ServerActionsResponse;
import synapticloop.scaleway.api.model.ServerDefinition;
import synapticloop.scaleway.api.model.ServerResponse;
import synapticloop.scaleway.api.model.ServerTask;
import synapticloop.scaleway.api.model.ServerType;
import synapticloop.scaleway.api.model.ServersResponse;
import synapticloop.scaleway.api.model.TaskResponse;
import synapticloop.scaleway.api.model.User;
import synapticloop.scaleway.api.model.UserResponse;
import synapticloop.scaleway.api.model.Volume;
import synapticloop.scaleway.api.model.VolumeResponse;
import synapticloop.scaleway.api.model.VolumeType;
import synapticloop.scaleway.api.model.VolumesResponse;

public class ScalewayApiClient {
	private static final String PATH_TASKS_SLASH = "/tasks/%s";

	private static final String PATH_ORGANIZATIONS = "/organizations";

	private static final String PATH_USERS_SLASH = "/users/%s";

	private static final String PATH_VOLUMES = "/volumes";

	private static final Logger LOGGER = LoggerFactory.getLogger(ScalewayApiClient.class);

	private static final String PATH_SERVERS = "/servers";
	private static final String PATH_SERVERS_SLASH = "/servers/%s";
	private static final String PATH_SERVERS_SLASH_ACTION = "/servers/%s/action";

	private final String accessToken;
	private final Region region;
	private final String computeUrl;
	private final CloseableHttpClient httpclient;

	/**
	 * Instantiate a new API Client for Saleway
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
				PATH_ORGANIZATIONS,
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
				String.format(PATH_USERS_SLASH, userId),
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
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, new StringBuilder(computeUrl).append(PATH_SERVERS).toString());
		try {
			StringEntity entity = new StringEntity(serializeObject(serverDefinition));
			request.setEntity(entity);
			return(executeAndGetResponse(request, 201, ServerResponse.class).getServer());
		} catch (JsonProcessingException | UnsupportedEncodingException ex) {
			throw new ScalewayApiException(ex);
		}
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
				String.format(PATH_SERVERS_SLASH, serverId), 
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
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, new StringBuilder(computeUrl).append(String.format(PATH_SERVERS, numPage, numPerPage)).toString());
		HttpResponse response = executeRequest(request);
		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			ServersResponse serversResponse = parseResponse(response, ServersResponse.class);
			serversResponse.setPaginationHeaders(allHeaders);
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
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, new StringBuilder(computeUrl).append(String.format(Constants.PATH_IMAGES, numPage, numPerPage)).toString());
		HttpResponse response = executeRequest(request);
		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			ImagesResponse imagesResponse = parseResponse(response, ImagesResponse.class);
			imagesResponse.setPaginationHeaders(allHeaders);
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
				String.format(PATH_SERVERS_SLASH, serverId), 
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
				String.format(PATH_SERVERS_SLASH_ACTION, serverId),
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
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, new StringBuilder(computeUrl).append(PATH_VOLUMES).toString());

		Volume volume = new Volume();
		volume.setName(name);
		volume.setOrganizationId(organizationId);
		volume.setSize(size);
		volume.setVolumeType(volumeType);

		try {
			StringEntity entity = new StringEntity(serializeObject(volume));
			request.setEntity(entity);
			return(executeAndGetResponse(request, 201, VolumeResponse.class).getVolume());
		} catch (UnsupportedEncodingException | JsonProcessingException ex) {
			throw new ScalewayApiException(ex);
		}
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
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, new StringBuilder(computeUrl).append(String.format(Constants.PATH_VOLUMES, numPage, numPerPage)).toString());
		HttpResponse response = executeRequest(request);

		if(response.getStatusLine().getStatusCode() == 200) {
			Header[] allHeaders = response.getAllHeaders();
			VolumesResponse volumesResponse = parseResponse(response, VolumesResponse.class);
			volumesResponse.setPaginationHeaders(allHeaders);
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
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, new StringBuilder(computeUrl).append(String.format(PATH_SERVERS_SLASH_ACTION, serverId)).toString());

		try {
			StringEntity entity = new StringEntity(String.format("{\"action\": \"%s\"}", serverAction.toString().toLowerCase()));
			request.setEntity(entity);
			return(executeAndGetResponse(request, 202, TaskResponse.class).getServerTask());
		} catch (UnsupportedEncodingException ex) {
			throw new ScalewayApiException(ex);
		}
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
				String.format(PATH_TASKS_SLASH, taskId),
				200, 
				TaskResponse.class).getServerTask());
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

		request.setHeader(Constants.HEADER_AUTH_TOKEN, accessToken);
		request.setHeader(HttpHeaders.CONTENT_TYPE, Constants.JSON_APPLICATION);

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
	 */
	private HttpRequestBase buildRequest(String httpMethod, String requestPath) {
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

		request.setHeader(Constants.HEADER_AUTH_TOKEN, accessToken);
		request.setHeader(HttpHeaders.CONTENT_TYPE, Constants.JSON_APPLICATION);

		return request;
	}
















	private void setResponseObject(HttpEntityEnclosingRequestBase request, Object responseObject) throws ScalewayApiException {
		try {
			request.setEntity(new StringEntity(formatJson(responseObject), "UTF-8"));
		} catch (JsonProcessingException ex) {
			throw new ScalewayApiException(ex);
		}
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

	private String formatJson(Object entity) throws JsonProcessingException {
		return initializeObjectMapperJson().writeValueAsString(entity);
	}
}
