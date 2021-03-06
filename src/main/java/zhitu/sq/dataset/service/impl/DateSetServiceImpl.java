package zhitu.sq.dataset.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.DataSetRdbVo;
import zhitu.sq.dataset.controller.vo.RdbVo;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.mapper.DataSetMapper;
import zhitu.sq.dataset.mapper.DataTableMapper;
import zhitu.sq.dataset.mapper.FtpFileMapper;
import zhitu.sq.dataset.mapper.RdbMapper;
import zhitu.sq.dataset.mapper.TaskInfoMapper;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.FtpFile;
import zhitu.sq.dataset.model.Rdb;
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.util.NumberDealHandler;
import zhitu.util.SparkSql;
import zhitu.util.StringHandler;
import zhitu.util.TikaUtils;
import zhitu.util.DataSourceUtil;
import zhitu.util.FileUpload;
import zhitu.util.JdbcDbUtils;

@Service
@Transactional
public class DateSetServiceImpl implements DataSetService{

	@Autowired
	private DataSetMapper dataSetMapper;
	@Autowired
	private FtpFileMapper ftpFileMapper;
	@Autowired
	private DataTableMapper dataTableMapper;
	@Autowired
	private RdbMapper rdbMapper;
	@Autowired
	private DataSourceUtil dataSourceUtil;
	@Autowired
	private TaskInfoMapper taskInfoMapper;
	
	
	@Override
	public DataSet selectByPrimaryKey(String id) {
		return dataSetMapper.selectByPrimaryKey(id);
	}
	
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
	public String saveLocalDataSet(String userId, String name, String describe, String projectId,
			MultipartFile files, HttpServletRequest request) throws Exception {
		String dataSetId = "DATASET_" + System.currentTimeMillis();
		
//		FTPClient ftp = dataSourceUtil.getFTPClient();
//		if (null == ftp) {
//			throw new Exception("获取FTP失败");
//		}
		
		// 文件上传到ftp
//		String directory = "zhituFile"+System.currentTimeMillis();
//		String ftpName = String.valueOf(System.currentTimeMillis())+su;
//		boolean flag = upload(ftp, file, directory, ftpName);
//		if (!flag) {
//			throw new Exception(fileName + "上传失败");
//		}
//		System.out.println("upload success");
//		ftp.disconnect();
		Properties p = System.getProperties();
		String sys = p.getProperty("os.name").toLowerCase();
		String split = "";
		if (sys.contains("windows")) {
			split = "\\\\";
		} else {
			split = "/";
		}
		
		List<String> paths = FileUpload.upload(request);// 上传返回path,成功和失败的都有
		List<String> success = new ArrayList<String>();// 上传成功的path集合
		boolean flag = false; // 是否有任何一个文件上传成功
		for (int i = 0; i < paths.size(); i++) {
			String path = paths.get(i);
			String[] ps = path.split(split);
			if(ps.length>1){
				paths.remove(path);
				success.add(path);
				flag = true;
				i--;
			}
		}
		if(!flag){
			return null;
		}
		
		DataSet dataSet = new DataSet(dataSetId, new Date(), name, describe, "ftp_file", userId, projectId, null, null);

		// 文件上传成功后保存数据集
		int i = dataSetMapper.insert(dataSet);
		if(i<1){
			return null;
		}
		
		for (int j = 0; j < success.size(); j++) {
			String path = success.get(j);
			String[] ps = path.split(split);
			FtpFile f = new FtpFile();
			f.setId("PDF_"+System.currentTimeMillis()); //ftp id
			String fileName = ps[ps.length-1]; //ftpFile name
			f.setFileName(fileName);
			File file = new File(path);
			String fileAbstract = TikaUtils.parseFile(file);
			f.setFileAbstract(fileAbstract);
			f.setFtpurl(path); //ftpFile url
			f.setDatasetId(dataSetId);
			int k = dataSetMapper.insertFtpFile(f);
			if(k>0){
				success.remove(j);
				j--;
			}
			Thread.sleep(1);
		}
		paths.addAll(success);
		return String.join(",", paths);
	}

