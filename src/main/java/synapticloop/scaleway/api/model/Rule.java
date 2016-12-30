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

import synapticloop.scaleway.api.RuleAction;
import synapticloop.scaleway.api.RuleDirection;
import synapticloop.scaleway.api.RuleProtocol;

public class Rule {

	@JsonProperty("id")              private String id;
	@JsonProperty("direction")       private RuleDirection direction;
	@JsonProperty("protocol")        private RuleProtocol protocol;
	@JsonProperty("ip_range")         private String ipRange;
	@JsonProperty("dest_port_from")  private Integer destPortFrom;
	@JsonProperty("action")          private RuleAction action;
	@JsonProperty("position")        private int position;
	@JsonProperty("dest_port_to")    private Integer destPortTo;
	@JsonProperty("editable")        private Boolean isEditable;

	public RuleDirection getDirection() { return this.direction; }

	public RuleProtocol getProtocol() { return this.protocol; }

	public String getIpRange() { return this.ipRange; }

	public Integer getDestPortFrom() { return this.destPortFrom; }

	public RuleAction getAction() { return this.action; }

	public int getPosition() { return this.position; }

	public Integer getDestPortTo() { return this.destPortTo; }

	public Boolean getIsEditable() { return this.isEditable; }

	public String getId() { return this.id; }

}
