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
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.Graph;
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
	@ApiOperation(value = "图谱分析-流程分析-查询过滤器中的具体的值", notes = "图谱分析-流程分析-查询过滤器中的具体的值")
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
	
	private static final String addFilterNode = "{\"id\":\"N_1534399392833\",\"table\":\"功能科目\",\"code\":\"201\",\"name\":\"人事教育\"}";
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
	
	

}
