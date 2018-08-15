package zhitu.sq.dataset.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import thredds.catalog2.Dataset;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.controller.vo.KnowledgeVo;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.FtpFile;
import zhitu.sq.dataset.model.Knowledge;
import zhitu.sq.dataset.model.User;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.sq.dataset.service.KnowledgeService;
import zhitu.util.Neo4jTest;
import zhitu.util.StringHandler;

@RequestMapping("knowledge")
@RestController
@CrossOrigin
public class KnowledgeController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(KnowledgeController.class);
	
	
	@Autowired
	private KnowledgeService knowledgeService;
	@Autowired
	private DataSetService dataSetService;
	
	private static final String user = "{\"page\":1,\"rows\":10,\"name\":\"sss\"}";
	@ApiOperation(value = "进入知识库页面", notes = "进入知识库页面")
	@ResponseBody
	@RequestMapping(value = "/queryKnowledge", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> queryKnowledge(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			PageInfo<Map<String, Object>> kInfo = knowledgeService.queryKnowledge(map);
			result = mergeJqGridData(kInfo);
			return success("查询成功", result);
		} catch (Exception e) {
			logger.error("knowledge/queryKnowledge",e);
			return error("查询异常");
		}
	}
	
	@ApiOperation(value = "修改知识库信息", notes = "修改知识库信息")
	@ResponseBody
	@RequestMapping(value = "/updateKnowledge", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> updateKnowledge(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Knowledge knowledge) {
		try {
			int i  = knowledgeService.updateKnowledge(knowledge);
			if(i>0){
				return success();
			}
			return error("保存失败");
		} catch (Exception e) {
			logger.error("knowledge/queryKnowledge",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "删除知识库信息", notes = "删除知识库信息")
	@ResponseBody
	@RequestMapping(value = "/deleteKnowledge", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> deleteKnowledge(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			List<String> ids = (List<String>) map.get("ids");
			int i  = knowledgeService.deleteKnowledge(ids);
			if(i>0){
				return success();
			}
			return error("保存失败");
		} catch (Exception e) {
			logger.error("knowledge/deleteKnowledge",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "保存结构化知识库", notes = "保存结构化知识库")
	@ResponseBody
	@RequestMapping(value = "/saveKnowledge", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> saveKnowledge(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Knowledge knowledge) {
		try {
			int i  = knowledgeService.saveKnowledge(knowledge);
			if(i>0){
				return success();
			}
			return error("保存失败");
		} catch (Exception e) {
			logger.error("knowledge/deleteKnowledge",e);
			return error("保存异常");
		}
	}
	private final String parm = "{\"datasetId\": \"DATASET_1533546327728\",\"key\":\"01\",\n"
			+ "\"description\": \"测试描述\", \"name\": \"测试\",\"audit\"\n"
			+ ":[\"activity\",\"underlying\",\"enforcement\",\"opinion\",\"problem\",\"advice\"]}";
	@ApiOperation(value = "非结构化点击知识提取", notes = "非结构化点击知识提取")
	@ResponseBody
	@RequestMapping(value = "/extractKnowledge", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> extractKnowledge(HttpServletRequest request,
			@ApiParam(value = parm) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<>();
			User user = (User)request.getSession().getAttribute("user");
        	//获取登录用户Id
        /*	if(user==null){
//        		return error("请重新登录!");
        		user.setId("USER_1353923423");
        	}*/
    		String userId = "USER_1353923423";
 			String taskId  = knowledgeService.extractKnowledge(map,userId);
 			result.put("taskId", taskId);
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/extractKnowledge",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "根据知识库的数据集id查询详情", notes = "根据知识库的数据集id查询详情")
	@ResponseBody
	@RequestMapping(value = "/selectByDatasetId", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> selectByDatasetId(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			String datasetId = StringHandler.objectToString(map.get("datasetId"));
			DataSet dataSet = dataSetService.selectById(datasetId);
			if(dataSet.getTypeId().equals("ftp_file")){
				map.put("id", dataSet.getId());
				PageInfo<Map<String, Object>> data = dataSetService.findByIdFtpFile(map);
            	result = mergeJqGridData(data);
			}else if (dataSet.getTypeId().equals("local_rdb")) {
				map.put("dataTable",dataSet.getDataTable());
				PageInfo<Map<String, Object>> data = dataSetService.findByIdLcoal(map);
            	result = mergeJqGridData(data);
			}else {
				map.put("rdbId",dataSet.getRdbId());
				Map<String, Object> data = dataSetService.findById(map);
            	result = data;
			}
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/selectByDatasetId",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "根据选择知识库查询表-关系配置", notes = "根据选择知识库查询表-关系配置")
	@ResponseBody
	@RequestMapping(value = "/selectByDataSetId", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> selectByDataSetId(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<>();
			List<String> ids = (List<String>) map.get("ids");
			if(ids.size()==0){
				return error("查詢失敗");
			}
			List<Map<String, Object>> list = knowledgeService.selectByDataSetId(ids);
			result.put("data", list);
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/deleteKnowledge",e);
			return error("查詢失敗");
		}
	}
	
	
	
	
	/**
	 * 
	 * @Author: qwm
	 * @Description:
	 * @return: SQApiResponse<List<Select>>      
	 * @throws
	 */
	@ApiOperation(value = "知识库-关系配置页面-所有知识表下拉选项", notes = "知识库-关系配置页面-所有知识表下拉选项")
	@ResponseBody
	@RequestMapping(value = "/queryKnowledgeSelect", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryKnowledgeSelect(HttpServletRequest request) {
		try {
			List<Select> result = new ArrayList<Select>();
			result = knowledgeService.queryKnowledge();
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/queryKnowledgeSelect", e);
			return error("查询异常");
		}
	}
	
	private static final String knId = "{\"knowledgeId\":\"KN_1483797784\"}";
	/**
	 * 
	 * @Author: qwm
	 * @Description:
	 * @return: SQApiResponse<List<Select>>      
	 * @throws
	 */
	@ApiOperation(value = "知识库-关系配置页面-外键下拉选项", notes = "知识库-关系配置页面-外键下拉选项")
	@ResponseBody
	@RequestMapping(value = "/queryForeignKey", method = RequestMethod.POST)
	public SQApiResponse<List<String>> queryForeignKey(HttpServletRequest request,
			@ApiParam(value = knId) @RequestBody Map<String, Object> map) {
		try {
			List<String> result = new ArrayList<String>();
			result = knowledgeService.queryForeignKey(map);
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/queryForeignKey", e);
			return error("查询异常");
		}
	}
	
	private static final String toMap = "{\"name\":\"图谱名称\",\"description\":\"图谱描述\","
			+ "\"sourceId\":\"KN_1483797784\",\"targetId\":\"KN_1483797784\","
			+ "\"relationship\":\"工作\",\"sourceKey\":\"KN_1483797784\",\"targetKey\":\"KN_1483797784\"}";
	//\"sourceTable\":\"KN_1483797784\",\"targetTable\":\"KN_1483797784\",
	/**
	 * 
	 * @Author: qwm
	 * @Description:
	 * @return: SQApiResponse<List<Select>>      
	 * @throws
	 */
	@ApiOperation(value = "知识库-关系配置页面-添加关系至图谱", notes = "知识库-关系配置页面-添加关系至图谱")
	@ResponseBody
	@RequestMapping(value = "/addToMap", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> addToMap(HttpServletRequest request,
			@ApiParam(value = toMap) @RequestBody Map<String, Object> map) {
		try {
			boolean flag = false;
			
			flag = knowledgeService.addToMap(map);
			if(flag){
				return success("添加成功",null);
			} else {
				return success("操作成功,但是数据没变",null);
			}
		} catch (Exception e) {
			logger.error("knowledge/addToMap", e);
			return error("添加异常");
		}
	}
	/**
	 * 
	 * @Author: qwm
	 * @Description:
	 * @return: SQApiResponse<List<Select>>      
	 * @throws
	 */
	@ApiOperation(value = "知识库-关系配置页面-节点标签", notes = "知识库-关系配置页面-节点标签")
	@ResponseBody
	@RequestMapping(value = "/queryNodes", method = RequestMethod.POST)
	public SQApiResponse<List<String>> queryNodes(HttpServletRequest request) {
		try {
			List<String> result = new ArrayList<String>();
			result = Neo4jTest.getLabels();
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/queryNodes", e);
			return error("查询异常");
		}
	}
	/**
	 * 
	 * @Author: qwm
	 * @Description:
	 * @return: SQApiResponse<List<Select>>      
	 * @throws
	 */
	@ApiOperation(value = "知识库-关系配置页面-关系类型", notes = "知识库-关系配置页面-关系类型")
	@ResponseBody
	@RequestMapping(value = "/queryTypes", method = RequestMethod.POST)
	public SQApiResponse<List<String>> queryTypes(HttpServletRequest request) {
		try {
			List<String> result = new ArrayList<String>();
			result = Neo4jTest.getTypes();
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/queryTypes", e);
			return error("查询异常");
		}
	}

	private static final String node = "{\"node\":\"sss\"}";
	/**
	 * 
	 * @Author: qwm
	 * @Description:
	 * @return: SQApiResponse<List<Select>>      
	 * @throws
	 */
	@ApiOperation(value = "知识库-关系配置页面-删除节点标签", notes = "知识库-关系配置页面-删除节点标签")
	@ResponseBody
	@RequestMapping(value = "/deleteNodes", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> deleteNodes(HttpServletRequest request,
			@ApiParam(value = node) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			String node = StringHandler.objectToString(map.get("node"));

			if (!Neo4jTest.delLabels(node)) {
				result.put("data", "删除失败");
			}
			result.put("data", "删除成功");
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/deleteNodes", e);
			return error("删除异常");
		}
	}

	private static final String type = "{\"type\":\"sss\"}";
	/**
	 * 
	 * @Author: qwm
	 * @Description:
	 * @return: SQApiResponse<List<Select>>      
	 * @throws
	 */
	@ApiOperation(value = "知识库-关系配置页面-删除关系类型", notes = "知识库-关系配置页面-删除关系类型")
	@ResponseBody
	@RequestMapping(value = "/deleteTypes", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> deleteTypes(HttpServletRequest request,
			@ApiParam(value = type) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			String type = StringHandler.objectToString(map.get("type"));
			
			if (!Neo4jTest.delTypes(type)) {
				result.put("data", "删除失败");
			}
			result.put("data", "删除成功");
			return success(result);
		} catch (Exception e) {
			logger.error("knowledge/deleteTypes", e);
			return error("删除异常");
		}
	}
	
	
}
