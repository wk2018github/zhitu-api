package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;

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
	 * @Description: 1单点分析-初始化根节点
	 */
	List<String> queryYearScope() throws Exception;
	
	

}
