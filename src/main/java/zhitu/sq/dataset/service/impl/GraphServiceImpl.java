package zhitu.sq.dataset.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.mapper.GraphMapper;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.service.GraphService;
import zhitu.util.MyTest.RuleNode;
import zhitu.util.Neo4jTest;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;
import zhitu.vgraph.Graphs;
import zhitu.vgraph.Node;
import zhitu.vgraph.NodeTypes;
import zhitu.vgraph.TableNodeName;

@Service
@Transactional
public class GraphServiceImpl implements GraphService{

	@Autowired
	private GraphMapper graphMapper;

	@Override
	public PageInfo<Graph> queryAllGraph(Map<String,Object> map) throws Exception {
		
		map = orderMap(map);
		
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		PageHelper.orderBy(map.get("sidx") + " " + map.get("sord"));
		List<Graph> list = graphMapper.queryAllGraph(map);
		
		return new PageInfo<Graph>(list);
	}
	
	@Override
	public boolean editGraph(Graph graph) throws Exception {
		int i = graphMapper.editGraph(graph);
		return i > 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteGraph(Map<String,Object> map) throws Exception {
//		String ids = splitIds(map.get("ids").toString());
		List<String> ids = (List<String>) map.get("ids");
		int i = graphMapper.deleteGraph(ids);
		return i > 0;
	}
	
	@Override
	public List<Select> queryTableFilter(Map<String,Object> map) throws Exception {
		
		String filter = map.get("table").toString();
		String table = getPayTable()+filter;
		String code = getPayTableCodeField(filter);
		String name = getPayTableNameField(filter);
		
		return graphMapper.queryTableFilter(table, code, name);
	}
	
	@Override
	public String initProcessAnalysis() throws Exception{
		Node node1 = new Node(TableNodeName.ONE, NodeTypes.PROCESS); //表节点
		Node node2 = new Node(node1, TableNodeName.TWO, NodeTypes.PROCESS);
		Node node3 = new Node(node2, TableNodeName.THREE, NodeTypes.PROCESS);
		@SuppressWarnings("unused")
		Node node4 = new Node(node3, TableNodeName.FOUR, NodeTypes.PROCESS);
		return node1.convertTreeToJsonObject().toString();
	}
	
	@Override
	public List<String> initAnnularData() throws Exception {
		
		return getAnnularData();
	}
	
	@Override
	public Map<String,Object> addFilterNode(Map<String,Object> map) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		String id = map.get("id").toString(); //初始节点id
		String parent_id = map.get("parent_id").toString(); //父节点id
		String tableSuffix = map.get("table").toString(); //表名后缀 选中环形数据
		String code = map.get("code").toString(); // 2级菜单id new node 的 code
		String name = map.get("name").toString(); // 2级菜单名称  new node 的 name
		
		Node node = new Node(name,NodeTypes.FILTER,code,tableSuffix); //将要添加的过滤器节点
		
		Node parant = Graphs.findNodeById(parent_id); //将要添加的过滤器节点的父节点
		for (Node no : parant.children) {
			no.setParent(node);
			break;
		}
		parant.addChild(node);
		
		Node nodeSource = Graphs.findNodeById(id); //初始的表节点
		result.put("node", nodeSource.convertTreeToJsonObject().toString());
		
		return result;
		
	}
	
	@Override
	public List<String> queryFilterNodeAnnularData(Map<String,Object> map) throws Exception{
		String tableSuffix = map.get("table").toString(); //表名 当前过滤器节点的名称
		String table = getPayTable()+tableSuffix; //完整表名称
		String code = map.get("code").toString(); // 码值 当前过滤器节点的码值
		
		List<String> annularData = getAnnularData(); //返回的环形11个基本菜单
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
	public Map<String,Object> addOtherNodesLine(Map<String,Object> map) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		String id = map.get("id").toString(); //初始节点id
		String parent_id = map.get("parent_id").toString(); //父节点id
		String tableSuffix = map.get("table").toString(); //表名后缀 选中环境数据
		String code = map.get("code").toString(); // 2级菜单id new node 的 code
		String name = map.get("name").toString(); // 2级菜单名称  new node 的 name
		
		Node node = new Node(name,NodeTypes.FILTER,code,tableSuffix); //将要添加的过滤器节点
		
		Node parant = Graphs.findNodeById(parent_id); //将要添加的过滤器节点的父节点
		parant.addChild(node);
		
		if(TableNodeName.ONE.equals(parant.text)){
			Node node1 = new Node(node, TableNodeName.TWO, NodeTypes.PROCESS);
			Node node2 = new Node(node1, TableNodeName.THREE, NodeTypes.PROCESS);
			@SuppressWarnings("unused")
			Node node3 = new Node(node2, TableNodeName.FOUR, NodeTypes.PROCESS);
		}
		if(TableNodeName.TWO.equals(parant.text)){
			Node node1 = new Node(node, TableNodeName.THREE, NodeTypes.PROCESS);
			@SuppressWarnings("unused")
			Node node2 = new Node(node1, TableNodeName.FOUR, NodeTypes.PROCESS);
		}
		if(TableNodeName.THREE.equals(parant.text)){
			@SuppressWarnings("unused")
			Node node1 = new Node(node, TableNodeName.FOUR, NodeTypes.PROCESS);
		}
		
		Node nodeSource = Graphs.findNodeById(id); //初始的表节点
		result.put("node", nodeSource.convertTreeToJsonObject().toString());
		
		return result;
		
	}
	
	@Override
	public PageInfo<NodeDetail> queryNodeDetails(Map<String,Object> map) throws Exception {
		map = orderMap(map);
		
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
	public Map<String,Object> orderMap(Map<String,Object> map) throws Exception {
		if(null == map.get("page") || 
				StringUtils.isEmpty(map.get("page").toString())){
			map.put("page",1);
		}
		if(null == map.get("rows") || 
				StringUtils.isEmpty(map.get("rows").toString())){
			map.put("rows",10);
		}
		
		if(null == map.get("sidx") || 
				StringUtils.isEmpty(map.get("sidx").toString())){
			map.put("sidx","createTime");
		}
		if(null == map.get("sord") || 
				StringUtils.isEmpty(map.get("sord").toString())){
			map.put("sord","desc");
		}
		
		return map;
	}
	
	@Override
	public String splitIds(String ids) throws Exception{
		if(StringUtils.isEmpty(ids)){
			return ids;
		}
		StringBuffer sb = new StringBuffer();
		String[] split = ids.split(",");
		for (String s : split) {
			sb.append("'").append(s).append("',");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
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
	/**
	 * @Author: qwm
	 * @Description: 查询是否存在下级菜单
	 */
	public boolean existLowerLevel(String table,String code,String name,String codeValue){
		
		List<Select> list = graphMapper.queryLowerLevelTableFilter(table, code, name, codeValue);
		if(list.size()>0){
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
