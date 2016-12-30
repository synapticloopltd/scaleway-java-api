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

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.AccountWarning;
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.model.Organization;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.model.ServerAction;
import synapticloop.scaleway.api.model.ServerTask;
import synapticloop.scaleway.api.model.ServerTaskStatus;
import synapticloop.scaleway.api.model.ServerType;
import synapticloop.scaleway.api.model.Volume;
import synapticloop.scaleway.api.response.ImagesResponse;
import synapticloop.scaleway.api.response.ServersResponse;

public class ScalewayApiClientTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScalewayApiClientTest.class);

	private static final String SCALEWAY_API_KEY = "SCALEWAY_API_KEY";

	private ScalewayApiClient scalewayApiClient;
	private String scalewayToken;

	private String ubuntuImageId = null;
	private String organizationId = null;

	private String getUbuntuImage() throws ScalewayApiException {
		if(null == ubuntuImageId) {
			for (int i = 1; i < Integer.MAX_VALUE; i++) {
				List<Image> images = scalewayApiClient.getAllImages(i, 100).getImages();
				for (Image image : images) {
					if("Ubuntu Xenial (16.04 latest)".equals(image.getName())) {
						this.ubuntuImageId = image.getId();
						return(this.ubuntuImageId);
					}
				}
			}
		}

		return(this.ubuntuImageId);
	}

	private String getOrganizationId() throws ScalewayApiException {
		if(null == organizationId) {
			this.organizationId = scalewayApiClient.getAllOrganizations().get(0).getId();
			return(this.organizationId);
		}

		return(organizationId);
	}

	@Before
	public void setup() {
		boolean isOK = true;
		scalewayToken = System.getenv(SCALEWAY_API_KEY);


		if(null == scalewayToken) {
			System.err.println("Could not find the environment variable '" + SCALEWAY_API_KEY + "', cannot continue with tests, exiting...");
			isOK = false;
		}

		if(!isOK) {
			System.exit(-1);
		}

		scalewayApiClient = new ScalewayApiClient(scalewayToken, Region.PARIS1);
	}

	@Test
	public void testGetImage() throws ScalewayApiException {
		ImagesResponse imagesResponse = scalewayApiClient.getAllImages(1, 50);
		assertNotNull(imagesResponse);

		Image image = imagesResponse.getImages().get(0);

		Image singleImage = scalewayApiClient.getImage(image.getId());
		assertEquals(image.getArch(), singleImage.getArch());
		assertEquals(image.getCreationDate(), singleImage.getCreationDate());
		assertEquals(image.getDefaultBootscript().getId(), singleImage.getDefaultBootscript().getId());
		assertEquals(image.getExtraVolumes(), singleImage.getExtraVolumes());
		assertEquals(image.getFromImage(), singleImage.getFromImage());
		assertEquals(image.getFromServer(), singleImage.getFromServer());
		assertEquals(image.getId(), singleImage.getId());
		assertEquals(image.getIsPublicImage(), singleImage.getIsPublicImage());
		assertEquals(image.getMarketplaceKey(), singleImage.getMarketplaceKey());
		assertEquals(image.getModificationDate(), singleImage.getModificationDate());
		assertEquals(image.getName(), singleImage.getName());
		assertEquals(image.getOrganization(), singleImage.getOrganization());
		assertEquals(image.getRootVolume().getId(), singleImage.getRootVolume().getId());
	}

	@Test
	public void testGetAllImages() throws ScalewayApiException {
		ImagesResponse imagesResponse = scalewayApiClient.getAllImages(1, 50);

		int numPages = imagesResponse.getNumPages();
		for(int i = 2; i <= numPages; i++) {
			ImagesResponse imagesResponseInner = scalewayApiClient.getAllImages(i, 50);
			assertEquals(i, imagesResponseInner.getCurrentPage());
			assertEquals(50, imagesResponseInner.getNumPerPage());
			assertEquals(numPages, imagesResponseInner.getNumPages());
			assertEquals(imagesResponse.getTotalCount(), imagesResponseInner.getTotalCount());
		}
	}

	@Test
	public void testGetAccountWarnings() throws ScalewayApiException {
		List<Organization> organizations = scalewayApiClient.getAllOrganizations();
		for (Organization organization : organizations) {
			// there should always be an account warning - as you need to sign TOS
			AccountWarning accountWarning = organization.getWarnings().get(0);
			assertNotNull(accountWarning.getClosedAt());
			assertNotNull(accountWarning.getId());
			assertNotNull(accountWarning.getIsClosableByUser());
			assertNotNull(accountWarning.getIsClosed());
			assertNotNull(accountWarning.getIsLocking());
			assertNotNull(accountWarning.getLockedAt());
			assertNotNull(accountWarning.getOpenedAt());
			assertNotNull(accountWarning.getReason());
			assertNotNull(accountWarning.toString());
		}
		assertNotNull(organizations);
	}


	@Test
	public void testCreateAndDeleteServer() throws ScalewayApiException {
		String organizationId = getOrganizationId();

		Server server = scalewayApiClient.createServer("scaleway-java-api-test-server", getUbuntuImage(), organizationId, ServerType.VC1S, new String[] {"scaleway", "java", "api", "server"});

		Server returnedServer = scalewayApiClient.getServer(server.getId());
		assertEquals(server.getArch(), returnedServer.getArch());
		assertEquals(server.getCreationDate(), returnedServer.getCreationDate());
		assertEquals(server.getHostname(), returnedServer.getHostname());
		assertEquals(server.getId(), returnedServer.getId());
		assertEquals(server.getIpv6(), returnedServer.getIpv6());
		assertEquals(server.getModificationDate(), returnedServer.getModificationDate());
		assertEquals(server.getName(), returnedServer.getName());
		assertEquals(server.getOrganization(), returnedServer.getOrganization());
		assertEquals(server.getPrivateIp(), returnedServer.getPrivateIp());
		assertEquals(server.getPublicIP(), returnedServer.getPublicIP());
		assertEquals(server.getStateDetail(), returnedServer.getStateDetail());

		scalewayApiClient.deleteServer(server.getId());

		Map<String, Volume> volumes = server.getVolumes();
		Iterator<String> iterator = volumes.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Volume volume = volumes.get(key);
			scalewayApiClient.deleteVolume(volume.getId());
		}

	}
