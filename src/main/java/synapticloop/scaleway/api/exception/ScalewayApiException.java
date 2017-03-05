package synapticloop.scaleway.api.exception;

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

public class ScalewayApiException extends Exception {
	private static final long serialVersionUID = -7535143860328025468L;

	public ScalewayApiException() {
		super();
	}

	public ScalewayApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ScalewayApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScalewayApiException(String message) {
		super(message);
	}

	public ScalewayApiException(Throwable cause) {
		super(cause);
	}
}

