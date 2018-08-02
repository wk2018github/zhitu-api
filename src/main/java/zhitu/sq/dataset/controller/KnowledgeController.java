package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import thredds.catalog2.Dataset;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.Knowledge;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.sq.dataset.service.KnowledgeService;
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
	
}
