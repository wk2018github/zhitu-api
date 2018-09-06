package zhitu.sq.dataset.model;

import java.io.Serializable;
import java.util.Date;

public class ProcessGraph implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一ID，必须以`PRO_毫秒时间戳`为格式
	 */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 流程图名称
     */
    private String name;
    
    /**
     * 流程图数据年度
     */
    private String year;
    
    /**
     * 流程图json
     */
    private String json;

    /**
     * 图谱Id
     */
    private String graphId;

	public ProcessGraph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProcessGraph(String id, Date createTime, String name, String year, String json, String graphId) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.name = name;
		this.year = year;
		this.json = json;
		this.graphId = graphId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getGraphId() {
		return graphId;
	}

	public void setGraphId(String graphId) {
		this.graphId = graphId;
	}

	
}
