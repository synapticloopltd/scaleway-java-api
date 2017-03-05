package synapticloop.scaleway.api.model;

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

import com.fasterxml.jackson.annotation.JsonProperty;

public class BootScript {
	@JsonProperty("id")            private String id;
	@JsonProperty("organization")  private String organization;
	@JsonProperty("title")         private String title;
	@JsonProperty("bootcmdargs")   private String bootcmdargs;
	@JsonProperty("initrd")        private String initrd;
	@JsonProperty("architecture")  private String architecture;
	@JsonProperty("kernel")        private String kernel;
	@JsonProperty("dtb")           private String dtb;
	@JsonProperty("default")       private boolean isDefault;
	@JsonProperty("public")        private boolean isPublic;

	public String getId() { return id; }

	public String getOrganization() { return organization; }

	public String getTitle() { return title; }

	public boolean getIsDefault() { return isDefault; }

	public boolean getIsPublic() { return isPublic; }

	public String getBootcmdargs() { return bootcmdargs; }

	public String getInitrd() { return initrd; }

	public String getArchitecture() { return architecture; }

	public String getKernel() { return kernel; }

	public String getDtb() { return dtb; }
}
