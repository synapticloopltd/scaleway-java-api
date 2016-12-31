package synapticloop.scaleway.api.request;

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
