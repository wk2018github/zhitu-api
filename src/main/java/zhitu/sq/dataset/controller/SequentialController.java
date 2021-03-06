package zhitu.sq.dataset.controller;

import java.util.ArrayList;
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
import zhitu.sq.dataset.controller.vo.LineChart;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.TableMoney;
import zhitu.sq.dataset.service.SequentialService;

@RequestMapping("sequential")
@RestController
@CrossOrigin
public class SequentialController extends BaseController {

	private static final Logger logger = Logger.getLogger(SequentialController.class);

	@Autowired
	private SequentialService sequentialService;

	@ApiOperation(value = "1.1时序分析-全图分析下拉选项", notes = "1.1时序分析-全图分析下拉选项")
	@ResponseBody
	@RequestMapping(value = "/queryAnalysisSelect", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> queryAnalysisSelect(HttpServletRequest request) {
		try {

			return success("操作成功", sequentialService.queryAnalysisSelect());
		} catch (Exception e) {
			logger.error("sequential/queryAnalysisSelect", e);
			return error("查询异常");
		}
	}
	
	@ApiOperation(value = "1.2时序分析-观测指标-第一个下拉框", notes = "1.2时序分析-观测指标-第一个下拉框")
	@ResponseBody
	@RequestMapping(value = "/querySelectOne", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> querySelectOne(HttpServletRequest request) {
		try {

			return success("操作成功", sequentialService.querySelectOne());
		} catch (Exception e) {
			logger.error("sequential/querySelectOne", e);
			return error("查询异常");
		}
	}
	
	private static final String tableKey = "{\"key\":\"01\"}";
	@ApiOperation(value = "1.3时序分析-观测指标-第二个下拉框", notes = "1.3时序分析-观测指标-第二个下拉框")
	@ResponseBody
	@RequestMapping(value = "/querySelectTwo", method = RequestMethod.POST)
	public SQApiResponse<List<Select>> querySelectTwo(HttpServletRequest request,
			@ApiParam(value=tableKey) @RequestBody Map<String,Object> map) {
		try {

			return success("操作成功", sequentialService.querySelectTwo(map));
		} catch (Exception e) {
			logger.error("sequential/querySelectTwo", e);
			return error("查询异常");
		}
	}
	
	/**
	 * 此方法需要重写的
	 */
	private static final String startAnalysis = "{\"analysisType\":\"01\",\"analysisObj\":\"01\",\"year\":\"2015,2017\","
			+ "\"nodeId\":\"2015,2017\",\"basicsTable\":\"基础表码值\",\"nodeColumn\":\"金额列码值\"}";
	@ApiOperation(value = "1.4时序分析-开始分析", notes = "1.4时序分析-开始分析")
	@ResponseBody
	@RequestMapping(value = "/startAnalysis", method = RequestMethod.POST)
	public SQApiResponse<Map<String,Object>> startAnalysis(HttpServletRequest request,
			@ApiParam(value=startAnalysis) @RequestBody Map<String,Object> map) {
		try {

			return success(sequentialService.startAnalysis(map));
			
		} catch (Exception e) {
			logger.error("sequential/startAnalysis", e);
			return error("查询异常");
		}
	}
	
	/**
	 * 此方法需要重写
	 */
	private static final String tableCodes = "{\"tableCodes\":\"01,02,03,04\"}";
	@ApiOperation(value = "1.5时序分析-开始分析-右下角四张表数据", notes = "1.5时序分析-开始分析-右下角四张表数据")
	@ResponseBody
	@RequestMapping(value = "/queryTableMoney", method = RequestMethod.POST)
	public SQApiResponse<List<TableMoney>> queryTableMoney(HttpServletRequest request,
			@ApiParam(value=tableCodes) @RequestBody Map<String,Object> map) {
		try {

			return success(sequentialService.queryTableMoney(map));
		} catch (Exception e) {
			logger.error("sequential/queryTableMoney", e);
			return error("查询异常");
		}
	}
	
	
	


}
