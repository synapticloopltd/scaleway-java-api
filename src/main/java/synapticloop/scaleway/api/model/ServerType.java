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

public enum ServerType {

    // List can be found at URL: https://availability.scaleway.com/availability.json

    @JsonProperty("ARM64-2GB") ARM64_2GB,
    @JsonProperty("ARM64-4GB") ARM64_4GB,
    @JsonProperty("ARM64-8GB") ARM64_8GB,
    @JsonProperty("C1") C1,
    @JsonProperty("C2L") C2L,
    @JsonProperty("C2M") C2M,
    @JsonProperty("C2S") C2S,
    @JsonProperty("VC1L") VC1L,
    @JsonProperty("VC1M") VC1M,
    @JsonProperty("VC1S") VC1S,
    @JsonProperty("X64-15GB") X64_15GB,
    @JsonProperty("X64-30GB") X64_30GB,
    @JsonProperty("X64-60GB") X64_60GB,
    @JsonProperty("X64-120GB") X64_120GB;
}
