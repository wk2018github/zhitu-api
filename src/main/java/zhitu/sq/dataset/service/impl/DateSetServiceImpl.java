package zhitu.sq.dataset.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.DataSetRdbVo;
import zhitu.sq.dataset.mapper.DataSetMapper;
import zhitu.sq.dataset.mapper.DataTableMapper;
import zhitu.sq.dataset.mapper.FtpFileMapper;
import zhitu.sq.dataset.mapper.RdbMapper;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.Rdb;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;
import zhitu.util.JdbcDbUtils;

@Service
public class DateSetServiceImpl implements DataSetService{

	@Autowired
	private DataSetMapper dataSetMapper;
	@Autowired
	private FtpFileMapper ftpFileMapper;
	@Autowired
	private DataTableMapper dataTableMapper;
	@Autowired
	private RdbMapper rdbMapper;
	
	@Override
	public PageInfo<Map<String, Object>> queryDateSet(Map<String, Object> map,String userId) {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		String name = StringHandler.objectToString(map.get("name"));
		String projectId = StringHandler.objectToString(map.get("projectId"));
		List<Map<String, Object>> list = dataSetMapper.findByName(name,userId,projectId);
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
		//文件上传成功后保存数据集
		dataSetMapper.insert(dataSet);
		//文件保存数据
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
		
		//创建zt_data_DATASET_1428399384表
		
		//字段 id（唯一ID，必须以PDF_毫秒时间戳为格式） createTime创建时间
		//fileName（pdf文件名带后缀） abstract （pdf内容摘要） 
		//ftpurl (pdf文件路径)
		return 0;
	}

	@Override
	public int updateDataSet(DataSet dSet) {
		return dataSetMapper.updateDataSet(dSet);
	}

	@Override
	public PageInfo<Map<String, Object>> findById(Map<String, Object> map) throws Exception{
		
		String id = StringHandler.objectToString(map.get("id"));
    	String typeId = StringHandler.objectToString(map.get("typeId"));
    	String dataTable = StringHandler.objectToString(map.get("dataTable"));
    	String rdbId = StringHandler.objectToString(map.get("rdbId"));
    	int page = NumberDealHandler.objectToInt(map.get("page"));
    	int rows = NumberDealHandler.objectToInt(map.get("rows"));
    	PageHelper.startPage(page,rows);
    	List<Map<String, Object>> list = new ArrayList<>();
		if(typeId.equals("local_file_pdf")){
			list = ftpFileMapper.findByDataSetId(id);
		}else if(typeId.equals("local_rdb")){
			//根据dataTable及数据库表查询改表下所有数据
			list = dataTableMapper.findByDataSetId(dataTable);
		}else{
			//先查询远程连接信息及表名
			Rdb rdb = rdbMapper.selectByPrimaryKey(rdbId);
			list = JdbcDbUtils.jdbcTable(rdb,page,rows);
		}
		return new PageInfo<>(list);
	}

	@Override
	public int deleteById(String id, String typeId,String dataTable,String rdbId) {
		
		if(typeId.equals("local_file_pdf")){
			ftpFileMapper.deleteByDataSetId(id);
		}else if(typeId.equals("local_rdb")){
			//根据dataTable及数据库表删除改表
			dataTableMapper.deleteByDataSetId(dataTable);
			//根据rdbId删除信息
			rdbMapper.deleteByPrimaryKey(rdbId);
		}else{
			//根据rdbId删除信息
			rdbMapper.deleteByPrimaryKey(rdbId);
		}
		int i = dataSetMapper.deleteByPrimaryKey(id);
		
		return i;
	}

	@Override
	public int saveSqlDataSet(DataSetRdbVo dRdbVo, String userId) {
//		String typeId = StringHandler.objectToString(dRdbVo.getTypeId());
		
		DataSet dataSet = new DataSet();
		
//		if(typeId.equals("remote_rdb")){
			
			/**
			 * 首先保存数据库连接rdb数据，在保存dataset信息
			 */
			Rdb rdb = new Rdb();
			String rdbId = "RDB_"+new Date().getTime();
			rdb.setId(rdbId);
			rdb.setCharset(dRdbVo.getCharset());
			rdb.setColumnNames(dRdbVo.getColumnNames());
			rdb.setCreateTime(new Date());
			rdb.setDbName(dRdbVo.getDbName());
			rdb.setHost(dRdbVo.getHost());
			rdb.setPassword(dRdbVo.getPassword());
			rdb.setPort(dRdbVo.getPort());
			rdb.setDatabaseType(dRdbVo.getDatabaseType());
			rdb.setTableName(dRdbVo.getTableName());
			rdb.setUser(dRdbVo.getUser());
			rdbMapper.insert(rdb);
			
			dataSet.setTypeId("remote_rdb");	
			dataSet.setRdbId(rdbId);
			
		/*}else if (typeId.equals("local_rdb")) {
			
			
		}*/
		String id = "DATASET_"+new Date().getTime();
		dataSet.setId(id);
		dataSet.setCreateTime(new Date());
		dataSet.setName(dRdbVo.getName());
		dataSet.setUserId(userId);
		dataSet.setProjectId(dRdbVo.getProjectId());
		int i = dataSetMapper.insert(dataSet);
		return i;
	}

	@Override
	public Map<String, Object> findByTableAndId(Map<String, Object> map) {
		String tableName = StringHandler.objectToString(map.get("tableName"));
		String id = StringHandler.objectToString(map.get("id"));
		return dataTableMapper.findByTableAndId(tableName,id);
	}

	@Override
	public List<Map<String, Object>> chartsByName(String name,String projectId, String userId) {
		return dataSetMapper.chartsByName(name,projectId,userId);
	}

}
