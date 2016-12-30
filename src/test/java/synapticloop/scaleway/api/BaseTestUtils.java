package synapticloop.scaleway.api;

import java.util.List;

import org.junit.Before;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Image;

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

public class BaseTestUtils {
	protected static final String SCALEWAY_API_KEY = "SCALEWAY_API_KEY";

	protected ScalewayApiClient scalewayApiClient;

	protected String organizationId;
	protected String ubuntuImageId = null;

	@Before
	public void setup() {
		scalewayApiClient = new ScalewayApiClient(System.getenv(SCALEWAY_API_KEY), Region.PARIS1);
	}


	protected String getOrganizationId() throws ScalewayApiException {
		if(null == organizationId) {
			this.organizationId = scalewayApiClient.getAllOrganizations().get(0).getId();
			return(this.organizationId);
		}

		return(organizationId);
	}

	protected String getUbuntuImage() throws ScalewayApiException {
		if(null == ubuntuImageId) {
			for (int i = 1; i < Integer.MAX_VALUE; i++) {
				List<Image> images = scalewayApiClient.getAllImages(i, 100).getImages();
				for (Image image : images) {
					if("Ubuntu Xenial (16.04 latest)".equals(image.getName())) {
						this.ubuntuImageId = image.getId();
						return(this.ubuntuImageId);
					}
				}
			}
		}

		return(this.ubuntuImageId);
	}

}
