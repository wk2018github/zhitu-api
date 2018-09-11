package zhitu.sq.dataset.controller.vo;

import java.util.List;

public class LineChart {
	
	private String name;
	private List<String> money;
	
	
	public LineChart() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public LineChart(String name, List<String> money) {
		super();
		this.name = name;
		this.money = money;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<String> getMoney() {
		return money;
	}


	public void setMoney(List<String> money) {
		this.money = money;
	}
	

}
