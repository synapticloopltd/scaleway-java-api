package synapticloop.scaleway.api.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePaginationResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(BasePaginationResponse.class);

	private static final Pattern PATTERN_NEXT = Pattern.compile(".*page=(\\d*).*per_page=(\\d*).*rel=\\\"next\\\".*");
	private static final Pattern PATTERN_PREVIOUS = Pattern.compile(".*page=(\\d*).*per_page=(\\d*).*rel=\\\"previous\\\".*");
	private static final Pattern PATTERN_LAST = Pattern.compile(".*page=(\\d*).*per_page=(\\d*).*rel=\\\"last\\\".*");

	private static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
	private static final String HEADER_LINK = "Link";

	private int totalCount = 0;
	private int numPerPage = 0;
	private int currentPage = 0;

	public void setPaginationHeaders(Header[] headers) {
		for (Header header : headers) {
			String headerName = header.getName();
			switch(headerName) {
			case HEADER_X_TOTAL_COUNT:
				LOGGER.debug("Received '{}' header with value '{}'.", header.getName(), header.getValue());
				this.totalCount = Integer.parseInt(header.getValue());
				break;
			case HEADER_LINK:
				LOGGER.debug("Received '{}' header with value '{}'.", header.getName(), header.getValue());
				// should look something like this:
				// </images?page=2&per_page=50>; rel="next",</images?page=5&per_page=50>; rel="last"
				parsePages(header.getValue());
				break;
			default:
				break;
			}
		}
	}

	private void parsePages(String value) {
		// should look something like this:
		// </images?page=1&per_page=50>; rel="first",</images?page=4&per_page=50>; rel="previous",</images?page=5&per_page=50>; rel="last"

		Matcher previousMatcher = PATTERN_PREVIOUS.matcher(value);
		if(previousMatcher.matches() && previousMatcher.groupCount() == 2) {
			this.currentPage = Integer.parseInt(previousMatcher.group(1)) +1;
			this.numPerPage = Integer.parseInt(previousMatcher.group(2));
			return;
		}

		// should look something like this:
		// </images?page=2&per_page=50>; rel="next",</images?page=5&per_page=50>; rel="last"
		Matcher nextMatcher = PATTERN_NEXT.matcher(value);
		if(nextMatcher.matches() && nextMatcher.groupCount() == 2) {
			this.currentPage = Integer.parseInt(nextMatcher.group(1)) -1;
			this.numPerPage = Integer.parseInt(nextMatcher.group(2));
			return;
		}

		// at this point - there is only one page and we are on it
		// should look something like this:
		// </servers?page=1&per_page=2>; rel="last"
		Matcher lastMatcher = PATTERN_LAST.matcher(value);
		if(lastMatcher.matches() && lastMatcher.groupCount() == 2) {
			this.currentPage = Integer.parseInt(lastMatcher.group(1));
			this.numPerPage = Integer.parseInt(lastMatcher.group(2));
			return;
		}
}

	public int getTotalCount() { return this.totalCount; }

	public int getNumPerPage() { return this.numPerPage; }

	public int getNumPages() { return((int)Math.ceil(((float)totalCount)/((float)numPerPage))); }

	public int getCurrentPage() { return this.currentPage; }
}
