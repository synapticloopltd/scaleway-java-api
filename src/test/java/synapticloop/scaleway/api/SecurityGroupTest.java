package synapticloop.scaleway.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import synapticloop.scaleway.api.exception.ScalewayApiException;
import synapticloop.scaleway.api.model.SecurityGroup;
import synapticloop.scaleway.api.response.SecurityGroupsResponse;

public class SecurityGroupTest extends BaseTestUtils {

	@Test
	public void testCreateUpdateAndDeleteSecurityGroup() throws ScalewayApiException {
		SecurityGroup securityGroup = scalewayApiClient.createSecurityGroup(getOrganizationId(), "scaleway-java-api-security-group", "this is a test security group and can be deleted.");
		assertNotNull(securityGroup);

		SecurityGroup securityGroupUpdate = scalewayApiClient.updateSecurityGroup(securityGroup.getId(), organizationId, "updated name", "this is an updated test security group and can be deleted.");

		SecurityGroup securityGroupGet = scalewayApiClient.getSecurityGroup(securityGroupUpdate.getId());

		assertEquals("updated name", securityGroupGet.getName());
		assertEquals("this is an updated test security group and can be deleted.", securityGroupGet.getDescription());

		scalewayApiClient.deleteSecurityGroup(securityGroup.getId());
	}

	@Test
	public void testGetAllSecurityGroups() throws ScalewayApiException {

		boolean finished = false;
		int i = 1;
		while(!finished) {
			SecurityGroupsResponse securityGroupsResponse = scalewayApiClient.getAllSecurityGroups(1, 3);

			List<SecurityGroup> securityGroups = securityGroupsResponse.getSecurityGroups();
			for (SecurityGroup securityGroup : securityGroups) {
				assertNotNull(securityGroup);
				SecurityGroup securityGroupInner = scalewayApiClient.getSecurityGroup(securityGroup.getId());
				assertNotNull(securityGroupInner);

			}

			i++;
			if(i > securityGroupsResponse.getNumPages()) {
				finished = true;
			}
		}
	}
}
