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
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.service.GraphService;
import zhitu.sq.dataset.service.SinglePointService;

@RequestMapping("singlePoint")
@RestController
@CrossOrigin
public class SinglePointController extends BaseController {

	private static final Logger logger = Logger.getLogger(SinglePointController.class);

	@Autowired
	private SinglePointService singlePointService;

	@ApiOperation(value = "1.1单点分析-初始化根节点", notes = "1.1单点分析-初始化根节点")
	@ResponseBody
	@RequestMapping(value = "/initRootSinglePoint", method = RequestMethod.POST)
	public SQApiResponse<String> initRootSinglePoint(HttpServletRequest request) {
		try {

			return success("操作成功", singlePointService.initRootSinglePoint());
		} catch (Exception e) {
			logger.error("singlePoint/initRootSinglePoint", e);
			return error("初始化数据异常");
		}
	}

	@ApiOperation(value = "1.2单点分析-流程节点环形菜单", notes = "1.2单点分析-流程节点环形菜单")
	@ResponseBody
	@RequestMapping(value = "/initAnnularData", method = RequestMethod.POST)
	public SQApiResponse<List<String>> initAnnularData(HttpServletRequest request) {
		try {

			return success("操作成功", singlePointService.initAnnularData());
		} catch (Exception e) {
			logger.error("singlePoint/initAnnularData", e);
			return error("菜单初始化异常");
		}
	}

	private static final String idAndTable = "{\"id\":\"N_1534399392833\",\"table\":\"功能科目\"}";

	@ApiOperation(value = "1.3单点分析-查询流程节点的过滤器中的具体的值", notes = "1.3单点分析-查询流程节点的过滤器中的具体的值")
	@ResponseBody
	@RequestMapping(value = "/queryTableFilter", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryTableFilter(HttpServletRequest request,
			@ApiParam(value = idAndTable) @RequestBody Map<String, Object> map) {
		try {

			return success("查询成功", singlePointService.queryTableFilter(map));

		} catch (Exception e) {
			logger.error("singlePoint/queryTableFilter", e);
			return error("查询异常");
		}
	}

	private static final String addFilterNode = "{\"id\":\"N_1534399392833\",\"parent_id\":\"N_1534399392833\",\"table\":\"功能科目\",\"code\":\"201\",\"name\":\"人事教育\"}";

	@ApiOperation(value = "1.4图谱分析-单点分析-添加过滤器节点", notes = "1.4图谱分析-单点分析-添加过滤器节点")
	@ResponseBody
	@RequestMapping(value = "/addFilterNode", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> addFilterNode(HttpServletRequest request,
			@ApiParam(value = addFilterNode) @RequestBody Map<String, Object> map) {
		try {

			return success("添加成功", singlePointService.addFilterNode(map));

		} catch (Exception e) {
			logger.error("singlePoint/addFilterNode", e);
			return error("添加异常");
		}
	}

	private static final String queryFilterNodeAnnular = "{\"table\":\"功能科目\",\"code\":\"201\"}";

	@ApiOperation(value = "1.5图谱分析-单点分析-过滤器节点的环形菜单的数据", notes = "1.5图谱分析-单点分析-过滤器节点的环形菜单的数据")
	@ResponseBody
	@RequestMapping(value = "/queryFilterNodeAnnularData", method = RequestMethod.POST)
	public SQApiResponse<List<String>> queryFilterNodeAnnularData(HttpServletRequest request,
			@ApiParam(value = queryFilterNodeAnnular) @RequestBody Map<String, Object> map) {
		try {

			return success("查询成功", singlePointService.queryFilterNodeAnnularData(map));

		} catch (Exception e) {
			logger.error("singlePoint/queryFilterNodeAnnularData", e);
			return error("查询异常");
		}
	}

	private static final String queryFilterNodeLowerLevelMenu = "{\"table\":\"功能科目\",\"code\":\"201\"}";

	@ApiOperation(value = "1.6图谱分析-单点分析-查询过滤器节点下级菜单的值", notes = "1.6图谱分析-单点分析-查询过滤器节点下级菜单的值")
	@ResponseBody
	@RequestMapping(value = "/queryFilterNodeLowerLevelMenu", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryFilterNodeLowerLevelMenu(HttpServletRequest request,
			@ApiParam(value = queryFilterNodeLowerLevelMenu) @RequestBody Map<String, Object> map) {
		try {

			return success("查询成功", singlePointService.queryFilterNodeLowerLevelMenu(map));

		} catch (Exception e) {
			logger.error("singlePoint/queryFilterNodeLowerLevelMenu", e);
			return error("查询异常");
		}
	}

	private static final String idAndTableAndFilterName = "{\"id\":\"N_1534399392833\",\"table\":\"结算方式\",\"code\":\"2\"}";

	@ApiOperation(value = "1.7图谱分析-单点分析-查询过滤器节点的过滤器中的具体的值", notes = "图谱分析-单点分析-查询过滤器节点的过滤器中的具体的值")
	@ResponseBody
	@RequestMapping(value = "/queryNodesFilterAnnularData", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryNodesFilterAnnularData(HttpServletRequest request,
			@ApiParam(value = idAndTableAndFilterName) @RequestBody Map<String, Object> map) {
		try {

			return success("查询成功", singlePointService.queryTableFilter(map));

		} catch (Exception e) {
			logger.error("singlePoint/queryTableFilter", e);
			return error("查询异常");
		}
	}


	private static final String nodeId = "{\"id\":\"GRAPH_12341441\",\"page\":\"1\",\"rows\":\"10\"}";
	@ApiOperation(value = "1.8图谱分析-单点分析-点击节点上的查看详情", notes = "1.8图谱分析-单点分析-点击节点上的查看详情")
	@ResponseBody
	@RequestMapping(value = "/queryNodeDetails", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> queryNodeDetails(HttpServletRequest request,
			@ApiParam(value = nodeId) @RequestBody Map<String,Object> map ) {
		try {
			
			Map<String, Object> result = new HashMap<String, Object>();
			
			PageInfo<NodeDetail> list = singlePointService.queryNodeDetails(map);
			result = mergeJqGridData(list);
			return success(result);
			
		} catch (Exception e) {
			logger.error("singlePoint/queryNodeDetails",e);
			return error("查询异常");
		}
	}
	
	@ApiOperation(value = "1.9图谱分析-单点分析-添加流程节点", notes = "1.9图谱分析-单点分析-添加流程节点")
	@ResponseBody
	@RequestMapping(value = "/addProcessNode", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> addProcessNode(HttpServletRequest request,
			@ApiParam(value = addFilterNode) @RequestBody Map<String, Object> map) {
		try {

			return success("添加成功", singlePointService.addProcessNode(map));

		} catch (Exception e) {
			logger.error("singlePoint/addProcessNode", e);
			return error("添加异常");
		}
	}

}
