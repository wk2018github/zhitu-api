package zhitu.sq.dataset.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.model.FtpFile;

public interface FtpFileService {

	/**
	 * 
	 * @param ftpFile
	 * @return
	 */
	int updateFtpFile(FtpFile ftpFile);

	PageInfo<FtpFile> queryFtpFile(Map<String, Object> map);

}
