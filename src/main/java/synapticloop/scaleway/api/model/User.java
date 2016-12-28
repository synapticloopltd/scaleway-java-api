package synapticloop.scaleway.api.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	@JsonProperty("id")                 private String id;
	@JsonProperty("email")              private String email;
	@JsonProperty("firstname")          private String firstname;
	@JsonProperty("fullname")           private String fullname;
	@JsonProperty("lastname")           private String lastname;
	@JsonProperty("phone_number")       private String phoneNumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("creation_date")      private Date creationDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
	@JsonProperty("modification_date")  private Date modificationDate;
	@JsonProperty("organizations")      private List<Organization> organizations;
	@JsonProperty("roles")              private List<Role> roles;
	@JsonProperty("ssh_public_keys")    private List<UserSshPublicKey> sshPublicKeys;

	public String getId() { return id; }

	public String getEmail() { return email; }

	public String getFirstname() { return firstname; }

	public String getFullname() { return fullname; }

	public String getLastname() { return lastname; }

	public List<Organization> getOrganizations() { return organizations; }

	public List<Role> getRoles() { return roles; }

	public List<UserSshPublicKey> getSshPublicKeys() { return sshPublicKeys; }

	public String getPhoneNumber() { return phoneNumber; }

	public Date getCreationDate() { return creationDate; }

	public Date getModificationDate() { return modificationDate; }

}
