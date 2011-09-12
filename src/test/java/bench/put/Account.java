package bench.put;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.carrotgarden.util.json.JSON;

public class Account {

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("email_list")
	private List<String> emailList;

	//

	@Override
	public String toString() {
		return JSON.intoText(this);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<String> getEmailList() {
		return emailList;
	}

	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

}
