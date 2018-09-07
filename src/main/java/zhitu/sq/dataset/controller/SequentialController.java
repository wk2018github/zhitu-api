package zhitu.sq.dataset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import zhitu.cfg.BaseController;
import zhitu.cfg.SQApiResponse;
import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.service.GraphService;
import zhitu.sq.dataset.service.SequentialService;
import zhitu.sq.dataset.service.SinglePointService;

@RequestMapping("sequential")
@RestController
@CrossOrigin
public class SequentialController extends BaseController {

	private static final Logger logger = Logger.getLogger(SequentialController.class);

	@Autowired
	private SequentialService sequentialService;

	@ApiOperation(value = "1.1时序分析-全图分析下拉选项", notes = "1.1时序分析-全图分析下拉选项")
	@ResponseBody
	@RequestMapping(value = "/queryYearScope", method = RequestMethod.POST)
	public SQApiResponse<List<String>> queryYearScope(HttpServletRequest request) {
		try {

			return success("操作成功", sequentialService.queryYearScope());
		} catch (Exception e) {
			logger.error("sequential/initRootSinglePoint", e);
			return error("初始化数据异常");
		}
	}


}
