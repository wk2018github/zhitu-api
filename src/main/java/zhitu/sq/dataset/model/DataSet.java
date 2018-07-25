package zhitu.sq.dataset.model;

import java.util.Date;

public class DataSet{

	/**
	 * 唯一ID，必须以`DATASET_毫秒时间戳`为格式
	 */
    private String id;
    
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 数据集名称
     */
    private String name;
    
    /**
     * 数据集描述
     */
    private String describe;

    /**
     * 类型ID，外键:引用zt_sys_dataset_local_file.id
     */
    private String typeId;
    
    /**
     * 用户ID，外键：引用zt_sys_user.id
     */
    private String userId;

    /**
     * 项目ID，外键：引用zt_sys_project.id
     */
    private String projectId;
    
    /**
     * 存放具体数据的数据表名，一般以`zt_data_数据集ID`命名，当typeId为remote_rdb时，该字段为null
     */
    private String dataTable;
    
    /**
     * 关系数据库描述信息ID，外键：引用zt_sys_dataset_rdb.id，对于文件类型（local_file），该字段为null
     */
    private String rdbId;

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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getDataTable() {
		return dataTable;
	}

	public void setDataTable(String dataTable) {
		this.dataTable = dataTable;
	}

	public String getRdbId() {
		return rdbId;
	}

	public void setRdbId(String rdbId) {
		this.rdbId = rdbId;
	}

	

}
