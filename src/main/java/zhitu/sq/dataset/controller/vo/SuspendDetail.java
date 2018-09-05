package zhitu.sq.dataset.controller.vo;


public class SuspendDetail {
	
	private String name;
	private String synopsis;
	
	
	public SuspendDetail() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SuspendDetail(String name, String synopsis) {
		super();
		this.name = name;
		this.synopsis = synopsis;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	

}
