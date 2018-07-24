package zhitu.sq.dataset.model;

import java.io.Serializable;
import java.util.Date;

public class Project implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 唯一ID，必须以`PROJECT_毫秒时间戳`为格式
	 */
    private String id;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 用户ID，外键：引用zt_sys_user.id
     */
    private String userId;
    
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Project(String id, Date createTime, String name, String userId) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.name = name;
		this.userId = userId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

    
    

}
