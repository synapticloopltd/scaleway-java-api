package synapticloop.scaleway.api;

/*
 * Copyright (c) 2016-2017 Synapticloop.
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

import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.AccountWarning;
import synapticloop.scaleway.api.model.Organization;

public class ScalewayApiClientTest extends BaseTestUtils {

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
