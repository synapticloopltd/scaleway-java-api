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

import org.junit.Before;
import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;

public class TokenTest {

	private ScalewayApiClient scalewayApiClient;

	@Before
	public void setup() {
		scalewayApiClient = new ScalewayApiClient(null, Region.AMSTERDAM1);
	}

	@Test
	public void testCreateToken() throws ScalewayApiException {
		scalewayApiClient.createToken("a@b.com", "password", true);
		assertTrue(true);
	}
}
