package zhitu.sq.dataset.model;

import java.io.Serializable;
import java.util.Date;

public class Graph implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一ID，必须以`GRAPH_毫秒时间戳`为格式
	 */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 图谱名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

	public Graph() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Graph(String id, Date createTime, String name, String description) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
