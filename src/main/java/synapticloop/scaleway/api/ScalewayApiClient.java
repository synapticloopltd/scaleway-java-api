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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Action;
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.model.ImageResponse;
import synapticloop.scaleway.api.model.ImagesResponse;
import synapticloop.scaleway.api.model.Organization;
import synapticloop.scaleway.api.model.Organizations;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.model.ServerActionsResponse;
import synapticloop.scaleway.api.model.ServerDefinition;
import synapticloop.scaleway.api.model.ServerInstance;
import synapticloop.scaleway.api.model.ServerInstances;
import synapticloop.scaleway.api.model.ServerTask;
import synapticloop.scaleway.api.model.ServerType;
import synapticloop.scaleway.api.model.SshPublicKey;
import synapticloop.scaleway.api.model.SshPublicKeyResponse;
import synapticloop.scaleway.api.model.State;
import synapticloop.scaleway.api.model.TaskResponse;
import synapticloop.scaleway.api.model.User;
import synapticloop.scaleway.api.model.UserResponse;

public class ScalewayApiClient {

	private static final String PATH_SERVERS = "servers";
	private static final String PATH_SERVERS_SLASH = "servers/%s";
	private static final String PATH_SERVERS_SLASH_ACTION = "servers/%s/action";
	private static final String PATH_SERVERS_SLASH_ACTION_SLASH = "servers/%s/action/%s";

	private final String accessToken;
	private final Region region;
	private final String computeUrl;
	private final CloseableHttpClient httpclient;

	/**
	 * 
	 * @param accessToken
	 * @param organizationToken
	 * @param region
	 */
	public ScalewayApiClient(String accessToken, Region region) {
		this.accessToken = accessToken;
		this.region = region;
		this.computeUrl = String.format(Constants.COMPUTE_URL, region);

		HttpClientBuilder httpBuilder = HttpClients.custom();
		httpBuilder.setUserAgent(Constants.USER_AGENT);
		this.httpclient = httpBuilder.build();
	}

	public Region getRegion() {
		return region;
	}

	public List<Image> getAllImages() throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, computeUrl, Constants.PATH_IMAGES);
		return(executeAndGetResponse(request, 200, ImagesResponse.class).getImages());
	}

	public Image getImage(String imageId) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, computeUrl, String.format(Constants.PATH_IMAGES_SLASH, imageId));
		return(executeAndGetResponse(request, 200, ImageResponse.class).getImage());

	}

	public Server createServer(String serverName, Image image, String organizationToken, ServerType serverType, String... tag) throws ScalewayApiException {
		ServerDefinition serverDefinition = new ServerDefinition();
		serverDefinition.setName(serverName);
		serverDefinition.setImage(image.getId());
		serverDefinition.setOrganization(organizationToken);
		serverDefinition.setDynamicIpRequired(true);
		serverDefinition.setTags(Arrays.asList(tag));
		serverDefinition.setServerType(serverType);
		return createServer(serverDefinition);
	}

	public void deleteServer(Server server) throws ScalewayApiException {
		deleteServer(server.getId());
	}

	public void deleteServer(String serverId) throws ScalewayApiException {
		Server serverToDelete = getServer(serverId);
		if (serverToDelete.getState() != State.STOPPED) {
			// TODO something
		}
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_DELETE, computeUrl, String.format(PATH_SERVERS_SLASH, serverId));
		executeAndGetResponse(request, 204, null);
	}

	public Server createServer(ServerDefinition serverDefinition) throws ScalewayApiException {
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, computeUrl, PATH_SERVERS);
		return(executeAndGetResponse(request, 201, ServerInstance.class).getServer());
	}

	public List<Server> getAllServers() throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, computeUrl, PATH_SERVERS);
		return(executeAndGetResponse(request, 200, ServerInstances.class).getServers());
	}

	public Server getServer(String serverID) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, computeUrl, new StringBuilder(PATH_SERVERS_SLASH).append(serverID).toString());
		return(executeAndGetResponse(request, 200, ServerInstance.class).getServer());
	}

	public List<Action> getServerActions(String serverID) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, computeUrl, String.format(PATH_SERVERS_SLASH_ACTION, serverID));
		return(executeAndGetResponse(request, 200, ServerActionsResponse.class).getActions());
	}

	public ServerTask executeServerActionSync(Server server, Action action) throws ScalewayApiException, InterruptedException {
		ServerTask task = executeServerAction(server, action);
		return getTaskResult(task);
	}

	public ServerTask executeServerActionSync(String serverId, Action action) throws ScalewayApiException, InterruptedException {
		ServerTask task = executeServerAction(serverId, action);
		return getTaskResult(task);
	}

	public ServerTask executeServerAction(Server server, Action action) throws ScalewayApiException {
		return executeServerAction(server.getId(), action);
	}

	public ServerTask executeServerAction(String serverID, Action action) throws ScalewayApiException {
		HttpPost request = (HttpPost) buildRequest(Constants.HTTP_METHOD_POST, computeUrl, String.format(PATH_SERVERS_SLASH_ACTION_SLASH, serverID, action));
		return(executeAndGetResponse(request, 202, TaskResponse.class).getServerTask());
	}

	public ServerTask getTaskStatus(ServerTask task) throws ScalewayApiException {
		return getTaskStatus(task.getId());
	}

	public ServerTask getTaskStatus(String taskID) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, computeUrl, new StringBuilder("tasks/").append(taskID).toString());
		return(executeAndGetResponse(request, 200, TaskResponse.class).getServerTask());
	}

	public List<Organization> getAllOrganizations() throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, Constants.ACCOUNT_URL, "organizations");
		return(executeAndGetResponse(request, 200, Organizations.class).getOrganizations());
	}

	public User getUser(String userID) throws ScalewayApiException {
		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, Constants.ACCOUNT_URL, new StringBuilder("users/").append(userID).toString());
		return(executeAndGetResponse(request, 200, UserResponse.class).getUser());
	}
	
