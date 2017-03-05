package synapticloop.scaleway.api.request;

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

import synapticloop.scaleway.api.model.RuleAction;
import synapticloop.scaleway.api.model.RuleDirection;
import synapticloop.scaleway.api.model.RuleProtocol;

/**
 * A rule request is used to encapsulate the JSON object for attaching a rule to
 * a security group
 */

public class RuleRequest {
	@JsonProperty("action")          private RuleAction ruleAction;
	@JsonProperty("direction")       private RuleDirection ruleDirection;
	@JsonProperty("protocol")        private RuleProtocol ruleProtocol;
	@JsonProperty("ip_range")        private String ipRange;
	@JsonProperty("dest_port_from")  private String destPortFrom;

	public RuleRequest(RuleAction ruleAction, RuleDirection ruleDirection, String ipRange, RuleProtocol ruleProtocol, int destPortFrom) {
		this.ruleAction = ruleAction;
		this.ruleDirection = ruleDirection;
		this.ipRange = ipRange;
		this.ruleProtocol = ruleProtocol;
		this.destPortFrom = new Integer(destPortFrom).toString();
	}
}
