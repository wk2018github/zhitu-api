package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mysql.jdbc.StringUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.model.User;
import zhitu.sq.dataset.service.UserService;

@RequestMapping("user")
@RestController
@CrossOrigin
public class UserController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	
	@Autowired
	private UserService userService;
	
	private static final String user = "{\"email\":\"superadmin\",\"password\":\"123456\"}";
	@ApiOperation(value = "登录接口", notes = "登录接口")
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> login(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody User user) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			if(null == user || StringUtils.isNullOrEmpty(user.getEmail()) || StringUtils.isNullOrEmpty(user.getPassword())){
				return error("参数错误");
			}
			
			int flag = userService.checkLoginName(user);
			if (-1 == flag) {
				return error("用户名不存在");
			} else if (-2 == flag) {
				return error("两个以上同名用户,怎么可能,请联系管理员");
			} 
			user = userService.checkPwd(user);
			if (null == user) {
				return error("密码错误");
			}
			
			request.getSession().setAttribute("user", user);
			
			result.put("user", user);
			
			return success("登录成功", result);
		} catch (Exception e) {
			logger.error("user/login",e);
			return error("登录异常");
		}
	}
	
	
	@ApiOperation(value = "登出", notes = "登出")
	@ResponseBody
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> loginOut(HttpServletRequest request) {
		try {
			
			if(null != request.getSession().getAttribute("user")){
				request.getSession().removeAttribute("user");
			}
			
			return success("退出成功",null);
		} catch (Exception e) {
			logger.error("user/loginOut",e);
			return error("退出异常");
		}
	}

}
