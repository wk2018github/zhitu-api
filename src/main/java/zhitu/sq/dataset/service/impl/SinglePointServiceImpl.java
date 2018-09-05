package zhitu.sq.dataset.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.mapper.GraphMapper;
import zhitu.sq.dataset.mapper.SinglePointMapper;
import zhitu.sq.dataset.service.SinglePointService;
import zhitu.vgraph.Node;
import zhitu.vgraph.NodeTypes;

@Service
@Transactional
public class SinglePointServiceImpl implements SinglePointService{

	@Autowired
	private SinglePointMapper singlePointMapper;
	@Autowired
	private GraphMapper graphMapper;

	@Override
	public String initRootSinglePoint() throws Exception{
		
		String rootName = singlePointMapper.queryRootSinglePoint();
		
		Node node = new Node(rootName, NodeTypes.PROCESS); //表节点
		
		return node.convertTreeToJsonObject().toString();
	}

	@Override
	public List<String> initAnnularData() throws Exception {
		Configuration con = new PropertiesConfiguration("file.properties");
		String config = con.getString("GRAPH_FILTER");
		List<String> annular = Arrays.asList(config.split("，")); 
		List<String> result = new ArrayList<String>(annular);
		result.add("流程点");
		return result;
	}

	@Override
	public List<Select> queryTableFilter(Map<String, Object> map) throws Exception {
		
		String filter = map.get("table").toString();
		String table = getPayTable()+filter;
		String code = getPayTableCodeField(filter);
		String name = getPayTableNameField(filter);
		
		return graphMapper.queryTableFilter(table, code, name);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @Author: qwm
	 * @Description: 获取代码表 支付管理表前缀
	 */
	public String getPayTable() throws Exception {
		Configuration config = new PropertiesConfiguration("file.properties");
		return config.getString("CODE_TABLE_PAY");
	}
	/**
	 * @Author: qwm
	 * @Description: 获取代码表 支付管理表  代码字段 名称
	 */
	public String getPayTableCodeField(String table) {
		return table + "代码";
	}
	/**
	 * @Author: qwm
	 * @Description: 获取代码表 支付管理表  代码名称  名称
	 */
	public String getPayTableNameField(String table) {
		return table + "名称";
	}
//	/**
//	 * @Author: qwm
//	 * @Description: 查询是否存在下级菜单
//	 */
//	public boolean existLowerLevel(String table,String code,String name,String codeValue){
//		
//		List<Select> list = graphMapper.queryLowerLevelTableFilter(table, code, name, codeValue);
//		if(list.size()>0){
//			return true;
//		}
//		return false;
//	}
	
}
