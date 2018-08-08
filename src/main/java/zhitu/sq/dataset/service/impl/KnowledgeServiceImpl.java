package zhitu.sq.dataset.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhuanzhi.structuring.App;
import com.zhuanzhi.structuring.model.Underlying;

import zhitu.sq.dataset.controller.vo.ActivityVo;
import zhitu.sq.dataset.controller.vo.AdviceVo;
import zhitu.sq.dataset.controller.vo.EnforcementVo;
import zhitu.sq.dataset.controller.vo.OpinionVo;
import zhitu.sq.dataset.controller.vo.ProblemVo;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.UnderlyingVo;
import zhitu.sq.dataset.mapper.DataSetMapper;
import zhitu.sq.dataset.mapper.DataTableMapper;
import zhitu.sq.dataset.mapper.FtpFileMapper;
import zhitu.sq.dataset.mapper.KnowledgeMapper;
import zhitu.sq.dataset.mapper.RdbMapper;
import zhitu.sq.dataset.mapper.TaskInfoMapper;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.FtpFile;
import zhitu.sq.dataset.model.Knowledge;
import zhitu.sq.dataset.model.Rdb;
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.service.KnowledgeService;
import zhitu.util.DataSourceUtil;
import zhitu.util.JdbcDbUtils;
import zhitu.util.Neo4jTest;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;
import zhitu.util.TableSqlUtil;

@Service
@Transactional
public class KnowledgeServiceImpl implements KnowledgeService {

	@Autowired
	private KnowledgeMapper knowledgeMapper;
	@Autowired
	private DataSetMapper dataSetMapper;
	@Autowired
	private RdbMapper rdbMapper;
	@Autowired
	private DataTableMapper dataTableMapper;
	@Autowired
	private FtpFileMapper ftpFileMapper;
	@Autowired
	private TaskInfoMapper taskInfoMapper;
	@Autowired
	private DataSourceUtil dataSourceUtil;

	@Override
	public PageInfo<Map<String, Object>> queryKnowledge(Map<String, Object> map) throws Exception {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		String name = StringHandler.objectToString(map.get("name"));
		List<Map<String, Object>> list = knowledgeMapper.findByName(name);
		return new PageInfo<>(list);
	}

	@Override
	public int updateKnowledge(Knowledge knowledge) throws Exception {
		return knowledgeMapper.updateByPrimaryKeySelective(knowledge);
	}

	@Override
	public int deleteKnowledge(List<String> ids) {
		return knowledgeMapper.deleteKnowledge(ids);
	}

	@Override
	public int saveKnowledge(Knowledge knowledge) {
		knowledge.setId("KN_" + System.currentTimeMillis());
		knowledge.setCreateTime(new Date());
		return knowledgeMapper.insert(knowledge);
	}

	@Override
	public List<Map<String, Object>> selectByDataSetId(List<String> ids) throws Exception {
		List<Map<String, Object>> list = new ArrayList<>();
		for (String id : ids) {
			Map<String, Object> map = new HashMap<>();
			Knowledge knowledge = knowledgeMapper.selectByPrimaryKey(id);
			if(knowledge.getTableName()!=null){
				List<String> fileds = dataTableMapper.findFields(knowledge.getTableName());
				map.put("fileds", fileds);
				map.put("tableName", knowledge.getTableName());
				map.put("id", id);
				list.add(map);
			}else{
				DataSet dataSet = dataSetMapper.selectByPrimaryKey(knowledge.getDatasetId());
				if (dataSet.getTypeId().equals("local_rdb")) {
					List<String> fileds = dataTableMapper.findFields(dataSet.getDataTable());
					map.put("fileds", fileds);
					map.put("tableName", dataSet.getDataTable());
					map.put("id", id);
					list.add(map);
				} else if(dataSet.getTypeId().equals("remote_rdb")){
					Rdb rdb = rdbMapper.selectByPrimaryKey(dataSet.getRdbId());
					List<String> fileds = JdbcDbUtils.jdbcTable(rdb);
					map.put("fileds", fileds);
					map.put("tableName", rdb.getTableName());
					map.put("id", id);
					list.add(map);
				}
			}
			
		}
		return list;
	}

