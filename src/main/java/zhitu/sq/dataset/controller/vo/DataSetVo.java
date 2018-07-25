package zhitu.sq.dataset.controller.vo;

import java.util.Date;

public class DataSetVo {

	/**
	 * 唯一ID，必须以`DATASET_毫秒时间戳`为格式
	 */
    private String id;
    
    /**
     * 创建时间(修改时间)
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


}
