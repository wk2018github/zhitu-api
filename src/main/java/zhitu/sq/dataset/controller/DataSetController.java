package zhitu.sq.dataset.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.controller.vo.DataSetRdbVo;
import zhitu.sq.dataset.controller.vo.DataSetVo;
import zhitu.sq.dataset.controller.vo.RdbVo;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.Rdb;
import zhitu.sq.dataset.model.User;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.util.StringHandler;


@RequestMapping("dataSet")
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
        	User user = (User)request.getSession().getAttribute("user");
        	//获取登录用户Id
        /*	if(user==null){
//        		return error("请重新登录!");
        		user.setId("USER_1353923423");
        	}*/
    		String userId = "USER_1353923423";
    		PageInfo<Map<String, Object>> dateSet = dataSetService.queryDateSet(map,userId);
    		result = mergeJqGridData(dateSet);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	private static final String param = "{\"page\":1,\"rows\":10,\n" 
			+ "\"id\":\"DATASET_1532596254452\",\"typeId\":\"local_rdb\",\n" 
			+ "\"rdbId\":\"RDB_1532596254450\",\"dataTable\":\"zt_data_dataset_1531139698243\"}";
	
	@ApiOperation(value = "根据数据集id和typeId(类型)查询数据集详细", notes = "根据数据集id和typeId(类型)查询数据集详细")
    @RequestMapping(value = "/findById",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> findById(HttpServletRequest request,
    		@ApiParam(value = param)@RequestBody Map<String, Object> map) {
    	try {
        	Map<String, Object> result = new HashMap<String, Object>();
        	String typeId = StringHandler.objectToString(map.get("typeId"));
        	
        	if(typeId.equals("ftp_file")){
        		PageInfo<Map<String, Object>> data = dataSetService.findByIdFtpFile(map);
            	result = mergeJqGridData(data);
    		}else if(typeId.equals("local_rdb")){
    			PageInfo<Map<String, Object>> data = dataSetService.findByIdLcoal(map);
            	result = mergeJqGridData(data);
    		}else{
    			Map<String, Object> data = dataSetService.findById(map);
            	result = data;
    		}
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	@ApiOperation(value = "根据id删除数据集", notes = "根据id删除数据集")
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> deleteById(HttpServletRequest request,
    		@ApiParam(value = "id")@RequestBody Map<String, Object> map) {
    	try {
    		List<String> ids = (List<String>) map.get("ids");
        	dataSetService.deleteById(ids);
        	return success();
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
	 * @author qwm
	 * @param request
	 * @param rdb
	 * @return
	 */
	@ApiOperation(value = "保存数据集信息-多文件上传保存", notes = "保存数据集信息-文件上传保存")
//	@RequestMapping(value = "/handleFileUpload", method = RequestMethod.POST)
	@RequestMapping(value = "/handleFileUpload", consumes = " multipart/*", headers = "content-type=multipart/form-data",method = RequestMethod.POST)
	@ResponseBody
	public SQApiResponse<Map<String, Object>> handleFileUpload(HttpServletRequest request,
			@RequestParam("name") String name, @RequestParam("describe") String describe,
			@RequestParam("projectId") String projectId ) {
//		,@ApiParam(value = "上传文件", required = true)MultipartFile file 
		try {
			
			// 获取登录用户Id
			User user = (User)request.getSession().getAttribute("user");
//			if(user==null){
////        		return error("请重新登录!");
//				user.setId("USER_1353923423");
//        	}
			String fileName = dataSetService.saveLocalDataSet("USER_1353923423", name, describe, projectId, null, request); //file, 
			if (StringUtils.isEmpty(fileName)) {
				return success();
			} else {
				return error(fileName+"上传失败");
			}
		} catch (Exception e) {
			LOG.error("handleFileUpload", e);
			return error("上传失败");
		}
	}
	
	@ApiOperation(value = "保存数据集信息-远程数据库sql", notes = "保存数据集信息-远程数据库sql")
    @RequestMapping(value = "/saveSqlDb",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> saveSqlDb(HttpServletRequest request,
    		@RequestBody DataSetRdbVo dRdbVo) {
    	try {
    		Map<String, Object> result = new HashMap<String, Object>();
        	//获取登录用户Id
    		User user = (User)request.getSession().getAttribute("user");
//    		if(user==null){
//        		user.setId("USER_1353923423");
//        	}
    		String userId = "USER_1353923423";
    		String id = dataSetService.saveSqlDataSet(dRdbVo,userId);
    		result.put("id", id);
    		return success(result);
		} catch (Exception e) {
			LOG.error("保存失败:" + e.getMessage(),e);
			return error("保存失败");
		}
    }
	
	@ApiOperation(value = "本地数据库local_rdb修改", notes = "本地数据库local_rdb修改")
    @RequestMapping(value = "/updateSqlDb",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> updateSqlDb(HttpServletRequest request,
    		@RequestBody Map<String, Object> map) {
    	try {
//        	//获取登录用户Id
//    		String userId = "USER_1293910401";
//    		int i = dataSetService.updateSqlDb(map);
//    		if(i>0){
//    			return success();
//    		}
    		return error("更新失败");
		} catch (Exception e) {
			LOG.error("更新失败:" + e.getMessage(),e);
			return error("更新失败");
		}
    }
	
	@ApiOperation(value = "本地数据库local_rdb根据表id查询数据", notes = "本地数据库local_rdb根据表id查询数据")
    @RequestMapping(value = "/findByTableAndId",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> findByTableAndId(HttpServletRequest request,
    		@RequestBody Map<String, Object> map) {
    	try {
    		Map<String, Object> result = new HashMap<String, Object>();
    		result = dataSetService.findByTableAndId(map);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	@ApiOperation(value = "数据库根据表查询数据分页--远程数据库", notes = "数据库根据表查询数据分页")
    @RequestMapping(value = "/findByTable",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> findByTable(HttpServletRequest request,
    		@RequestBody RdbVo rdbVo) {
    	try {
    		Map<String, Object> result = dataSetService.findByTable(rdbVo);
//    		result = mergeJqGridData(list);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	@ApiOperation(value = "数据库根据表查询数据分页--本地", notes = "数据库根据表查询数据分页--本地")
    @RequestMapping(value = "/findByLocalTable",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> findByLocalTable(HttpServletRequest request,
    		@RequestBody Map<String, Object> map) {
    	try {
    		Map<String, Object> result = new HashMap<String, Object>();
    		String id = StringHandler.objectToString(map.get("id"));
    		DataSet ds = dataSetService.selectByPrimaryKey(id);
    		map.put("dataTable", ds.getDataTable());
    		PageInfo<Map<String, Object>> list = dataSetService.findByIdLcoal(map);
    		result = mergeJqGridData(list);
    		result.put("dataSetName", ds.getName());
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	@ApiOperation(value = "数据库根据表查询表字段", notes = "数据库根据表查询表字段")
    @RequestMapping(value = "/findByTableFiled",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> findByTableFiled(HttpServletRequest request,
    		@RequestBody RdbVo rdbVo) {
    	try {
    		Map<String, Object> result = new HashMap<String, Object>();
    		List<String> list = dataSetService.findByTableFiled(rdbVo);
    		result.put("data", list);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	@ApiOperation(value = "根据数据集id查询字段以及样本个数", notes = "根据数据集id查询表字段以及样本个数")
    @RequestMapping(value = "/findByTableValue",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> findByTableValue(HttpServletRequest request,
    		@RequestBody Map<String, Object> map) {
    	try {
    		Map<String, Object> result = new HashMap<String, Object>();
    		String id = StringHandler.objectToString(map.get("id"));
    		DataSet dataSet = dataSetService.selectByPrimaryKey(id);
    		Map<String, Object> data = dataSetService.findByTableValue(dataSet);
    		result.put("data", data);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	@ApiOperation(value = "数据集饼状图", notes = "数据集饼状图")
    @RequestMapping(value = "/chartsByName",method = RequestMethod.POST)
    @ResponseBody
    public SQApiResponse<Map<String, Object>> chartsByName(HttpServletRequest request,
    		@RequestBody Map<String, Object> map) {
    	try {
    		Map<String, Object> result = new HashMap<String, Object>();
    		//获取登录用户Id
    		User user = (User)request.getSession().getAttribute("user");
//    		if(user==null){
////        		return error("请重新登录!");
//    			user.setId("USER_1353923423");
//        	}
    		String userId = "USER_1353923423";
    		String name = StringHandler.objectToString(map.get("name"));
    		String projectId = StringHandler.objectToString(map.get("projectId"));
//    		if(projectId.isEmpty()){
//    			return error("项目Id不能为空!");
//    		}
    		List<Map<String, Object>> list = dataSetService.chartsByName(name,projectId,userId);
    		result.put("data", list);
    		return success(result);
		} catch (Exception e) {
			LOG.error("查询失败:" + e.getMessage(),e);
			return error("查询失败");
		}
    }
	
	
	/**
	 * @author qwm
	 * @param request
	 * @param rdb
	 * @return
	 */
	@ApiOperation(value = "连接数据库页面查询数据库类型", notes = "连接数据库页面查询数据库类型")
	@RequestMapping(value = "/queryDBType", method = RequestMethod.POST)
	@ResponseBody
	public SQApiResponse<List<Select>> queryDBType(HttpServletRequest request) {
		try {
			List<Select> res = new ArrayList<Select>();

			res = dataSetService.queryDBType();

			return success(res);
		} catch (Exception e) {
			LOG.error("dataSet/queryDBType", e);
			return error("查询失败");
		}
	}

	/**
	 * @author qwm
	 * @param request
	 * @param rdb
	 * @return
	 */
	@ApiOperation(value = "远程连接数据库查询数据库表所有表", notes = "远程连接数据库查询数据库表所有表")
	@RequestMapping(value = "/queryDBTables", method = RequestMethod.POST)
	@ResponseBody
	public SQApiResponse<List<String>> queryDBTables(HttpServletRequest request, @RequestBody Rdb rdb) {
		try {
			List<String> res = new ArrayList<String>();

			res = dataSetService.queryDBTables(rdb);

			return success(res);
		} catch (Exception e) {
			LOG.error("dataSet/queryDBTables", e);
			return error("查询失败");
		}
	}

	private static final String queryRdbData = "{\"page\":1,\"rows\":10,\"host\":\"localhost\",\n\"port\":\"3306\","
			+ "\"databaseType\":\"mysql\",\"user\":\"root\",\n\"password\":\"123456\",\"dbName\":\"db_kg\",\"tableName\":\"zt_sys_dict\"}";

	/**
	 * @author qwm
	 * @param request
	 * @param rdb
	 * @return
	 */
	@ApiOperation(value = "远程连接数据库查询数据库指定表中的数据", notes = "远程连接数据库查询数据库指定表中的数据")
	@RequestMapping(value = "/queryTableData", method = RequestMethod.POST)
	@ResponseBody
	public SQApiResponse<Map<String, Object>> queryTableData(HttpServletRequest request,
			@ApiParam(value = queryRdbData) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String,Object>();

			result = dataSetService.queryTableData(map);

			return success(result);
		} catch (Exception e) {
			LOG.error("dataSet/queryTableData", e);
			return error("查询失败");
		}
	}

	/**
	 * @author qwm
	 * @param request
	 * @param rdb
	 * @return
	 */
	@ApiOperation(value = "远程连接数据库查询数据库指定表中的列", notes = "远程连接数据库查询数据库指定表中的列")
	@RequestMapping(value = "/queryTableColumn", method = RequestMethod.POST)
	@ResponseBody
	public SQApiResponse<List<String>> queryTableColumn(HttpServletRequest request,
			@ApiParam(value = queryRdbData) @RequestBody Map<String, Object> map) {
		try {
			List<String> res = new ArrayList<String>();

			res = dataSetService.queryTableColumn(map);

			return success(res);
		} catch (Exception e) {
			LOG.error("dataSet/queryTableColumn", e);
			return error("查询失败");
		}
	}
	
	private static final String rdbColumnData = "{\"page\":1,\"rows\":10,\"host\":\"192.168.100.111\",\n\"port\":\"30620\",\"databaseType\":\"01\",\n"
			+ "\"charset\":\"utf-8\",\"user\":\"root\",\n\"password\":\"123456\",\"dbName\":\"ldp_test\","
			+ "\"tableName\":\"ldp_asset_object\",\n\"columnNames\":\"id,code,name\",\"dataSetId\":\"DATASET_1531139698243\"}";
	/**
	 * @author qwm
	 * @param request
	 * @param rdb
	 * @return
	 */
	@ApiOperation(value = "远程连接数据库查询表中指定的字段的表数据", notes = "远程连接数据库查询表中指定的字段的表数据")
	@RequestMapping(value = "/queryTableColumnData", method = RequestMethod.POST)
	@ResponseBody
	public SQApiResponse<Map<String, Object>> queryTableColumnData(HttpServletRequest request,
			@ApiParam(value = rdbColumnData) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String,Object>();

			result = dataSetService.queryTableColumnData(map);

			return success(result);
		} catch (Exception e) {
			LOG.error("dataSet/queryTableColumnData", e);
			return error("查询失败");
		}
	}
	
	/**
	 * @author qwm
	 * @param request
	 * @param rdb
	 * @return
	 */
	@ApiOperation(value = "远程连接数据库数据导入本地数据库并且查询表中指定的字段的表数据", notes = "远程连接数据库数据导入本地数据库并且查询表中指定的字段的表数据")
	@RequestMapping(value = "/queryLocalTableColumnData", method = RequestMethod.POST)
	@ResponseBody
	public SQApiResponse<Map<String, Object>> queryLocalTableColumnData(HttpServletRequest request,
			@ApiParam(value = rdbColumnData) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String,Object>();
			
			//获取登录用户Id
			User user = (User)request.getSession().getAttribute("user");
			/*if(user==null){
//        		return error("请重新登录!");
				user.setId("USER_1353923423");
        	}*/
			String userId = "USER_1353923423";
			result = dataSetService.queryLocalTableColumnData(map,userId);

			return success(result);
		} catch (Exception e) {
			LOG.error("dataSet/queryLocalTableColumnData", e);
			return error("查询失败");
		}
	}
	
	
}
