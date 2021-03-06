package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.SuspendDetail;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.model.ProcessGraph;
import zhitu.sq.dataset.service.GraphService;

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
	
	private static final String id = "{\"id\":\"GRAPH_12341441\"}";
	@ApiOperation(value = "首页图谱分析-更新图谱按钮", notes = "首页图谱分析-更新图谱按钮")
	@ResponseBody
	@RequestMapping(value = "/updateGraph", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> updateGraph(HttpServletRequest request,
			@ApiParam(value = id) @RequestBody Map<String,Object> map) {
		try {
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("graph", graphService.updateGraph(map));
			return success("查询成功",result);
			
		} catch (Exception e) {
			logger.error("graph/updateGraph",e);
			return error("更新异常");
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
	
	
	
	@ApiOperation(value = "图谱分析-流程分析-加载四个初始节点表节点", notes = "首页图谱分析-刪除圖譜-加载四个初始节点表节点")
	@ResponseBody
	@RequestMapping(value = "/initProcessAnalysis", method = RequestMethod.POST)
	public SQApiResponse<String> initProcessAnalysis(HttpServletRequest request) {
		try {
			
			return success("操作成功", graphService.initProcessAnalysis());
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
			
			return success("操作成功",graphService.initAnnularData());
		} catch (Exception e) {
			logger.error("graph/initAnnularData",e);
			return error("菜单初始化异常");
		}
	}
	
	private static final String idAndTable = "{\"id\":\"N_1534399392833\",\"table\":\"功能科目\"}";
	@ApiOperation(value = "图谱分析-流程分析-查询表节点的过滤器中的具体的值", notes = "图谱分析-流程分析-查询表节点的过滤器中的具体的值")
	@ResponseBody
	@RequestMapping(value = "/queryTableFilter", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryTableFilter(HttpServletRequest request,
			@ApiParam(value = idAndTable) @RequestBody Map<String,Object> map ) {
		try {
			
			return success("查询成功",graphService.queryTableFilter(map));
			
		} catch (Exception e) {
			logger.error("graph/queryTableFilter",e);
			return error("查询异常");
		}
	}
	
	private static final String addFilterNode = "{\"id\":\"N_1534399392833\",\"parent_id\":\"N_1534399392833\",\"table\":\"功能科目\",\"code\":\"201\",\"name\":\"人事教育\"}";
	@ApiOperation(value = "图谱分析-流程分析-添加过滤器节点", notes = "图谱分析-流程分析-添加过滤器节点")
	@ResponseBody
	@RequestMapping(value = "/addFilterNode", method = RequestMethod.POST)
	public SQApiResponse<Map<String,Object>> addFilterNode(HttpServletRequest request,
			@ApiParam(value = addFilterNode) @RequestBody Map<String,Object> map ) {
		try {
			
			return success("添加成功",graphService.addFilterNode(map));
			
		} catch (Exception e) {
			logger.error("graph/addFilterNode",e);
			return error("添加异常");
		}
	}
	
	private static final String queryFilterNodeAnnular = "{\"table\":\"功能科目\",\"code\":\"201\"}";
	@ApiOperation(value = "图谱分析-流程分析-过滤器节点的环形菜单的数据", notes = "图谱分析-流程分析-过滤器节点的环形菜单的数据")
	@ResponseBody
	@RequestMapping(value = "/queryFilterNodeAnnularData", method = RequestMethod.POST)
	public SQApiResponse<List<String>> queryFilterNodeAnnularData(HttpServletRequest request,
			@ApiParam(value = queryFilterNodeAnnular) @RequestBody Map<String,Object> map ) {
		try {
			
			return success("查询成功",graphService.queryFilterNodeAnnularData(map));
			
		} catch (Exception e) {
			logger.error("graph/queryFilterNodeAnnularData",e);
			return error("查询异常");
		}
	}
	
	private static final String queryFilterNodeLowerLevelMenu = "{\"table\":\"功能科目\",\"code\":\"201\"}";
	@ApiOperation(value = "图谱分析-流程分析-查询过滤器节点下级菜单的值", notes = "图谱分析-流程分析-查询过滤器节点下级菜单的值")
	@ResponseBody
	@RequestMapping(value = "/queryFilterNodeLowerLevelMenu", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryFilterNodeLowerLevelMenu(HttpServletRequest request,
			@ApiParam(value = queryFilterNodeLowerLevelMenu) @RequestBody Map<String,Object> map ) {
		try {
			
			return success("查询成功",graphService.queryFilterNodeLowerLevelMenu(map));
			
		} catch (Exception e) {
			logger.error("graph/queryFilterNodeLowerLevelMenu",e);
			return error("查询异常");
		}
	}
	
	private static final String idAndTableAndFilterName = "{\"id\":\"N_1534399392833\",\"table\":\"结算方式\",\"code\":\"2\"}";
	@ApiOperation(value = "图谱分析-流程分析-查询过滤器节点的过滤器中的具体的值", notes = "图谱分析-流程分析-查询过滤器节点的过滤器中的具体的值")
	@ResponseBody
	@RequestMapping(value = "/queryNodesFilterAnnularData", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryNodesFilterAnnularData(HttpServletRequest request,
			@ApiParam(value = idAndTableAndFilterName) @RequestBody Map<String,Object> map ) {
		try {
			
			return success("查询成功",graphService.queryTableFilter(map));
			
		} catch (Exception e) {
			logger.error("graph/queryTableFilter",e);
			return error("查询异常");
		}
	}
	
	@ApiOperation(value = "图谱分析-流程分析-添加其他表节点线", notes = "图谱分析-流程分析-添加其他表节点线")
	@ResponseBody
	@RequestMapping(value = "/addOtherNodesLine", method = RequestMethod.POST)
	public SQApiResponse<Map<String,Object>> addOtherNodesLine(HttpServletRequest request,
			@ApiParam(value = addFilterNode) @RequestBody Map<String,Object> map ) {
		try {
			
			return success("添加成功",graphService.addOtherNodesLine(map));
			
		} catch (Exception e) {
			logger.error("graph/addOtherNodesLine",e);
			return error("添加异常");
		}
	}
	
	private static final String nodeIdPage = "{\"id\":\"GRAPH_12341441\",\"year\":\"2017\",\"page\":\"1\",\"rows\":\"10\"}";
	@ApiOperation(value = "图谱分析-流程分析-点击节点上的查看详情", notes = "图谱分析-流程分析-点击节点上的查看详情")
	@ResponseBody
	@RequestMapping(value = "/queryNodeDetails", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> queryNodeDetails(HttpServletRequest request,
			@ApiParam(value = nodeIdPage) @RequestBody Map<String,Object> map ) {
		try {
			
			Map<String, Object> result = new HashMap<String, Object>();
			
			PageInfo<NodeDetail> list = graphService.queryNodeDetails(map);
			result = mergeJqGridData(list);
			return success(result);
			
		} catch (Exception e) {
			logger.error("graph/queryNodeDetails",e);
			return error("查询异常");
		}
	}
	
	private static final String nodeId = "{\"id\":\"GRAPH_12341441\"}";
	@ApiOperation(value = "图谱分析-流程分析-鼠标悬浮节点显示信息", notes = "图谱分析-流程分析-鼠标悬浮节点显示信息")
	@ResponseBody
	@RequestMapping(value = "/querySuspendDetails", method = RequestMethod.POST)
	public SQApiResponse<List<SuspendDetail>> querySuspendDetails(HttpServletRequest request,
			@ApiParam(value = nodeId) @RequestBody Map<String,Object> map ) {
		try {
			
			return success(graphService.querySuspendDetails(map));
			
		} catch (Exception e) {
			logger.error("graph/querySuspendDetails",e);
			return error("查询异常");
		}
	}
	
	private static final String nodeIdAndSourceId = "{\"initId\":\"GRAPH_12341441\",\"id\":\"GRAPH_12341441\"}";
	@ApiOperation(value = "图谱分析-删除当前节点", notes = "图谱分析-流程分析-删除当前节点")
	@ResponseBody
	@RequestMapping(value = "/deleteThisNode", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> deleteThisNode(HttpServletRequest request,
			@ApiParam(value = nodeIdAndSourceId) @RequestBody Map<String,Object> map ) {
		try {
			
			return success(graphService.deleteThisNode(map));
			
		} catch (Exception e) {
			logger.error("graph/deleteThisNode",e);
			return error("删除异常");
		}
	}
	
	private static final String saveGraph = "{\"year\":\"2017\",\"name\":\"测试流程图\",\"json\":\"json\",\"graphId\":\"json\"}";
	@ApiOperation(value = "9.1图谱分析-流程分析-保存当前流程图", notes = "9.1图谱分析-流程分析-保存当前流程图")
	@ResponseBody
	@RequestMapping(value = "/saveProcessGraph", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> saveProcessGraph(HttpServletRequest request,
			@ApiParam(value = saveGraph) @RequestBody Map<String,Object> map ) {
		try {
			
			return success(graphService.saveProcessGraph(map));
			
		} catch (Exception e) {
			logger.error("graph/saveProcessGraph",e);
			return error("保存异常");
		}
	}
	
	private static final String queryProcessGraph = "{\"page\":\"1\",\"rows\":\"10\"}";
	@ApiOperation(value = "9.2图谱分析-流程分析-流程图列表", notes = "9.2图谱分析-流程分析-流程图列表")
	@ResponseBody
	@RequestMapping(value = "/queryProcessGraph", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> queryProcessGraph(HttpServletRequest request,
			@ApiParam(value = queryProcessGraph) @RequestBody Map<String,Object> map) {
		try {
			
			Map<String, Object> result = new HashMap<String, Object>();
			
			List<ProcessGraph> list = graphService.queryProcessGraph(map);
			result.put("list", list);
			return success(result);
			
		} catch (Exception e) {
			logger.error("graph/queryProcessGraph",e);
			return error("查询异常");
		}
	}
	
	private static final String deleteProcessGraph = "{\"id\":\"1,2\"}";
	@ApiOperation(value = "9.3图谱分析-流程分析-删除流程图列表", notes = "9.3图谱分析-流程分析-删除流程图列表")
	@ResponseBody
	@RequestMapping(value = "/deleteProcessGraph", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> deleteProcessGraph(HttpServletRequest request,
			@ApiParam(value = deleteProcessGraph) @RequestBody Map<String,Object> map) {
		try {
			
			return success(graphService.deleteProcessGraph(map));
			
		} catch (Exception e) {
			logger.error("graph/deleteProcessGraph",e);
			return error("删除异常");
		}
	}
	

}
