package bench.json;

import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

public class Bean {

	@JsonProperty("value")
	private String value;

	@JsonProperty("list")
	private List<String> entryList;

	@JsonProperty("set")
	private Set<String> entrySet;

	@JsonProperty("bean")
	private Bean bean;

	//

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getEntryList() {
		return entryList;
	}

	public void setEntryList(List<String> entryList) {
		this.entryList = entryList;
	}

	public Set<String> getEntrySet() {
		return entrySet;
	}

	public void setEntrySet(Set<String> entrySet) {
		this.entrySet = entrySet;
	}

	public Bean getBean() {
		return bean;
	}

	public void setBean(Bean bean) {
		this.bean = bean;
	}

}
