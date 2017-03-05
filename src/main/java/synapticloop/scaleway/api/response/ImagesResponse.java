package synapticloop.scaleway.api.response;

/*
 * Copyright (c) 2016-2017 synapticloop.
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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import synapticloop.scaleway.api.model.Image;

/**
 * The images response object which encapsulates the paginated list of images
 *
 */
public class ImagesResponse extends BasePaginationResponse {
	@JsonProperty("images")  private List<Image> images;

	/**
	 * Return the list of paginated images
	 * 
	 * @return the list of paginated images
	 */
	public List<Image> getImages() { return images; }
}
