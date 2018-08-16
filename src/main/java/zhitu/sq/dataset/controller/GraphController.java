package zhitu.sq.dataset.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.graph.tree.NodeTest;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.service.GraphService;
import zhitu.vgraph.Node;
import zhitu.vgraph.NodeTypes;

@RequestMapping("graph")
@RestController
@CrossOrigin
public class GraphController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(GraphController.class);
	
	
	@Autowired
	private GraphService graphService;
	
	private static final String queryAllGraph = "{\"name\":\"测试\",\"page\":\"1\",\"rows\":\"10\",\"sidx\":\"createTime\",\"sord\":\"desc\"}";
	@ApiOperation(value = "查询所有图谱列表,按照名称查询", notes = "查询所有图谱列表,按照名称查询")
	@ResponseBody
	@RequestMapping(value = "/queryAllGraph", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> queryAllGraph(HttpServletRequest request,
			@ApiParam(value = queryAllGraph) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
//			User user = (User)request.getSession().getAttribute("user");
//			if(null == user){
//				return error("请登录");
//			}
			
			PageInfo<Graph> list = graphService.queryAllGraph(map);
			result = mergeJqGridData(list);
			return success(result);
		} catch (Exception e) {
			logger.error("graph/queryAllGraph",e);
			return error("查询异常");
		}
	}
	
	private static final String edit = "{\"name\":\"测试\",\"description\":\"描述\"}";
	@ApiOperation(value = "首页图谱分析-行内编辑按钮", notes = "首页图谱分析-行内编辑按钮")
	@ResponseBody
	@RequestMapping(value = "/editGraph", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> editGraph(HttpServletRequest request,
			@ApiParam(value = edit) @ModelAttribute Graph graph) {
		try {
			
			boolean flag = graphService.editGraph(graph);
			if(flag){
				return success("编辑成功",null);
			} else {
				return success("操作成功,数据没变",null);
			}
		} catch (Exception e) {
			logger.error("graph/edit",e);
			return error("编辑异常");
		}
	}
	
	private static final String ids = "{\"ids\":\"GRAPH_12341441,GRAPH_12549942\"}";
	@ApiOperation(value = "首页图谱分析-刪除圖譜", notes = "首页图谱分析-刪除圖譜")
	@ResponseBody
	@RequestMapping(value = "/deleteGraph", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> deleteGraph(HttpServletRequest request,
			@ApiParam(value = ids) @RequestBody Map<String,Object> map) {
		try {
			
			boolean flag = graphService.deleteGraph(map);
			if(flag){
				return success("删除成功",null);
			} else {
				return success("操作成功,数据没变",null);
			}
		} catch (Exception e) {
			logger.error("graph/deleteGraph",e);
			return error("删除异常");
		}
	}
	
	
	
	@ApiOperation(value = "图谱分析-流程分析-加载四个初始节点", notes = "首页图谱分析-刪除圖譜-加载四个初始节点")
	@ResponseBody
	@RequestMapping(value = "/initProcessAnalysis", method = RequestMethod.POST)
	public SQApiResponse<String> initProcessAnalysis(HttpServletRequest request) {
		try {
//			Map<String,Object> result = new HashMap<String,Object>();
			Node node1 = new Node("科处室指标", NodeTypes.PROCESS);
			Node node2 = new Node(node1, "单位指标", NodeTypes.PROCESS);
			Node node3 = new Node(node2, "计划", NodeTypes.PROCESS);
			@SuppressWarnings("unused")
			Node node4 = new Node(node3, "支付", NodeTypes.PROCESS);
			
//			result.put("initData", node1.convertTreeToJsonObject().toString());
			
			return success("操作成功",node1.convertTreeToJsonObject().toString());
		} catch (Exception e) {
			logger.error("graph/initProcessAnalysis",e);
			return error("初始化数据异常");
		}
	}
	

	@ApiOperation(value = "图谱分析-流程分析-初始4个节点环形菜单", notes = "图谱分析-流程分析-初始4个节点环形菜单")
	@ResponseBody
	@RequestMapping(value = "/initAnnularData", method = RequestMethod.POST)
	public SQApiResponse<List<String>> initAnnularData(HttpServletRequest request) {
		try {
			List<String> annular = new ArrayList<>();
			
			Configuration con = new PropertiesConfiguration("file.properties");
			String config = con.getString("GRAPH_FILTER");
			
			annular = Arrays.asList(config.split("，")); 
			return success("操作成功",annular);
			
		} catch (Exception e) {
			logger.error("graph/initAnnularData",e);
			return error("菜单初始化异常");
		}
	}
	
	private static final String idAndTable = "{\"id\":\"N_1534399392833\",\"table\":\"功能科目\"}";
	@ApiOperation(value = "图谱分析-流程分析-查询过滤器中的具体的值", notes = "图谱分析-流程分析-查询过滤器中的具体的值")
	@ResponseBody
	@RequestMapping(value = "/queryTableFilter", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryTableFilter(HttpServletRequest request,
			@ApiParam(value = idAndTable) @RequestBody Map<String,Object> map ) {
		try {
			
			List<Select> list = graphService.queryTableFilter(map);
			
			return success("查询成功",list);
			
		} catch (Exception e) {
			logger.error("graph/queryTableFilter",e);
			return error("查询异常");
		}
	}
	
	private static final String addNode = "{\"id\":\"N_1534399392833\",\"table\":\"功能科目\"}";
	@ApiOperation(value = "图谱分析-流程分析-查询过滤器中的具体的值", notes = "图谱分析-流程分析-查询过滤器中的具体的值")
	@ResponseBody
	@RequestMapping(value = "/addNode", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> addNode(HttpServletRequest request,
			@ApiParam(value = idAndTable) @RequestBody Map<String,Object> map ) {
		try {
			
			List<Select> list = graphService.queryTableFilter(map);
			
			return success("查询成功",list);
			
		} catch (Exception e) {
			logger.error("graph/queryTableFilter",e);
			return error("查询异常");
		}
	}
	
	

}
