package zhitu.sq.dataset.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.DataSetMapper;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.utils.NumberDealHandler;
import zhitu.utils.StringHandler;

@Service
public class DateSetServiceImpl implements DataSetService{

	@Autowired
	private DataSetMapper dataSetMapper;
	
	@Override
	public PageInfo<Map<String, Object>> queryDateSet(Map<String, Object> map,String userId) {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		String name = StringHandler.objectToString(map.get("name"));
		List<Map<String, Object>> list = dataSetMapper.findByName(name,userId);
		return new PageInfo<>(list);
	}

	@Override
	public int saveLocalDataSet(String userId,String name, String describe, String projectId, MultipartFile[] files) {
		
		
		//假设文件上传成功，上传ftp地址为System.getProperty("user.dir")
		DataSet dataSet = new DataSet();
		String id = "DATASET_"+new Date().getTime();
		dataSet.setId(id);
		dataSet.setCreateTime(new Date());
		dataSet.setName(name);
		dataSet.setUserId(userId);
		dataSet.setProjectId(projectId);
		dataSet.setTypeId("local_file");	
		dataSet.setDataTable("zt_data_"+id);
		
		for (MultipartFile file : Arrays.asList(files)) {
			//文件后缀名(文件类型)
			String suffix = file.getOriginalFilename()
					.substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			//文件名称带扩展名
			String fileName2 = file.getOriginalFilename();
			//文件名不带扩展名
			String fileName = fileName2.substring(0, fileName2.lastIndexOf("."));
			//文件上传到ftp
			//................
			//假设文件上传成功，上传ftp地址为System.getProperty("user.dir")
			
		}
		//文件上传成功后保存数据集
		dataSetMapper.insert(dataSet);
		//创建zt_data_DATASET_1428399384表
		//字段 id（唯一ID，必须以PDF_毫秒时间戳为格式） createTime创建时间
		//fileName（pdf文件名带后缀） abstract （pdf内容摘要） 
		//ftpurl (pdf文件路径)
		return 0;
	}

	@Override
	public DataSet findById(String id) {
		return dataSetMapper.findById(id);
	}

	@Override
	public int updateDataSet(DataSet dSet) {
		return dataSetMapper.updateDataSet(dSet);
	}

}
