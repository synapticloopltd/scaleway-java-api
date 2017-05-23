package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Volume;
import synapticloop.scaleway.api.model.VolumeType;
import synapticloop.scaleway.api.response.VolumesResponse;

public class VolumeTest extends BaseTestUtils {

	@Test
	public void testCreateAndDeleteVolume() throws ScalewayApiException {
		Volume volume = scalewayApiClient.createVolume("scaleway-java-api-volume", getOrganizationId(), 1000000000, VolumeType.l_ssd);
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
