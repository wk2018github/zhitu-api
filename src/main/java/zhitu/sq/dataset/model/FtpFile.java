package zhitu.sq.dataset.model;

import java.util.Date;

public class FtpFile {
    private String id;

    private Date createTime;

    private String fileName;

    private String ftpurl;

    private String datasetId;

    private String fileAbstract;

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