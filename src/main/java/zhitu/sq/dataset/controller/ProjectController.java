package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.model.Project;
import zhitu.sq.dataset.model.User;
import zhitu.sq.dataset.service.PorjectService;
import zhitu.util.StringHandler;

@RequestMapping("project")
@RestController
@CrossOrigin
public class ProjectController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(ProjectController.class);

	@Autowired
	private PorjectService porjectService;
	
	private static final String name = "{\"name\":\"审计项目\",\"description\":\"ss\"}";
	@ApiOperation(value = "保存新增项目信息", notes = "保存新增项目信息")
	@ResponseBody
	@RequestMapping(value = "/saveProject", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> saveProject(HttpServletRequest request,
			@ApiParam(value = name) @RequestBody Map<String, Object> map) {
		try {
//			User user = (User)request.getSession().getAttribute("user");
			String name = StringHandler.objectToString(map.get("name"));
			String description = StringHandler.objectToString(map.get("description"));
//			Map<String, Object> result = new HashMap<String, Object>();
			int i = porjectService.saveProject("USER_1353923423",name,description);
			if(i>0){
				return success("保存成功",null);
			}
			return error("保存异常");
		} catch (Exception e) {
			logger.error("project/saveProject",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "查询自己的项目", notes = "查询自己的项目")
	@ResponseBody
	@RequestMapping(value = "/queryProject", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> queryProject(HttpServletRequest request,
			@ApiParam(value = name) @RequestBody Map<String, Object> map) {
		try {
//			User user = (User)request.getSession().getAttribute("user");
			String name = StringHandler.objectToString(map.get("name"));
			Map<String, Object> result = new HashMap<String, Object>();
			List<Map<String, Object>> project = porjectService.queryProject(name,"USER_1353923423");
			result.put("data", project);
			return success(result);
		} catch (Exception e) {
			logger.error("project/queryProject",e);
			return error("查询异常");
		}
	}
	
	@ApiOperation(value = "保存修改的项目", notes = "保存修改的项目")
	@ResponseBody
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> updateProject(HttpServletRequest request,
			@ApiParam(value = name) @RequestBody Project project) {
		try {
			int i = porjectService.updateProject(project);
			if(i>0){
				return success("保存成功",null);
			}
			return error("保存异常");
		} catch (Exception e) {
			logger.error("project/updateProject",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "删除项目", notes = "删除项目")
	@ResponseBody
	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> deleteProject(HttpServletRequest request,
			@ApiParam(value = name) @RequestBody Map<String, Object> map) {
		try {
			int i = porjectService.deleteProject(map);
			if(i>0){
				return success("删除成功",null);
			}
			return error("删除异常");
		} catch (Exception e) {
			logger.error("project/deleteProject",e);
			return error("删除异常");
		}
	}
}
