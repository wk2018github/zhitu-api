package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.model.Dict;
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.service.DictService;
import zhitu.sq.dataset.service.TaskInfoService;
import zhitu.util.StringHandler;

@RequestMapping("dict")
@RestController
@CrossOrigin
public class DictController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(DictController.class);
	
	
	@Autowired
	private DictService dictService;
	
	private static final String user = "{\"page\":1,\"rows\":10}";
	@ApiOperation(value = "查询知识抽取器", notes = "查询知识抽取器")
	@ResponseBody
	@RequestMapping(value = "/findKnowledge", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> findKnowledge(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			List<Dict>  dicts= dictService.findKnowledge();
			result.put("data", dicts);
			return success("查询成功", result);
		} catch (Exception e) {
			logger.error("taskInfo/selectAllTask",e);
			return error("查询异常");
		}
	}
	
	
	@ApiOperation(value = "根据code和parentValue查询信息", notes = "根据code和parentValue查询信息")
	@ResponseBody
	@RequestMapping(value = "/findByCodeAndValue", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> findByCodeAndValue(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			String code = "kgSlot";
			String parentValue = StringHandler.objectToString(map.get("parentValue"));
			List<Dict>  dicts= dictService.findByCodeAndValue(code,parentValue);
			result.put("data", dicts);
			return success("查询成功", result);
		} catch (Exception e) {
			logger.error("taskInfo/selectAllTask",e);
			return error("查询异常");
		}
	}
	
	@ApiOperation(value = "新增dict数据", notes = "新增dict数据")
	@ResponseBody
	@RequestMapping(value = "/saveDict", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> saveDict(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Dict dict) {
		try {
			dict.setId("dict_"+System.currentTimeMillis());
			int i = dictService.insert(dict);
			if(i>0){
				return success();
			}
			return error("保存失败");
		} catch (Exception e) {
			logger.error("taskInfo/selectAllTask",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "修改dict数据", notes = "修改dict数据")
	@ResponseBody
	@RequestMapping(value = "/updateDict", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> updateDict(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Dict dict) {
		try {
			int i = dictService.update(dict);
			if(i>0){
				return success();
			}
			return error("保存失败");
		} catch (Exception e) {
			logger.error("taskInfo/selectAllTask",e);
			return error("保存异常");
		}
	}
}
