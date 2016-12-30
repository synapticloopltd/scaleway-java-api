package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.Rule;
import synapticloop.scaleway.api.model.SecurityGroup;
import synapticloop.scaleway.api.response.RulesResponse;
import synapticloop.scaleway.api.response.SecurityGroupsResponse;

public class RuleTest extends BaseTestUtils {

	@Test
	public void testCreateUpdateAndDeleteRule() throws ScalewayApiException {
		SecurityGroup securityGroup = scalewayApiClient.createSecurityGroup(getOrganizationId(), "scaleway-java-api-rule-test", "Please delete me if found.");

		Rule rule = scalewayApiClient.createRule(securityGroup.getId(),
				RuleAction.ACCEPT, 
				RuleDirection.INBOUND, 
				"0.0.0.0/0", 
				RuleProtocol.TCP, 
				1000);
		assertNotNull(rule);

		Rule ruleUpdate = scalewayApiClient.updateRule(securityGroup.getId(),
				rule.getId(), 
				RuleAction.ACCEPT, 
				RuleDirection.INBOUND, 
				"0.0.0.0/0", 
				RuleProtocol.TCP, 
				1010);

		Rule ruleGet = scalewayApiClient.getRule(securityGroup.getId(), ruleUpdate.getId());

		assertEquals(new Integer(1010), ruleGet.getDestPortFrom());

		scalewayApiClient.deleteRule(securityGroup.getId(), rule.getId());

		scalewayApiClient.deleteSecurityGroup(securityGroup.getId());
	}

	@Test
	public void testGetAllRules() throws ScalewayApiException {
		SecurityGroupsResponse securityGroupsResponse = scalewayApiClient.getAllSecurityGroups(1, 1);
		List<SecurityGroup> securityGroups = securityGroupsResponse.getSecurityGroups();

		for (SecurityGroup securityGroup : securityGroups) {
			boolean finished = false;
			int i = 1;
			while(!finished) {
				RulesResponse rulesResponse = scalewayApiClient.getAllRules(securityGroup.getId(), 1, 3);

				List<Rule> rules = rulesResponse.getRules();
				for (Rule rule : rules) {
					assertNotNull(rule);
				}

				i++;
				if(i > rulesResponse.getNumPages()) {
					finished = true;
				}
			}
		}
	}
}
