package zhitu.sq.dataset.controller.vo;


public class Select {
	
	private String id;
	private String code;
	private String name;
	private String other;
	
	
	public Select() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Select(String id, String code, String name, String other) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.other = other;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
	

}
