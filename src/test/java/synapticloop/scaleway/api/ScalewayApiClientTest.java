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

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.AccountWarning;
import synapticloop.scaleway.api.model.Image;
import synapticloop.scaleway.api.model.Organization;

public class ScalewayApiClientTest {
	private static final String SCALEWAY_API_KEY = "SCALEWAY_API_KEY";

	private ScalewayApiClient scalewayApiClient;
	private String scalewayToken;

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
	public void testGetAllImages() throws ScalewayApiException {
		List<Image> images = scalewayApiClient.getAllImages();
		assertNotNull(images);
	}

	@Test
	public void testGetImages() throws ScalewayApiException {
		List<Image> images = scalewayApiClient.getAllImages();
		assertNotNull(images);
		Image image = images.get(0);

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

}
