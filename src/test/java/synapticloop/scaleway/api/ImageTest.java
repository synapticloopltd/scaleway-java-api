package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.response.ImagesResponse;

public class ImageTest extends BaseTestUtils {

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
}
