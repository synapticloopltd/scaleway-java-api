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
import synapticloop.scaleway.api.model.AccountWarnings;
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

		scalewayApiClient = new ScalewayApiClient(scalewayToken, null, Region.PARIS1);
	}

	@Test
	public void testGetAllImages() throws ScalewayApiException {
		List<Image> images = scalewayApiClient.getAllImages();
		assertNotNull(images);
	}

	@Test
	public void testGetAccountWarnings() throws ScalewayApiException {
		List<Organization> organizations = scalewayApiClient.getAllOrganizations();
		for (Organization organization : organizations) {
			List<AccountWarnings> warnings = organization.getWarnings();
			for (AccountWarnings accountWarnings : warnings) {
				System.out.println(accountWarnings);
			}
		}
		assertNotNull(organizations);
	}

}
