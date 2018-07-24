package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.PageInfo;

import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.service.DataSetService;



@RestController
@CrossOrigin
public class DataSetController extends BaseController{
	
	private static Logger Log = Logger.getLogger(DataSetController.class);
	@Autowired
	private DataSetService dataSetService;
	
    @RequestMapping(value = "/queryDateSet",  method = RequestMethod.GET)
    public SQApiResponse<Map<String, Object>> queryDateSet(@RequestBody Map<String, Object> map) {
    	try {
    		Map<String, Object> result = new HashMap<>();
    		//获取登录用户Id
    		String userId = "USER_1293910401";
    		PageInfo<Map<String, Object>> dateSet = dataSetService.queryDateSet(map,userId);
    		result = mergeJqGridData(dateSet);
    		return success(result);
		} catch (Exception e) {
			Log.error("查询失败",e);
			return error("查询失败");
		}
    }

}
