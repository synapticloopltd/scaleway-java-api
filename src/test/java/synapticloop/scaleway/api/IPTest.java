package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.IP;
import synapticloop.scaleway.api.response.IPsResponse;

public class IPTest extends BaseTestUtils {

	@Test
	public void testCreateAndDeleteIP() throws ScalewayApiException {
		IP createIP = scalewayApiClient.createIP(getOrganizationId());
		assertNotNull(createIP);

		scalewayApiClient.deleteIP(createIP.getId());
	}

	@Test
	public void testGetAllIPAddresses() throws ScalewayApiException {
		boolean finished = false;
		int i = 1;
		while(!finished) {
			IPsResponse ipsResponse = scalewayApiClient.getAllIPs(i, 3);

			List<IP> IPs = ipsResponse.getIPs();
			for (IP ip : IPs) {
				assertNotNull(ip);
				IP ipInner = scalewayApiClient.getIP(ip.getId());
				assertNotNull(ipInner);
				assertEquals(ip.getId(), ipInner.getId());
				assertEquals(ip.getIpAddress(), ipInner.getIpAddress());
				assertEquals(ip.getIpAddressReverse(), ipInner.getIpAddressReverse());
				assertEquals(ip.getOrganizationId(), ipInner.getOrganizationId());
			}

			i++;
			if(i > ipsResponse.getNumPages()) {
				finished = true;
			}
		}
	}


}
