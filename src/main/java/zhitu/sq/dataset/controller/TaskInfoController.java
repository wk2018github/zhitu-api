package zhitu.sq.dataset.controller;

import java.util.HashMap;
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
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.service.TaskInfoService;
import zhitu.util.StringHandler;

@RequestMapping("taskInfo")
@RestController
@CrossOrigin
public class TaskInfoController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(TaskInfoController.class);
	
	
	@Autowired
	private TaskInfoService taskInfoService;
	
	private static final String user = "{\"page\":1,\"rows\":10}";
	@ApiOperation(value = "任务列表", notes = "任务列表")
	@ResponseBody
	@RequestMapping(value = "/selectAllTask", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> selectAllTask(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			String userId = "USER_1293910401";
			PageInfo<TaskInfo> tInfo = taskInfoService.selectAllTask(map);
			result = mergeJqGridData(tInfo);
			return success("查询成功", result);
		} catch (Exception e) {
			logger.error("taskInfo/selectAllTask",e);
			return error("查询异常");
		}
	}
	
	@ApiOperation(value = "任务列表更新", notes = "任务列表更新")
	@ResponseBody
	@RequestMapping(value = "/updateTask", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> updateTask(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody TaskInfo taskInfo) {
		try {
			if(taskInfo.getId().isEmpty()){
				return error("任务id不能为空！");
			}
			String userId = "USER_1293910401";
			int i = taskInfoService.updateTask(taskInfo);
			return success();
		} catch (Exception e) {
			logger.error("taskInfo/updateTask",e);
			return error("查询异常");
		}
	}

	@ApiOperation(value = "根据任务id查询任务情况", notes = "根据任务id查询任务情况")
	@ResponseBody
	@RequestMapping(value = "/selectById", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> selectById(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			String taskId = StringHandler.objectToString(map.get("taskId"));
			TaskInfo taskInfo = taskInfoService.selectById(taskId);
			result.put("data", taskInfo);
			return success(result);
		} catch (Exception e) {
			logger.error("taskInfo/updateTask",e);
			return error("查询异常");
		}
	}
	
}
