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

import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.mapper.GraphMapper;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.service.GraphService;
import zhitu.util.NumberDealHandler;
import zhitu.vgraph.Graphs;
import zhitu.vgraph.Node;
import zhitu.vgraph.NodeTypes;

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
	
	@Override
	public boolean deleteGraph(Map<String,Object> map) throws Exception {
		String ids = splitIds(map.get("ids").toString());
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
		Node node1 = new Node("科处室指标", NodeTypes.PROCESS); //表节点
		Node node2 = new Node(node1, "单位指标", NodeTypes.PROCESS);
		Node node3 = new Node(node2, "计划", NodeTypes.PROCESS);
		@SuppressWarnings("unused")
		Node node4 = new Node(node3, "支付", NodeTypes.PROCESS);
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
		String tableSuffix = map.get("table").toString(); //表名后缀 选中环境数据
		String code = map.get("code").toString(); // 2级菜单id new node 的 code
		String name = map.get("name").toString(); // 2级菜单名称  new node 的 name
		
		Node nodeSource = Graphs.findNodeById(id); //初始的表节点
		Node node = new Node(name,NodeTypes.FILTER,code,tableSuffix); //将要添加的过滤器节点
		
		Node parant = Graphs.findNodeById(parent_id); //将要添加的过滤器节点的父节点
		for (Node no : parant.children) {
			no.setParent(node);
			break;
		}
		parant.addChild(node);
		
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


	
	
}
