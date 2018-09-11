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
				String money = sequentialMapper.queryMoney(tableName,s);
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
	
}
