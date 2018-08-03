package zhitu.sq.dataset.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.FtpFileMapper;
import zhitu.sq.dataset.model.FtpFile;
import zhitu.sq.dataset.service.FtpFileService;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;

@Service
public class FtpFileServiceImpl implements FtpFileService{

	@Autowired
	private FtpFileMapper ftpFileMapper;

	@Override
	public int updateFtpFile(FtpFile ftpFile) {
		return ftpFileMapper.updateByPrimaryKeySelective(ftpFile);
	}

	@Override
	public PageInfo<Map<String, Object>> queryFtpFile(Map<String, Object> map) {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		String id = StringHandler.objectToString(map.get("id"));
		List<Map<String, Object>> list = ftpFileMapper.findByDataSetId(id);
		return new PageInfo<>(list);
	}

	
}
