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
import zhitu.sq.dataset.model.FtpFile;
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.service.DictService;
import zhitu.sq.dataset.service.FtpFileService;
import zhitu.sq.dataset.service.TaskInfoService;
import zhitu.util.StringHandler;

@RequestMapping("ftpFile")
@RestController
@CrossOrigin
public class FtpFileController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(FtpFileController.class);
	
	
	@Autowired
	private FtpFileService ftpFileService;
	
	private static final String user = "{\"page\":1,\"rows\":10}";
	@ApiOperation(value = "对上传的文件内容进行修改", notes = "对上传的文件内容进行修改")
	@ResponseBody
	@RequestMapping(value = "/updateFtpFile", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> updateFtpFile(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody FtpFile ftpFile) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			int i = ftpFileService.updateFtpFile(ftpFile);
			if(i>0){
				return success();
			}
			return error("保存成功");
		} catch (Exception e) {
			logger.error("taskInfo/selectAllTask",e);
			return error("保存异常");
		}
	}
	
	@ApiOperation(value = "查询上传文件内容", notes = "查询上传文件内容")
	@ResponseBody
	@RequestMapping(value = "/queryFtpFile", method = RequestMethod.POST)
	public SQApiResponse<Map<String, Object>> queryFtpFile(HttpServletRequest request,
			@ApiParam(value = user) @RequestBody Map<String, Object> map) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			PageInfo<FtpFile> ftpFile = ftpFileService.queryFtpFile(map);
			result = mergeJqGridData(ftpFile);
			return success(result);
		} catch (Exception e) {
			logger.error("taskInfo/selectAllTask",e);
			return error("保存异常");
		}
	}
	
}
