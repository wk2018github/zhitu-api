package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.controller.vo.DataSetVo;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.util.StringHandler;



@RestController
@CrossOrigin
public class DataSetController extends BaseController{
	
	private static final Logger LOG = Logger.getLogger(DataSetController.class);
	
	@Autowired
	private DataSetService dataSetService;
	
	
	@ApiOperation(value = "进入数据集页面", notes = "进入数据集页面")
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
	
	@ApiOperation(value = "根据数据集id和typeId(类型)查询数据集详细", notes = "根据数据集id和typeId(类型)查询数据集详细")
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> findById(HttpServletRequest request,
    		@ApiParam(value = "id")@RequestBody Map<String, Object> map) {
    	try {
        	Map<String, Object> result = new HashMap<String, Object>();
        	PageInfo<Map<String, Object>> data = dataSetService.findById(map);
        	result.put("date", data);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	@ApiOperation(value = "根据id和typeId(类型)删除数据集", notes = "根据id和typeId(类型)删除数据集")
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> deleteById(HttpServletRequest request,
    		@ApiParam(value = "id")@RequestBody Map<String, Object> map) {
    	try {
    		
    		String id = StringHandler.objectToString(map.get("id"));
    		String typeId = StringHandler.objectToString(map.get("typeId"));
        	int i = dataSetService.deleteById(id,typeId);
        	if(i>0){
        		return success();
        	}
    		return error("删除失败");
		} catch (Exception e) {
			LOG.error("删除失败:" + e.getMessage(),e);
			return error("删除失败");
		}
    }
	
	@ApiOperation(value = "更新数据集信息", notes = "更新数据集信息")
    @RequestMapping(value = "/updateDataSet",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> updateDataSet(HttpServletRequest request,
    		@RequestBody DataSetVo dataSet) {
    	try {
        	if(dataSet.getId().isEmpty()){
        		return error("数据集id不能为空！");
        	}
        	DataSet dSet = new DataSet();
        	BeanUtils.copyProperties(dSet, dataSet);
        	int i = dataSetService.updateDataSet(dSet);
        	if(i>0){
        		return success();
        	}
        	return error("修改失败");
		} catch (Exception e) {
			LOG.error("修改失败:" + e.getMessage(),e);
			return error("修改失败");
		}
    }
	
	
	/**
	 * 
	 * 暂时未完成
	 */
	@ApiOperation(value = "保存数据集信息-多文件上传保存", notes = "保存数据集信息-文件上传保存")
    @RequestMapping(value = "/handleFileUpload",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> handleFileUpload(/*HttpServletRequest request,*/
    		@RequestParam("name") String name, @RequestParam("describe") String describe,
    		@RequestParam("projectId") String projectId,@RequestParam("files") MultipartFile[] files) {
    	try {
        	//获取登录用户Id
    		String userId = "USER_1293910401";
    		int i = dataSetService.saveLocalDataSet(userId,name,describe,projectId,files);
    		if(i>0){
    			return success();
    		}
    		return error("上传失败");
		} catch (Exception e) {
			LOG.error("上传失败:" + e.getMessage(),e);
			return error("上传失败");
		}
    }
	
}