	@Override
	public int updateDataSet(DataSet dSet) {
		return dataSetMapper.updateDataSet(dSet);
	}

	
	@Override
	public PageInfo<Map<String, Object>> findByIdFtpFile(Map<String, Object> map) throws Exception {
		
    	int page = NumberDealHandler.objectToInt(map.get("page"));
    	int rows = NumberDealHandler.objectToInt(map.get("rows"));
    	String id = StringHandler.objectToString(map.get("id"));
    	PageHelper.startPage(page,rows);
    	List<Map<String, Object>> list = ftpFileMapper.findByDataSetId(id);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<Map<String, Object>> findByIdLcoal(Map<String, Object> map) throws Exception {
		String dataTable = StringHandler.objectToString(map.get("dataTable"));
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
    	
    	List<Map<String, Object>> list = dataTableMapper.findByDataSetId(dataTable);
		return new PageInfo<>(list);
	}
	
	@Override
	public Map<String, Object> findById(Map<String, Object> map) throws Exception{
		
    	int page = NumberDealHandler.objectToInt(map.get("page"));
    	int rows = NumberDealHandler.objectToInt(map.get("rows"));
    	String rdbId = StringHandler.objectToString(map.get("rdbId"));
    	PageHelper.startPage(page,rows);
		//先查询远程连接信息及表名
		Rdb rdb = rdbMapper.selectByPrimaryKey(rdbId);
		List<Map<String, Object>> list  = JdbcDbUtils.jdbcTable(rdb,page,rows);
		Integer records = JdbcDbUtils.jdbcTableCount(rdb);
		Integer total = 0;
		if(records>0){
			float d = (float)records/rows;
			total = (int)Math.ceil(d);
		}
		Map<String, Object> map2 = new HashMap<>();
		map2.put("total", total);
		map2.put("records", records);
		map2.put("page", page);
		map2.put("rows", list);
		map2.put("userdata", "");
		return map2;
	}

	@Override
	public Map<String, Object> findByTableValue(DataSet dataSet) throws Exception {
		Map<String, Object> map = new HashMap<>();
		String typeId = dataSet.getTypeId();
		Integer count = null;
		List<String> fileds = null;
		if (typeId.equals("ftp_file")) {
			List<Map<String, Object>> list =ftpFileMapper.findByDataSetId(dataSet.getId());
			count = list.size();
			fileds = ftpFileMapper.findFields();
		}else if (typeId.equals("local_rdb")) {
			//根据dataTable及数据库表查询改表下所有数据
			List<Map<String, Object>> list = dataTableMapper.findByDataSetId(dataSet.getDataTable());
			count = list.size();
//			Rdb rdb = rdbMapper.selectByPrimaryKey(dataSet.getRdbId());
			fileds = dataTableMapper.findFields(dataSet.getDataTable());
		}else{
			//先查询远程连接信息及表名
			Rdb rdb = rdbMapper.selectByPrimaryKey(dataSet.getRdbId());
			count = JdbcDbUtils.jdbcTableCount(rdb);
			fileds = JdbcDbUtils.jdbcTable(rdb);
		}
		map.put("count", count);
		map.put("fileds", fileds);
		return map;
	}
	
	@Override
	public void deleteById(List<String> ids) {
		for(int i=0;i<ids.size();i++){
			String id = ids.get(i);
			DataSet dataset = dataSetMapper.selectByPrimaryKey(id);
			
			if(dataset.getTypeId().equals("ftp_file")){
				ftpFileMapper.deleteByDataSetId(id);
			}else if(dataset.getTypeId().equals("local_rdb")){
				//根据dataTable及数据库表删除改表
				dataTableMapper.deleteByDataSetId(dataset.getDataTable());
				//根据rdbId删除信息
				rdbMapper.deleteByPrimaryKey(dataset.getRdbId());
			}else{
				//根据rdbId删除信息
				rdbMapper.deleteByPrimaryKey(dataset.getRdbId());
			}
			dataSetMapper.deleteByPrimaryKey(id);
		}
		
	}

	@Override
	public String saveSqlDataSet(DataSetRdbVo dRdbVo, String userId) {
//		String typeId = StringHandler.objectToString(dRdbVo.getTypeId());
		
		DataSet dataSet = new DataSet();
		
//		if(typeId.equals("remote_rdb")){
			
			/**
			 * 首先保存数据库连接rdb数据，在保存dataset信息
			 */
			Rdb rdb = new Rdb();
			String rdbId = "RDB_"+String.valueOf(System.currentTimeMillis());
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
		String id = "DATASET_"+String.valueOf(System.currentTimeMillis());
		dataSet.setId(id);
		dataSet.setCreateTime(new Date());
		dataSet.setName(dRdbVo.getName());
		dataSet.setUserId(userId);
		dataSet.setDescription(dRdbVo.getDescription());
		dataSet.setProjectId(dRdbVo.getProjectId());
		int i = dataSetMapper.insert(dataSet);
		if(i>0){
			return id;
		}
		return null;
//		return i;
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

	@Override
	public Map<String, Object> findByTable(RdbVo rdbVo) throws Exception{
		
		int page = rdbVo.getPage();
    	int rows = rdbVo.getRows();
//    	String rdbId = StringHandler.objectToString(map.get("rdbId"));
    	PageHelper.startPage(page,rows);
		//先查询远程连接信息及表名
		Rdb rdb = new Rdb();
		BeanUtils.copyProperties(rdb, rdbVo);
		List<Map<String, Object>> list  = JdbcDbUtils.jdbcTable(rdb,page,rows);
		Integer records = JdbcDbUtils.jdbcTableCount(rdb);
		Integer total = 0;
		if(records>0){
			float d = (float)records/rows;
			total = (int)Math.ceil(d);
		}
		Map<String, Object> map2 = new HashMap<>();
		map2.put("total", total);
		map2.put("records", records);
		map2.put("page", page);
		map2.put("rows", list);
		map2.put("userdata", "");
		return map2;
	}

	@Override
	public List<String> findByTableFiled(RdbVo rdbVo) throws Exception {
		Rdb rdb = new Rdb();
		BeanUtils.copyProperties(rdb, rdbVo);
		List<String> list = JdbcDbUtils.jdbcTable(rdb);
		return list;
	}
	
	@Override
	public List<Select> queryDBType() throws Exception {
		return dataSetMapper.queryDBType();
	}

	@Override
	public List<String> queryDBTables(Rdb rdb) throws Exception {
		List<String> list = new ArrayList<String>();
		String ip = rdb.getHost();
		Integer port = rdb.getPort();
		String userName = rdb.getUser();
		String password = rdb.getPassword();
		String databaseName = rdb.getDbName();
		
		String dbType = rdb.getDatabaseType().toLowerCase();
		// 数据库名称包含oracle,测试连接
//		if (dbType.contains("oracle")) {
//			
//		}
		// 数据库名称包含mysql,测试连接
		if (dbType.contains("mysql")) {
			list = dataSourceUtil.queryDataBaseTableInMysql(ip, port, userName, password, databaseName);
		}
		// 数据库名称包含sqlserver,测试连接
//		if (dbType.contains("sqlserver")) {
//			
//		}
		
		
		return list;
	}

	@Override
	public Map<String, Object> queryTableData(Map<String, Object> map) throws Exception {
		Map<String, Object> res = new HashMap<String,Object>();
		String ip = String.valueOf(map.get("host"));
		Integer port = Integer.parseInt(String.valueOf(map.get("port")));
		String userName = String.valueOf(map.get("user"));
		String password = String.valueOf(map.get("password"));
		String databaseName = String.valueOf(map.get("dbName"));
		String tableName = String.valueOf(map.get("tableName"));
		
		//分页参数
		Integer page = Integer.parseInt(String.valueOf(map.get("page")));
		Integer rows = Integer.parseInt(String.valueOf(map.get("rows")));
		Integer start = getIntStart(page,rows);
		Integer end = getIntEnd(page,rows);
		
		String dbType = String.valueOf(map.get("databaseType")).toLowerCase();
		// 数据库名称包含oracle,测试连接
//		if (dbType.contains("oracle")) {
//			
//		}
		// 数据库名称包含mysql,测试连接
		if (dbType.contains("mysql")) {
			res = dataSourceUtil.queryTableData(ip, port, userName, password, databaseName, tableName, start, end);
		}
		// 数据库名称包含sqlserver,测试连接
//		if (dbType.contains("sqlserver")) {
//			
//		}
		//查找数据库
		if(null == res){
			throw new Exception("查询表中的数据时出现异常");
		}
		return res;
	}

	@Override
	public List<String> queryTableColumn(Map<String, Object> map) throws Exception {
		List<String> list = new ArrayList<String>();
		String ip = String.valueOf(map.get("host"));
		Integer port = Integer.parseInt(String.valueOf(map.get("port")));
		String userName = String.valueOf(map.get("user"));
		String password = String.valueOf(map.get("password"));
		String databaseName = String.valueOf(map.get("dbName"));
		String tableName = String.valueOf(map.get("tableName"));
		
		String dbType = String.valueOf(map.get("databaseType")).toLowerCase();
		// 数据库名称包含oracle,测试连接
//		if (dbType.contains("oracle")) {
//			
//		}
		// 数据库名称包含mysql,测试连接
		if (dbType.contains("mysql")) {
			list = dataSourceUtil.queryTableColumn(ip, port, userName, password, databaseName, tableName);
		}
		// 数据库名称包含sqlserver,测试连接
//		if (dbType.contains("sqlserver")) {
//			
//		}
		
		if(null == list){
			throw new Exception("查询表中的列名称出现异常");
		}
		return list;
	}

	public Map<String, Object> queryTableColumnData(Map<String, Object> map) throws Exception {
		Map<String, Object> res = new HashMap<String,Object>();
		
		String ip = String.valueOf(map.get("host"));
		Integer port = Integer.parseInt(String.valueOf(map.get("port")));
		String userName = String.valueOf(map.get("user"));
		String password = String.valueOf(map.get("password"));
		String databaseName = String.valueOf(map.get("dbName"));
		String tableName = String.valueOf(map.get("tableName"));
		String columnNames = String.valueOf(map.get("columnNames"));
		
		DataSet ds = dataSetMapper.selectByPrimaryKey(String.valueOf(map.get("id")));
//		res.put("dataSet", ds);
		//分页参数
		Integer page = Integer.parseInt(String.valueOf(map.get("page")));
		Integer rows = Integer.parseInt(String.valueOf(map.get("rows")));
		Integer start = getIntStart(page,rows);
		Integer end = getIntEnd(page,rows);
		//直接查询,不查redis
		res = dataSourceUtil.queryTableColumnData(ip, port, userName, password, databaseName, tableName, columnNames, start, end);
		
		if(null == res){
			throw new Exception("查询表中指定列数据出现异常");
		}
		res.put("dataSetName", ds.getName());
		return res;
	}
	
	@Override
	public Map<String, Object> queryLocalTableColumnData(Map<String, Object> map,String userId) throws Exception {
//		Configuration config = new PropertiesConfiguration("application.properties");
//		String url = config.getString("spring.datasource.url");
//		String userName = config.getString("spring.datasource.username");
//		String password = config.getString("spring.datasource.password");
//		String columnNames = String.valueOf(map.get("columnNames"));
//		
		Map<String, Object> res = new HashMap<String,Object>();
		
		//RDB表插入一条数据
		String rdbId = "RDB_"+System.currentTimeMillis();
		map.put("rdbId", rdbId);
		Rdb rdb = getRdb(map);
		rdb.setCreateTime(new Date());
		int i = rdbMapper.insert(rdb);
		//数据表插入一条数据
		String id = "DATASET_"+System.currentTimeMillis();
		map.put("id", id);
		String dataTable = "zt_data_"+id;//本地表名称 zt_data_数据集ID
		map.put("dataTable",dataTable);
		DataSet dataSet = getDataSet(map);
		dataSet.setUserId(userId);
		dataSet.setDataTable(dataTable);
		int j = dataSetMapper.insert(dataSet);
		if(i<1 || j<1){
			throw new Exception("数据集信息保存异常"); 
		}
		//任务表插入一条数据,创建任务
		TaskInfo taskInfo = new TaskInfo();
		Date date = new Date();
		taskInfo.setId("TASK_"+System.currentTimeMillis());
		taskInfo.setName("暂时还未确定规则");
		taskInfo.setDescription("描述信息");
		taskInfo.setStatus("1");
		taskInfo.setCreateTime(date);
		taskInfo.setUserId(userId);
		taskInfo.setProjectId("暂时没有project");
		taskInfoMapper.insert(taskInfo);
		
		//数据迁移
		SparkSql.migration(rdb, dataTable,taskInfo.getId());
		
		res.put("id", dataSet.getId());
		res.put("taskId",taskInfo.getId());
		return res;
	}
	
	public DataSet getDataSet(Map<String, Object> map){
		DataSet ds = new DataSet();
		ds.setId(String.valueOf(map.get("id")));
		ds.setCreateTime(new Date());
		ds.setName(String.valueOf(map.get("name")));
		ds.setDescription(String.valueOf(map.get("description")));
		ds.setTypeId("local_rdb");
		ds.setUserId(String.valueOf(map.get("userId")));
		ds.setProjectId(String.valueOf(map.get("projectId")));
		ds.setRdbId(String.valueOf(map.get("rdbId")));
		return ds;
	}
	public Rdb getRdb(Map<String, Object> map){
		Rdb rdb = new Rdb();
		rdb.setId(String.valueOf(map.get("rdbId")));
		rdb.setCharset(String.valueOf(map.get("charset")));
		rdb.setColumnNames(String.valueOf(map.get("columnNames")));
		rdb.setDatabaseType(String.valueOf(map.get("databaseType")));
		rdb.setDbName(String.valueOf(map.get("dbName")));
		rdb.setHost(String.valueOf(map.get("host")));
		rdb.setPassword(String.valueOf(map.get("password")));
		rdb.setPort(Integer.parseInt(String.valueOf(map.get("port"))));
		rdb.setTableName(String.valueOf(map.get("tableName")));
		rdb.setUser(String.valueOf(map.get("user")));
		return rdb;
	}
	public Integer getIntStart(int page,int rows){
		return (page - 1)*rows;
	}
	public Integer getIntEnd(int page,int rows){
		return page*rows;
	}
	
	public boolean upload(FTPClient ftp, MultipartFile file, String directory, String fileName) throws Exception {
		boolean flag = false;
		String ftpPath = new String(directory.getBytes("GBK"),"iso-8859-1");
		boolean m = ftp.makeDirectory(ftpPath);// 创建文件夹
		if (!m) {
			throw new Exception("文件夹创建失败");
		}
		m = ftp.changeWorkingDirectory(directory);
		if (!m) {
			throw new Exception("进入文件夹失败");
		}
		ftp.enterLocalPassiveMode(); //被动模式
//		ftp.enterLocalActiveMode(); //主动模式
		// 上传文件
		// FTP协议规定文件编码格式为ISO-8859-1
		fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
		InputStream in = file.getInputStream();
		
		ftp.setBufferSize(1024*1024*50);
		flag = ftp.storeFile(new String(fileName.getBytes("GBK"),"ISO-8859-1"), in);//
		
		return flag;

	}

	@Override
	public DataSet selectById(String datasetId) {
		return dataSetMapper.selectByPrimaryKey(datasetId);
	}

}
