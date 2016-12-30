package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.IP;

public class IPTest extends BaseTestUtils {

	@Test
	public void testCreateAndDeleteIP() throws ScalewayApiException {
		IP createIP = scalewayApiClient.createIP(getOrganizationId());
		assertNotNull(createIP);

		scalewayApiClient.deleteIP(createIP.getId());
	}
}
