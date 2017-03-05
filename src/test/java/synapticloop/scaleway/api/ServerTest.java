package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.response.ServersResponse;

public class ServerTest extends BaseTestUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerTest.class);

//	@Test
//	public void testCreateAndDeleteServer() throws ScalewayApiException {
//		String organizationId = getOrganizationId();
//
//		Server server = scalewayApiClient.createServer("scaleway-java-api-test-server", getUbuntuImage(), organizationId, ServerType.VC1S, new String[] {"scaleway", "java", "api", "server"});
//
//		Server returnedServer = scalewayApiClient.getServer(server.getId());
//		assertEquals(server.getArch(), returnedServer.getArch());
//		assertEquals(server.getCreationDate(), returnedServer.getCreationDate());
//		assertEquals(server.getHostname(), returnedServer.getHostname());
//		assertEquals(server.getId(), returnedServer.getId());
//		assertEquals(server.getIPv6(), returnedServer.getIPv6());
//		assertEquals(server.getModificationDate(), returnedServer.getModificationDate());
//		assertEquals(server.getName(), returnedServer.getName());
//		assertEquals(server.getOrganizationId(), returnedServer.getOrganizationId());
//		assertEquals(server.getPrivateIp(), returnedServer.getPrivateIp());
//		assertEquals(server.getPublicIP(), returnedServer.getPublicIP());
//		assertEquals(server.getStateDetail(), returnedServer.getStateDetail());
//
//		scalewayApiClient.deleteServer(server.getId());
//
//		Map<String, Volume> volumes = server.getVolumes();
//		Iterator<String> iterator = volumes.keySet().iterator();
//		while (iterator.hasNext()) {
//			String key = (String) iterator.next();
//			Volume volume = volumes.get(key);
//			scalewayApiClient.deleteVolume(volume.getId());
//		}
//
//	}

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
			List<Server> servers = serversResponseInner.getServers();
			for (Server server : servers) {
				System.out.println(server.getHostname());
			}
		}
	}

//	@Test
//	public void testGetServerActions() throws ScalewayApiException {
//		String organizationId = getOrganizationId();
//		Server server = scalewayApiClient.createServer("scaleway-java-api-test-server", getUbuntuImage(), organizationId, ServerType.VC1S, new String[] {"scaleway", "java", "api", "server"});
//
//		List<ServerAction> serverActions = scalewayApiClient.getServerActions(server.getId());
//		assertNotNull(serverActions);
//		assertTrue(serverActions.size() >= 1);
//
//		scalewayApiClient.deleteServer(server.getId());
//
//		Map<String, Volume> volumes = server.getVolumes();
//		Iterator<String> iterator = volumes.keySet().iterator();
//		while (iterator.hasNext()) {
//			String key = (String) iterator.next();
//			Volume volume = volumes.get(key);
//			scalewayApiClient.deleteVolume(volume.getId());
//		}
//	}

//	@Test
//	public void testPowerCycleServer() throws ScalewayApiException {
//		String organizationId = scalewayApiClient.getAllOrganizations().get(0).getId();
//		Server server = scalewayApiClient.createServer("scaleway-java-api-test-server", 
//				getUbuntuImage(), 
//				organizationId, 
//				ServerType.VC1S, 
//				new String[] {"scaleway", "java", "api", "server"});
//
//		ServerTask powerOnServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWERON);
//
//		boolean isStarted = false;
//		while(!isStarted) {
//			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOnServerTask.getId());
//			LOGGER.debug("Server task with id '{}' is in current state '{}' (progress '{}')", 
//					taskStatus.getId(), 
//					taskStatus.getStatus(), 
//					taskStatus.getProgress());
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException ex) {
//				LOGGER.warn("The sleeping thread was interrupted, continuing...");
//			}
//			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
//				isStarted = true;
//			}
//		}
//
//		ServerTask powerOffServerTask = scalewayApiClient.executeServerAction(server.getId(), ServerAction.POWEROFF);
//		boolean isEnded = false;
//		while(!isEnded) {
//			ServerTask taskStatus = scalewayApiClient.getTaskStatus(powerOffServerTask.getId());
//			LOGGER.debug("Server task with id '{}' is in current state '{}' (progress '{}')", taskStatus.getId(), taskStatus.getStatus(), taskStatus.getProgress());
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException ex) {
//				LOGGER.warn("The sleeping thread was interrupted, continuing...");
//			}
//
//			if(taskStatus.getStatus() == ServerTaskStatus.SUCCESS) {
//				isEnded = true;
//			}
//		}
//
//		scalewayApiClient.deleteServer(server.getId());
//
//		Map<String, Volume> volumes = server.getVolumes();
//		Iterator<String> iterator = volumes.keySet().iterator();
//		while (iterator.hasNext()) {
//			String key = (String) iterator.next();
//			Volume volume = volumes.get(key);
//			scalewayApiClient.deleteVolume(volume.getId());
//		}
//	}
}
