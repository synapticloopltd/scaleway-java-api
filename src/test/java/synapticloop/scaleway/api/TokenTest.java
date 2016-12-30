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
import synapticloop.scaleway.api.model.Token;
import synapticloop.scaleway.api.response.TokensResponse;

public class TokenTest extends BaseTestUtils {

	@Before
	public void setup() {
		scalewayApiClient = new ScalewayApiClient(System.getenv(SCALEWAY_API_KEY), Region.AMSTERDAM1);
	}

	@Test
	public void testCreateToken() throws ScalewayApiException {
		Token createToken = scalewayApiClient.createToken(System.getenv("SCALEWAY_EMAIL_ADDRESS"), System.getenv("SCALEWAY_PASSWORD"), true);
		assertNotNull(createToken);

		scalewayApiClient.deleteToken(createToken.getId());
	}

	@Test
	public void testUpdateToken() throws ScalewayApiException {
		Token createToken = scalewayApiClient.createToken(System.getenv("SCALEWAY_EMAIL_ADDRESS"), System.getenv("SCALEWAY_PASSWORD"), true);
		assertNotNull(createToken);

		String tokenId = createToken.getId();

		Token updateToken = scalewayApiClient.updateToken(createToken.getId());
		// to be fair - this seems to randomly update the date and time...
		assertTrue(updateToken.getExpiresDate().getTime() != createToken.getExpiresDate().getTime());
		scalewayApiClient.deleteToken(tokenId);
	}

	@Test
	public void testGetAllTokens() throws ScalewayApiException {
		int i = 1;
		boolean finished = false;
		while(!finished) {
			TokensResponse allTokens = scalewayApiClient.getAllTokens(i, 10);
			List<Token> tokens = allTokens.getTokens();
			for (Token token : tokens) {
				assertNotNull(token);
			}

			if(i > allTokens.getNumPages()) {
				finished = true;
			}

			i++;
		}
	}
}