//	private <T> T buildAndExecute(String methodType, String url, String apiCall, int allowableStatus, Class<T> responseType) {
////		HttpRequestBase request = buildRequest(Constants.HTTP_METHOD_GET, Constants.ACCOUNT_URL, new StringBuilder("users/").append(userID).toString());
////		return(executeAndGetResponse(request, 200, ScalewayUserResponse.class).getUser());
//
////		String requestPath = new StringBuilder(typeUrl).append("/").append(method).toString();
////		return buildRequest(type, requestPath);
//
//	}

	public void addSSHKey(String userID, SshPublicKey userKeyDefinition) throws ScalewayApiException {
		Set sshKeys = new HashSet(getUser(userID).getSshPublicKeys());
		sshKeys.add(userKeyDefinition);
		SshPublicKeyResponse keyDefinitions = new SshPublicKeyResponse();
		keyDefinitions.setSshPublicKeys(new ArrayList<SshPublicKey>(sshKeys));
		modifySSHKeys(userID, keyDefinitions);
	}

	public void modifySSHKeys(String userID, SshPublicKeyResponse userKeysDefinition) throws ScalewayApiException {
		HttpPatch request = (HttpPatch) buildRequest(Constants.HTTP_METHOD_PATCH, Constants.ACCOUNT_URL, new StringBuilder("users/").append(userID).toString());
		setResponseObject(request, userKeysDefinition);
		executeAndGetResponse(request, 200, null);
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

	public ServerTask getTaskResult(ServerTask serverTask) throws InterruptedException, ScalewayApiException {
		while (serverTask.getTerminatedAt() == null) {
			Thread.sleep(1000);
			serverTask = getTaskStatus(serverTask);
		}
		return serverTask;
	}

	private HttpRequestBase buildRequest(String type, String typeUrl, String method) {
		String requestPath = new StringBuilder(typeUrl).append("/").append(method).toString();
		return buildRequest(type, requestPath);
	}

	private HttpRequestBase buildRequest(String type, String requestPath) {
		HttpRequestBase request = null;
		switch (type) {
		case Constants.HTTP_METHOD_POST:
			request = new HttpPost(requestPath);
			break;
		case Constants.HTTP_METHOD_GET:
			request = new HttpGet(requestPath);
			break;
		case Constants.HTTP_METHOD_DELETE:
			request = new HttpDelete(requestPath);
			break;
		case Constants.HTTP_METHOD_PATCH:
			request = new HttpPatch(requestPath);
			break;
		}

		request.setHeader(Constants.HEADER_AUTH_TOKEN, accessToken);
		request.setHeader(HttpHeaders.CONTENT_TYPE, Constants.JSON_APPLICATION);

		return request;
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
			System.out.println(jsonString);
			throw ex;
		}
	}

	private String formatJson(Object entity) throws JsonProcessingException {
		return initializeObjectMapperJson().writeValueAsString(entity);
	}
}
