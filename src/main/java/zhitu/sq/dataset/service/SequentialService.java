package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;


import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.TableMoney;

public interface SequentialService {
	
	/**
	 * @Author: qwm
	 * @Description: 1.1时序分析-全图分析下拉选项
	 */
	List<Select> queryAnalysisSelect() throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 1.1时序分析-观测指标-第一个下拉框
	 */
	List<Select> querySelectOne() throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 1.1时序分析-观测指标-第二个下拉框
	 */
	List<Select> querySelectTwo(Map<String,Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 
	 */
	List<String> queryYearScope() throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 
	 */
	Map<String,Object> startAnalysis(Map<String,Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 1.5时序分析-开始分析-右下角四张表数据
	 */
	List<TableMoney> queryTableMoney(Map<String,Object> map) throws Exception;
	
	

}
