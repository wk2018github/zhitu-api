package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.model.User;
import zhitu.sq.dataset.service.TaskInfoService;
import zhitu.sq.dataset.service.UserService;

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
	

}
