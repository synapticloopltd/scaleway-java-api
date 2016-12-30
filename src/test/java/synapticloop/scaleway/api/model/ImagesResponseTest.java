package synapticloop.scaleway.api.model;

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

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Before;
import org.junit.Test;

import synapticloop.scaleway.api.response.ImagesResponse;

public class ImagesResponseTest {

	private ImagesResponse imagesResponse;

	@Before
	public void setup() {
		imagesResponse = new ImagesResponse();
	}

	@Test
	public void testPaginationHeaders() {
		Header[] headers = {
				new BasicHeader("X-Total-Count", "223"),
				new BasicHeader("Link", "</images?page=2&per_page=50>; rel=\"next\",</images?page=5&per_page=50>; rel=\"last\"")
		};

		imagesResponse.parsePaginationHeaders(headers);

		assertEquals(223, imagesResponse.getTotalCount());
		assertEquals(50, imagesResponse.getNumPerPage());
		assertEquals(5, imagesResponse.getNumPages());
	}

	public ImagesResponseTest() {
		// TODO Auto-generated constructor stub
	}

}