	@Override
	public String extractKnowledge(Map<String, Object> map, String userId) throws Exception {
		String datasetId = StringHandler.objectToString(map.get("datasetId"));
		String name = StringHandler.objectToString(map.get("name"));
		String description = StringHandler.objectToString(map.get("description"));

		List<FtpFile> fList = ftpFileMapper.selectByDataSetId(datasetId);
		String key = StringHandler.objectToString(map.get("key"));
		List<String> adudits = (List<String>) map.get("audit");
		Map<String, Object> tMap = new HashMap<>();
		if (key.equals("01")) {
			// 根据选择槽位创建对应槽位表
			for (String audit : adudits) {
				if (audit.equals("01")) {
					String tableName = "zt_knowledge_activity_" + System.currentTimeMillis();
					String sql = TableSqlUtil.tableActivity(tableName);
					dataTableMapper.createTalbe(sql);
					// 新增knowledge数据
					Knowledge knowledge = new Knowledge();
					knowledge.setId("KN_" + System.currentTimeMillis());
					knowledge.setCreateTime(new Date());
					knowledge.setTableName(tableName);
					knowledge.setName(name);
					knowledge.setDatasetId(datasetId);
					knowledge.setDescription(description);
					knowledgeMapper.insert(knowledge);
					// 槽位表名
					tMap.put("activity", tableName);
				} else if (audit.equals("02")) {
					String tableName = "zt_knowledge_underlying_" + System.currentTimeMillis();
					String sql = TableSqlUtil.tableUnderlying(tableName);
					dataTableMapper.createTalbe(sql);
					// 新增knowledge数据
					Knowledge knowledge = new Knowledge();
					knowledge.setId("KN_" + System.currentTimeMillis());
					knowledge.setCreateTime(new Date());
					knowledge.setTableName(tableName);
					knowledge.setName(name);
					knowledge.setDatasetId(datasetId);
					knowledge.setDescription(description);
					knowledgeMapper.insert(knowledge);
					// 槽位表名
					tMap.put("underlying", tableName);
				} else if (audit.equals("06")) {
					String tableName = "zt_knowledge_enforcement_" + System.currentTimeMillis();
					String sql = TableSqlUtil.tableEnforcement(tableName);
					dataTableMapper.createTalbe(sql);
					// 新增knowledge数据
					Knowledge knowledge = new Knowledge();
					knowledge.setId("KN_" + System.currentTimeMillis());
					knowledge.setCreateTime(new Date());
					knowledge.setTableName(tableName);
					knowledge.setName(name);
					knowledge.setDatasetId(datasetId);
					knowledge.setDescription(description);
					knowledgeMapper.insert(knowledge);
					// 槽位表名
					tMap.put("enforcement", tableName);
				} else if (audit.equals("03")) {
					String tableName = "zt_knowledge_opinion_" + System.currentTimeMillis();
					String sql = TableSqlUtil.tableOpinion(tableName);
					dataTableMapper.createTalbe(sql);
					// 新增knowledge数据
					Knowledge knowledge = new Knowledge();
					knowledge.setId("KN_" + System.currentTimeMillis());
					knowledge.setCreateTime(new Date());
					knowledge.setTableName(tableName);
					knowledge.setName(name);
					knowledge.setDatasetId(datasetId);
					knowledge.setDescription(description);
					knowledgeMapper.insert(knowledge);
					// 槽位表名
					tMap.put("opinion", tableName);
				} else if (audit.equals("04")) {
					String tableName = "zt_knowledge_problem_" + System.currentTimeMillis();
					String sql = TableSqlUtil.tableProblem(tableName);
					dataTableMapper.createTalbe(sql);
					// 新增knowledge数据
					Knowledge knowledge = new Knowledge();
					knowledge.setId("KN_" + System.currentTimeMillis());
					knowledge.setCreateTime(new Date());
					knowledge.setTableName(tableName);
					knowledge.setName(name);
					knowledge.setDatasetId(datasetId);
					knowledge.setDescription(description);
					knowledgeMapper.insert(knowledge);
					// 槽位表名
					tMap.put("problem", tableName);
				} else if (audit.equals("05")) {
					String tableName = "zt_knowledge_advice_" + System.currentTimeMillis();
					String sql = TableSqlUtil.tableAdvice(tableName);
					dataTableMapper.createTalbe(sql);
					// 新增knowledge数据
					Knowledge knowledge = new Knowledge();
					knowledge.setId("KN_" + System.currentTimeMillis());
					knowledge.setCreateTime(new Date());
					knowledge.setTableName(tableName);
					knowledge.setName(name);
					knowledge.setDatasetId(datasetId);
					knowledge.setDescription(description);
					knowledgeMapper.insert(knowledge);
					// 槽位表名
					tMap.put("advice", tableName);
				}
			}
		}
		HashMap<String, ArrayList<HashMap<String, String>>> list = new HashMap<String, ArrayList<HashMap<String, String>>>();
		// //任务表插入一条数据,创建任务
		// TaskInfo taskInfo = new TaskInfo();
		// Date date = new Date();
		// taskInfo.setId("TASK_"+System.currentTimeMillis());
		// taskInfo.setName("暂时还未确定规则");
		// taskInfo.setDescription("描述信息");
		// taskInfo.setStatus("1");
		// taskInfo.setCreateTime(date);
		// taskInfo.setUserId(userId);
		// taskInfo.setProjectId("暂时没有project");
		// taskInfoMapper.insert(taskInfo);

		FTPClient ftp = dataSourceUtil.getFTPClient();
		if (null == ftp) {
			throw new Exception("获取FTP失败");
		}
		// 获取ftp 文件
		for (FtpFile file : fList) {
			App.processFile("G:\\项目\\海南\\文档\\文档提取整理\\工伤生育市本级审计报告 -未标注.doc", list);
		}
		ftp.disconnect();

		if (tMap.containsKey("activity")) {
			List<HashMap<String, String>> activityList = list.get("activity");
			if (activityList.size() > 0) {
				String tableName = StringHandler.objectToString(tMap.get("activity"));
				for (HashMap<String, String> act : activityList) {
					ActivityVo activityVo = new ActivityVo();
					BeanUtils.copyProperties(activityVo, act);
					dataTableMapper.insertActivity(activityVo, tableName);
				}
			}

		}
		if (tMap.containsKey("advice")) {
			List<HashMap<String, String>> activityList = list.get("advice");
			if (activityList.size() > 0) {
				String tableName = StringHandler.objectToString(tMap.get("advice"));
				for (HashMap<String, String> act : activityList) {
					// activity 插入数据
					AdviceVo adviceVo = new AdviceVo();
					BeanUtils.copyProperties(adviceVo, act);
					dataTableMapper.insertAdvice(adviceVo, tableName);
				}
			}
		}
		if (tMap.containsKey("opinion")) {
			List<HashMap<String, String>> activityList = list.get("opinion");
			if (activityList.size() > 0) {
				String tableName = StringHandler.objectToString(tMap.get("opinion"));
				for (HashMap<String, String> act : activityList) {
					// activity 插入数据
					OpinionVo opinionVo = new OpinionVo();
					BeanUtils.copyProperties(opinionVo, act);
					dataTableMapper.insertOpinion(opinionVo, tableName);

				}
			}
		}
		if (tMap.containsKey("underlying")) {
			List<HashMap<String, String>> activityList = list.get("underlying");
			if (activityList.size() > 0) {
				String tableName = StringHandler.objectToString(tMap.get("underlying"));
				for (HashMap<String, String> act : activityList) {
					// activity 插入数据
					UnderlyingVo underlyingVo = new UnderlyingVo();
					BeanUtils.copyProperties(underlyingVo, act);
					dataTableMapper.insertUnderlying(underlyingVo, tableName);

				}
			}
		}
		if (tMap.containsKey("problem")) {
			List<HashMap<String, String>> activityList = list.get("problem");
			if (activityList.size() > 0) {
				String tableName = StringHandler.objectToString(tMap.get("problem"));
				for (HashMap<String, String> act : activityList) {
					// activity 插入数据
					ProblemVo problemVo = new ProblemVo();
					BeanUtils.copyProperties(problemVo, act);
					dataTableMapper.insertProblem(problemVo, tableName);

				}
			}
		}

		if (tMap.containsKey("enforcement")) {
			List<HashMap<String, String>> activityList = list.get("enforcement");
			if (activityList.size() > 0) {
				String tableName = StringHandler.objectToString(tMap.get("enforcement"));
				for (HashMap<String, String> act : activityList) {
					// activity 插入数据
					EnforcementVo enforcement = new EnforcementVo();
					BeanUtils.copyProperties(enforcement, act);
					dataTableMapper.insertEnforcement(enforcement, tableName);

				}
			}
		}

		return null;
	}

