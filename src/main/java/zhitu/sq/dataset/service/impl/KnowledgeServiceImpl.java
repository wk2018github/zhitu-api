package zhitu.sq.dataset.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.DataSetMapper;
import zhitu.sq.dataset.mapper.DataTableMapper;
import zhitu.sq.dataset.mapper.KnowledgeMapper;
import zhitu.sq.dataset.mapper.RdbMapper;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.Knowledge;
import zhitu.sq.dataset.model.Rdb;
import zhitu.sq.dataset.service.KnowledgeService;
import zhitu.util.JdbcDbUtils;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;

@Service
public class KnowledgeServiceImpl implements KnowledgeService{

	@Autowired
	private KnowledgeMapper knowledgeMapper;
	@Autowired
	private DataSetMapper dataSetMapper;
	@Autowired
	private RdbMapper rdbMapper;
	@Autowired
	private DataTableMapper dataTableMapper;

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
		knowledge.setId("KN_"+System.currentTimeMillis());
		knowledge.setCreateTime(new Date());
		return knowledgeMapper.insert(knowledge);
	}

	@Override
	public List<Map<String, Object>> selectByDataSetId(List<String> datasetIds) throws Exception{
		List<Map<String, Object>> list = new ArrayList<>();
		for(String dataSetId : datasetIds){
			Map<String, Object> map = new HashMap<>();
			DataSet dataSet = dataSetMapper.selectByPrimaryKey(dataSetId);
			if (dataSet.getTypeId().equals("ftp_file")) {
				List<String> fileds = dataTableMapper.findFields("zt_data_ftp_file");
				map.put("fileds", fileds);
				map.put("tableName","zt_data_ftp_file");
				list.add(map);
			}else if(dataSet.getTypeId().equals("local_rdb")){
				List<String> fileds = dataTableMapper.findFields(dataSet.getDataTable());
				map.put("fileds", fileds);
				map.put("tableName", dataSet.getDataTable());
				list.add(map);
			}else {
				Rdb rdb = rdbMapper.selectByPrimaryKey(dataSet.getRdbId());
				List<String> fileds = JdbcDbUtils.jdbcTable(rdb);
				map.put("fileds", fileds);
				map.put("tableName", rdb.getTableName());
				list.add(map);
			}
		}
		return list;
	}
}
