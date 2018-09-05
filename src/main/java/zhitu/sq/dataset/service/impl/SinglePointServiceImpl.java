package zhitu.sq.dataset.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.SuspendDetail;
import zhitu.sq.dataset.mapper.GraphMapper;
import zhitu.sq.dataset.mapper.SinglePointMapper;
import zhitu.sq.dataset.service.GraphService;
import zhitu.sq.dataset.service.SinglePointService;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;
import zhitu.util.MyTest.RuleNode;
import zhitu.vgraph.Graphs;
import zhitu.vgraph.Node;
import zhitu.vgraph.NodeTypes;

@Service
@Transactional
public class SinglePointServiceImpl implements SinglePointService {

	@Autowired
	private SinglePointMapper singlePointMapper;
	@Autowired
	private GraphMapper graphMapper;
	@Autowired
	private GraphService graphService;

	@Override
	public String initRootSinglePoint() throws Exception {

		String rootName = singlePointMapper.queryRootSinglePoint();

		Node node = new Node(rootName, NodeTypes.PROCESS); // 表节点

		return node.convertTreeToJsonObject().toString();
	}

	@Override
	public List<String> initAnnularData() throws Exception {
		
		List<String> result = getAnnularData();
		result.add("流程点");
		return result;
	}

	@Override
	public List<Select> queryTableFilter(Map<String, Object> map) throws Exception {

		String filter = map.get("table").toString();
		if ("流程点".equals(filter)) {   ///////////////////////////////////////////
			Select s = new Select(null, "0000", "科处室指标", null);
			Select s1 = new Select(null, "0000", "单位指标", null);
			Select s2 = new Select(null, "0000", "计划", null);
			Select s3 = new Select(null, "0000", "支付", null);
			List<Select> list = new ArrayList<>();
			list.add(s);
			list.add(s1);
			list.add(s2);
			list.add(s3); ///////////////////////////////////////////
			return list;
		}
		String table = getPayTable() + filter;
		String code = getPayTableCodeField(filter);
		String name = getPayTableNameField(filter);

		return graphMapper.queryTableFilter(table, code, name);
	}

	@Override
	public Map<String, Object> addFilterNode(Map<String, Object> map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = map.get("id").toString(); // 初始节点id
		String parent_id = map.get("parent_id").toString(); // 父节点id
		String tableSuffix = map.get("table").toString(); // 表名后缀 选中环形数据
		String code = map.get("code").toString(); // 2级菜单id new node 的 code
		String name = map.get("name").toString(); // 2级菜单名称 new node 的 name

		Node node = new Node(name, NodeTypes.FILTER, code, tableSuffix); // 将要添加的过滤器节点

		Node parant = Graphs.findNodeById(parent_id); // 将要添加的过滤器节点的父节点
		for (Node no : parant.children) {
			no.setParent(node);
			break;
		}
		parant.addChild(node);

		Node nodeSource = Graphs.findNodeById(id); // 初始的表节点
		result.put("node", nodeSource.convertTreeToJsonObject().toString());

		return result;

	}
	
	@Override
	public List<String> queryFilterNodeAnnularData(Map<String,Object> map) throws Exception{
		String tableSuffix = map.get("table").toString(); //表名 当前过滤器节点的名称
		String table = getPayTable()+tableSuffix; //完整表名称
		String code = map.get("code").toString(); // 码值 当前过滤器节点的码值
		
		List<String> annularData = getAnnularData(); //返回的环形11个基本菜单
		annularData.add("流程点");////////////////////////////////////////////////////////////
		boolean flag = existLowerLevel(table, getPayTableCodeField(tableSuffix), getPayTableNameField(tableSuffix), code); // 2级菜单 是否有3级菜单 
		if(flag){
			annularData.add("下级");
		}
		
		return annularData;
	}
	
	@Override
	public List<Select> queryFilterNodeLowerLevelMenu(Map<String,Object> map) throws Exception {
		String tableSuffix = map.get("table").toString(); //表名 当前过滤器节点的名称
		String table = getPayTable()+tableSuffix; //表名 选中环境数据
		String code = map.get("code").toString(); // 2级菜单id new node 的 code
		
		return graphMapper.queryLowerLevelTableFilter(table, getPayTableCodeField(tableSuffix), getPayTableNameField(tableSuffix), code);
		
	}
	
