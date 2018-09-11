package zhitu.sq.dataset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zhitu.sq.dataset.controller.vo.LineChart;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.TableMoney;
import zhitu.sq.dataset.mapper.SequentialMapper;
import zhitu.sq.dataset.service.SequentialService;

@Service
@Transactional
public class SequentialServiceImpl implements SequentialService {

	@Autowired
	private SequentialMapper sequentialMapper;
	
	@Override
	public List<Select> queryAnalysisSelect() throws Exception {

		return sequentialMapper.queryAnalysisSelect();
	}
	
	@Override
	public List<Select> querySelectOne() throws Exception {

		return sequentialMapper.querySelectOne();
	}
	
	@Override
	public List<Select> querySelectTwo(Map<String,Object> map) throws Exception {

		return sequentialMapper.querySelectTwo(map);
	}

	@Override
	public List<String> queryYearScope() throws Exception {

//		String rootName = singlePointMapper.queryRootSinglePoint();


		return null;
	}
	
	@Override
	public Map<String,Object> startAnalysis(Map<String,Object> map) throws Exception {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		String analysisType = map.get("analysisType").toString(); //01 全图  02 流程
		String analysisObj = "";
		String nodeId = map.get("analysisType").toString(); // 图的根节点id
		String tableCode = map.get("basicsTable").toString(); //基础表的码值
		String tableName = sequentialMapper.queryBasicsTableName(tableCode).get(0).getName(); //完整表名称
		String nodeColumn = map.get("nodeColumn").toString(); //选择的金额列的码值
		String columnName = sequentialMapper.queryColumnNameByCodeParentCodeFromDict(nodeColumn,tableCode).get(0); //列名称
		String year = map.get("year").toString(); // 2016,2017 年度范围
		List<Integer> years = getYears(year);
		if("01".equals(analysisType)){
			analysisObj = map.get("analysisObj").toString(); //分析对象 
			List<String> moneys = new ArrayList<>();
			
			for (int i = 0; i < years.size(); i++) {
				String money = sequentialMapper.queryMoney(tableName, columnName, years.get(i).toString());
				moneys.add(money);
			}
			LineChart l = new LineChart(analysisObj,moneys);
			result.put("obj", l);
		} else {
			
			analysisObj = sequentialMapper.queryBasicsTableNameByCode(tableCode);
			List<String> moneys = new ArrayList<>();
			
			for (int i = 0; i < years.size(); i++) {
				String money = sequentialMapper.queryMoney(tableName, columnName, years.get(i).toString());
				moneys.add(money);
			}
			LineChart l = new LineChart(analysisObj,moneys);
			result.put("obj", l);
		}
//		if("02".equals(analysisType)){
//			LineChart l = new LineChart("科处室指标",money);
//			LineChart l2 = new LineChart("单位指标",money2);
//			LineChart l3 = new LineChart("计划",money3);
//			LineChart l4 = new LineChart("支付",money4);
//			List<LineChart> ls = new ArrayList<>();
//			ls.add(l);
//			ls.add(l2);
//			ls.add(l3);
//			ls.add(l4);
//			result.put("obj", ls);
//		}
		result.put("transverse", years); //横线  年度
		
		return result;
	}
	
	@Override
	public List<TableMoney> queryTableMoney(Map<String,Object> map) throws Exception {
		String tableCodes = map.get("tableCodes").toString();
		List<TableMoney> tableMoney = sequentialMapper.queryBasicsTableName(tableCodes);
//		tableMoney = getTableMoney(tableMoney);
		for (int i = 0; i < tableMoney.size(); i++) {
			String tableName = tableMoney.get(i).getName(); //表名称
			List<Map<String,Object>> ls = new ArrayList<>();
			List<String> column = sequentialMapper.queryNodeColumnName(tableMoney.get(i).getCode()); //列名称集合
			for (String s : column) {
				Map<String,Object> m = new HashMap<String,Object>();
				String money = sequentialMapper.queryMoney(tableName,s,null);
				m.put(s, money);
				ls.add(m);
			}
			tableMoney.get(i).setLs(ls);
		}
		
		return tableMoney;
	}
	
	public List<TableMoney> getTableMoney(List<TableMoney> tableMoney) throws Exception {
		for (TableMoney t : tableMoney) {
//			String table = getPayTable()+t.getName(); //完整表名称
//			t.setName(table);
		}
		return tableMoney;
	}
	
	
	public List<Integer> getYears(String year){
		List<Integer> ls = new ArrayList<>();
		String[] years = year.split(",");
		int length = Integer.valueOf(years[1]) - Integer.valueOf(years[0]);
		ls.add(Integer.valueOf(years[0])); //起始年度
		for (int i = 1; i < length; i++) {
			ls.add(Integer.valueOf(years[0])+i);
		}
		ls.add(Integer.valueOf(years[1])); //终止年度
		return ls;
	}
	
}
