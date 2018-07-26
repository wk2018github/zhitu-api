package zhitu.sq.dataset.controller.vo;

import java.util.Date;

public class DataSetVo {

	/**
	 * 唯一ID，必须以`DATASET_毫秒时间戳`为格式
	 */
    private String id;
    
    /**
     * 数据集名称
     */
    private String name;
    
    /**
     * 数据集描述
     */
    private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
