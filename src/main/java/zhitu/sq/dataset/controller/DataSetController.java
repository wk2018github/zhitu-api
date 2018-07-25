package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.service.DataSetService;



@RestController
@CrossOrigin
@MapperScan("zhitu.sq.dataset.mapper")
public class DataSetController extends BaseController{
	
	private static final Logger LOG = Logger.getLogger(DataSetController.class);
	
	@Autowired
	private DataSetService dataSetService;
	
    @RequestMapping(value = "/queryDateSet",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> queryDateSet(HttpServletRequest request,
    		@ApiParam(value = "{\"page\":1,\"rows\":10,\"name\":\"项目名称\"}")@RequestBody Map<String, Object> map) {
    	try {
        	Map<String, Object> result = new HashMap<String, Object>();
        	//获取登录用户Id
    		String userId = "USER_1293910401";
    		PageInfo<Map<String, Object>> dateSet = dataSetService.queryDateSet(map,userId);
    		result = mergeJqGridData(dateSet);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }

}
