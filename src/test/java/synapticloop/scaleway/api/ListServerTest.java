package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Server;
import synapticloop.scaleway.api.model.ServerAction;
import synapticloop.scaleway.api.model.ServerTask;
import synapticloop.scaleway.api.model.ServerTaskStatus;
import synapticloop.scaleway.api.model.ServerType;
import synapticloop.scaleway.api.model.Volume;
import synapticloop.scaleway.api.response.ServersResponse;

public class ListServerTest extends BaseTestUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(ListServerTest.class);

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
}
