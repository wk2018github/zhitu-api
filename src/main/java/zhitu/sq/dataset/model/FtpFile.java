package zhitu.sq.dataset.model;

import java.util.Date;

public class FtpFile {
	
    private String id; //唯一ID，必须以`PDF_毫秒时间戳`为格式

    private Date createTime;//创建时间

    private String fileName;//文件名

    private String ftpurl;//文件路径

    private String datasetId;//zt_sys_dataset表中新增一条记录的id

    private String fileAbstract;//内容摘要

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFtpurl() {
		return ftpurl;
	}

	public void setFtpurl(String ftpurl) {
		this.ftpurl = ftpurl;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public String getFileAbstract() {
		return fileAbstract;
	}

	public void setFileAbstract(String fileAbstract) {
		this.fileAbstract = fileAbstract;
	}

   
}