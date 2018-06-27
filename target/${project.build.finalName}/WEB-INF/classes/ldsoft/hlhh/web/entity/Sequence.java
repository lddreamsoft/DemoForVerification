package ldsoft.hlhh.web.entity;


//序列表，存储某一键对应的最大值 
public class Sequence {
	
	private String name;
	
	private long value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
