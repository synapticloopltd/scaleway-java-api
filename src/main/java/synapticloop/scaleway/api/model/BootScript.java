package synapticloop.scaleway.api.model;

/*
 * Copyright (c) 2016 synapticloop.
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

	public void setId(String id) { this.id = id; }

	public String getOrganization() { return organization; }

	public void setOrganization(String organization) { this.organization = organization; }

	public String getTitle() { return title; }

	public void setTitle(String title) { this.title = title; }

	public boolean getIsDefault() { return isDefault; }

	public void setIsDefault(boolean isDefault) { this.isDefault = isDefault; }

	public boolean getIsPublic() { return isPublic; }

	public void setIsPublic(boolean isPublic) { this.isPublic = isPublic; }

	public String getBootcmdargs() { return bootcmdargs; }

	public void setBootcmdargs(String bootcmdargs) { this.bootcmdargs = bootcmdargs; }

	public String getInitrd() { return initrd; }

	public void setInitrd(String initrd) { this.initrd = initrd; }

	public String getArchitecture() { return architecture; }

	public void setArchitecture(String architecture) { this.architecture = architecture; }

	public String getKernel() { return kernel; }

	public void setKernel(String kernel) { this.kernel = kernel; }

	public String getDtb() { return dtb; }

	public void setDtb(String dtb) { this.dtb = dtb; }

}
