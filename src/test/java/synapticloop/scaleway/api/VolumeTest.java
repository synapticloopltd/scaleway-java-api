package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Volume;
import synapticloop.scaleway.api.model.VolumeType;
import synapticloop.scaleway.api.model.VolumesResponse;

public class VolumeTest {
	private static final String SCALEWAY_API_KEY = "SCALEWAY_API_KEY";

	private ScalewayApiClient scalewayApiClient;

	private String organizationId;

	private String getOrganizationId() throws ScalewayApiException {
		if(null == organizationId) {
			this.organizationId = scalewayApiClient.getAllOrganizations().get(0).getId();
			return(this.organizationId);
		}

		return(organizationId);
	}

	@Before
	public void setup() {
		scalewayApiClient = new ScalewayApiClient(System.getenv(SCALEWAY_API_KEY), Region.PARIS1);
	}

	@Test
	public void testCreateAndDeleteVolume() throws ScalewayApiException {
		Volume volume = scalewayApiClient.createVolume("scaleway-java-api-volume", getOrganizationId(), 1000000000, VolumeType.L_SSD);
		Volume volumeInfo = scalewayApiClient.getVolume(volume.getId());

		assertEquals(volume.getId(), volumeInfo.getId());

		scalewayApiClient.deleteVolume(volume.getId());
	}
	
	@Test
	public void testGetAllVolumes() throws ScalewayApiException {
		VolumesResponse volumesResponse = scalewayApiClient.getAllVolumes(1, 2);

		int numPages = volumesResponse.getNumPages();
		for(int i = 2; i <= numPages; i++) {
			VolumesResponse volumesResponseInner = scalewayApiClient.getAllVolumes(i, 2);
			assertEquals(i, volumesResponseInner.getCurrentPage());
			assertEquals(2, volumesResponseInner.getNumPerPage());
			assertEquals(numPages, volumesResponseInner.getNumPages());
			assertEquals(volumesResponse.getTotalCount(), volumesResponseInner.getTotalCount());
		}
		
	}

}