	@Override
	public Map<String, Object> addToMap(Map<String, Object> map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();

		DataSet source = knowledgeMapper.queryDataSetByKnId(map);
		DataSet target = knowledgeMapper.queryDataSetByKnId(map);

		String sourceDataSetType = source.getTypeId();// 源数据集的类型
		String targetDataSetType = target.getTypeId();// 目标数据集的类型
		String sourceTable = "";
		String targetTable = "";
		// 文件上传
		// if("local_file".equals(sourceDataSetType)){
		//
		// }
		// 源数据集本地数据库导入
		if ("local_rdb".equals(sourceDataSetType)) {
			// 获取数据库连接 查询数据集中 表 字段 对应的数据
			Configuration config = new PropertiesConfiguration("application.properties");
			String url = config.getString("spring.datasource.url");
			String userName = config.getString("spring.datasource.username");
			String password = config.getString("spring.datasource.password");
			sourceTable = source.getDataTable();
			String columnNames = knowledgeMapper.queryColumnByRdbId(source.getRdbId());
			List<String> list = dataSourceUtil.queryLocalTableColumnDataNoLimit(url, userName, password, sourceTable,
					columnNames);
			// 连接neo4j,数据插入至图数据库
			Neo4jTest.addToNeo4j(sourceTable, columnNames, list);
		}
		// 源数据集远程数据库直接过去的数据
		if ("remote_rdb".equals(sourceDataSetType)) {
			// 获取数据库连接 查询数据集中 表 字段 对应的数据
			Rdb rdb = knowledgeMapper.queryRdbById(source.getRdbId());
			String url = rdb.getHost();
			Integer port = rdb.getPort();
			String userName = rdb.getUser();
			String password = rdb.getPassword();
			String databaseName = rdb.getDbName();
			sourceTable = rdb.getTableName();
			String columnNames = rdb.getColumnNames();
			List<String> list = dataSourceUtil.queryTableColumnDataNoLimit(url, port, userName, password, databaseName,
					sourceTable, columnNames);
			// 连接neo4j,数据插入至图数据库
			Neo4jTest.addToNeo4j(sourceTable, columnNames, list);
		}

		// 文件上传
		// if("local_file".equals(sourceDataSetType)){
		//
		// }
		// 目标数据集本地数据库导入
		if ("local_rdb".equals(targetDataSetType)) {
			// 获取数据库连接 查询数据集中 表 字段 对应的数据
			Configuration config = new PropertiesConfiguration("application.properties");
			String url = config.getString("spring.datasource.url");
			String userName = config.getString("spring.datasource.username");
			String password = config.getString("spring.datasource.password");
			targetTable = target.getDataTable();
			String columnNames = knowledgeMapper.queryColumnByRdbId(target.getRdbId());
			List<String> list = dataSourceUtil.queryLocalTableColumnDataNoLimit(url, userName, password, targetTable,
					columnNames);
			// 连接neo4j,数据插入至图数据库
			Neo4jTest.addToNeo4j(targetTable, columnNames, list);
		}
		// 目标数据集远程数据库直接过去的数据
		if ("remote_rdb".equals(targetDataSetType)) {
			// 获取数据库连接 查询数据集中 表 字段 对应的数据
			Rdb rdb = knowledgeMapper.queryRdbById(target.getRdbId());
			String url = rdb.getHost();
			Integer port = rdb.getPort();
			String userName = rdb.getUser();
			String password = rdb.getPassword();
			String databaseName = rdb.getDbName();
			targetTable = rdb.getTableName();
			String columnNames = rdb.getColumnNames();
			List<String> list = dataSourceUtil.queryTableColumnDataNoLimit(url, port, userName, password, databaseName,
					targetTable, columnNames);
			// 连接neo4j,数据插入至图数据库
			Neo4jTest.addToNeo4j(targetTable, columnNames, list);
		}

		// 建立两个数据集在neo4j中的关系
		String sourceKey = String.valueOf(map.get("sourceKey"));
		String targetKey = String.valueOf(map.get("targetKey"));
		String relationship = String.valueOf(map.get("relationship"));
		Neo4jTest.createRelationship(sourceTable, targetTable, sourceKey, targetKey, relationship);

		return result;
	}

	@Override
	public List<Select> queryKnowledge() throws Exception {

		return knowledgeMapper.queryKnowledge();
	}

	@Override
	public List<String> queryForeignKey(Map<String, Object> map) throws Exception {

		DataSet dataSet = knowledgeMapper.queryDataSetByKnId(map);

		// String dataSetType = dataSet.getTypeId();

		String rdbId = dataSet.getRdbId();
		// 关系数据库导入本地//关系数据库不导入本地
		String column = knowledgeMapper.queryColumnByRdbId(rdbId);

		// 文件上传
		// if("local_file".equals(dataSetType)){
		//
		// }

		return strToList(column);
	}

	public List<String> strToList(String str) {
		if (StringUtils.isEmpty(str)) {
			return null;
		}
		List<String> list = new ArrayList<String>();

		String[] split = str.split(",");

		list = Arrays.asList(split);

		return list;
	}

}