	@Override
	public PageInfo<NodeDetail> queryNodeDetails(Map<String,Object> map) throws Exception {
		map = graphService.orderMap(map);
		
		List<NodeDetail> nodeDetail = new ArrayList<NodeDetail>();
		String id = StringHandler.objectToString(map.get("id"));
		String cypher = "";
		Node node = Graphs.findNodeById(id);
		RuleNode ruleNode = new RuleNode(); // 做一个构造函数，直接把流程节点传进去
		ruleNode.id = node.id;
		ruleNode.addLabel(node.text);
		ruleNode = getFirstRuleNode(ruleNode, node);
		
		for (String s : ruleNode.createCypherList(1)) {
			cypher = s;
			System.out.println(s);
		}
//		nodeDetail = Neo4jTest.queryNodeDetails(cypher);
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		List<NodeDetail> list = graphMapper.queryNodeDetails(map);
		
		return new PageInfo<NodeDetail>(list);
		
//		return nodeDetail;
		
	}
	
	@Override
	public Map<String, Object> addProcessNode(Map<String, Object> map) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = map.get("id").toString(); // 初始节点id
		String parent_id = map.get("parent_id").toString(); // 父节点id
		String tableSuffix = map.get("table").toString(); // 表名后缀 选中环形数据
		String code = map.get("code").toString(); // 2级菜单id new node 的 code
		String name = map.get("name").toString(); // 2级菜单名称 new node 的 name

		Node node = new Node(name, NodeTypes.PROCESS, code, tableSuffix); // 将要添加的过滤器节点

		Node parant = Graphs.findNodeById(parent_id); // 将要添加的过滤器节点的父节点
		for (Node no : parant.children) {
			no.setParent(node);
			break;
		}
		parant.addChild(node);

		Node nodeSource = Graphs.findNodeById(id); // 初始的表节点
		result.put("node", nodeSource.convertTreeToJsonObject().toString());

		return result;

	}
	
	@Override
	public List<SuspendDetail> querySuspendDetails(Map<String,Object> map) throws Exception {
//		List<SuspendDetail> list = new ArrayList<>();
		
//		String id = StringHandler.objectToString(map.get("id"));
//		Node node = Graphs.findNodeById(id);

		return singlePointMapper.queryProcessNodeText();
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
	 * @Description: 获取代码表 支付管理表 代码字段 名称
	 */
	public String getPayTableCodeField(String table) {
		return table + "代码";
	}

	/**
	 * @Author: qwm
	 * @Description: 获取代码表 支付管理表 代码名称 名称
	 */
	public String getPayTableNameField(String table) {
		return table + "名称";
	}
	/**
	 * @Author: qwm
	 * @Description: 获取11个环形数据
	 */
	public List<String> getAnnularData() throws Exception {
		List<String> annular = new ArrayList<>();
		
		Configuration con = new PropertiesConfiguration("file.properties");
		String config = con.getString("GRAPH_FILTER");
		
		annular = Arrays.asList(config.split("，")); 
		return new ArrayList<String>(annular);
	}

	/**
	 * @Author: qwm
	 * @Description: 查询是否存在下级菜单
	 */
	public boolean existLowerLevel(String table, String code, String name, String codeValue) {

		List<Select> list = graphMapper.queryLowerLevelTableFilter(table, code, name, codeValue);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}
	/**
	 * @Author: qwm
	 * @Description: 递归取得根节点node
	 */
	public RuleNode getFirstRuleNode(RuleNode ruleNode, Node node){
		if(null != node.parent){
			
			Node p = node.parent;
			RuleNode pNode = new RuleNode(); // 做一个构造函数，直接把流程节点传进去
			pNode.id = p.id;
			pNode.addLabel(p.text);
			pNode.filterMap.put(p.text, node.text);
			ruleNode.addParent(pNode, "");
			
			getFirstRuleNode(pNode, p);
		}
    	return ruleNode;
    }

}