/*
	@Test
	public void testCreateAndUpdateServer() throws ScalewayApiException {
		String organizationId = getOrganizationId();

		Server server = scalewayApiClient.createServer("scaleway-java-api-test-server", getUbuntuImage(), organizationId, ServerType.VC1S, new String[] {"scaleway", "java", "api", "server"});

		server.setName("scaleway-java-api-test-server-updated");

		Server updatedServer = scalewayApiClient.updateServer(server);

		assertEquals(server.getId(), updatedServer.getId());
		assertEquals("scaleway-java-api-test-server-updated", updatedServer.getName());

		scalewayApiClient.executeServerAction(server.getId(), ServerAction.TERMINATE);
	}
*/
	@Test
	public void testGetAllServers() throws ScalewayApiException {
		ServersResponse serversResponse = scalewayApiClient.getAllServers(1, 2);

		int numPages = serversResponse.getNumPages();
		for(int i = 2; i <= numPages; i++) {
			ServersResponse serversResponseInner = scalewayApiClient.getAllServers(i, 2);
			assertEquals(i, serversResponseInner.getCurrentPage());
			assertEquals(2, serversResponseInner.getNumPerPage());
			assertEquals(numPages, serversResponseInner.getNumPages());
			assertEquals(serversResponse.getTotalCount(), serversResponseInner.getTotalCount());
		}
	}

	@Test
	public void testGetServerActions() throws ScalewayApiException {
		String organizationId = getOrganizationId();
		Server server = scalewayApiClient.createServer("scaleway-java-api-test-server", getUbuntuImage(), organizationId, ServerType.VC1S, new String[] {"scaleway", "java", "api", "server"});

		List<ServerAction> serverActions = scalewayApiClient.getServerActions(server.getId());
		assertNotNull(serverActions);
		assertTrue(serverActions.size() >= 1);

		scalewayApiClient.deleteServer(server.getId());

		Map<String, Volume> volumes = server.getVolumes();
		Iterator<String> iterator = volumes.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Volume volume = volumes.get(key);
			scalewayApiClient.deleteVolume(volume.getId());
		}
	}

	@Test
	public void testPowerCycleServer() throws ScalewayApiException {
		String organizationId = scalewayApiClient.getAllOrganizations().get(0).getId();
		Server server = scalewayApiClient.createServer("scaleway-java-api-test-server", getUbuntuImage(), organizationId, ServerType.VC1S, new String[] {"scaleway", "java", "api", "server"});

		ServerTask powerOnServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWERON);

		boolean isStarted = false;
		while(!isStarted) {
			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOnServerTask.getId());
			LOGGER.debug("Server task with id '{}' is in current state '{}' (progress '{}')", taskStatus.getId(), taskStatus.getStatus(), taskStatus.getProgress());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				LOGGER.warn("The sleeping thread was interrupted, continuing...");
			}
			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
				isStarted = true;
			}
		}

		ServerTask powerOffServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWEROFF);
		boolean isEnded = false;
		while(!isEnded) {
			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOffServerTask.getId());
			LOGGER.debug("Server task with id '{}' is in current state '{}' (progress '{}')", taskStatus.getId(), taskStatus.getStatus(), taskStatus.getProgress());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException ex) {
				LOGGER.warn("The sleeping thread was interrupted, continuing...");
			}

			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
				isEnded = true;
			}
		}

		scalewayApiClient.deleteServer(server.getId());

		Map<String, Volume> volumes = server.getVolumes();
		Iterator<String> iterator = volumes.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Volume volume = volumes.get(key);
			scalewayApiClient.deleteVolume(volume.getId());
		}
	}
}
