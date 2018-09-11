package zhitu.sq.dataset.controller.vo;

import java.util.List;
import java.util.Map;

public class TableMoney {
	
	private String name; //表名
	private String code; //表名码值
	
	private List<Map<String,Object>> ls;
	
	
	public TableMoney() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TableMoney(String name, String code, List<Map<String,Object>> ls) {
		super();
		this.name = name;
		this.code = code;
		this.ls = ls;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Map<String, Object>> getLs() {
		return ls;
	}

	public void setLs(List<Map<String, Object>> ls) {
		this.ls = ls;
	}
	
	
	

}
