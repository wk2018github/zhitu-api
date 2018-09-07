package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.Graph;


@Mapper
public interface SequentialMapper {
	
	/**
	 * @Author: qwm
	 * @Description: 1.1时序分析-全图分析下拉选项
	 */
	List<Select> queryAnalysisSelect();
	
	/**
	 * @Author: qwm
	 * @Description: 1.1时序分析-观测指标-第一个下拉框
	 */
	List<Select> querySelectOne();
	
	/**
	 * @Author: qwm
	 * @Description: 1.1时序分析-观测指标-第二个下拉框
	 */
	List<Select> querySelectTwo(Map<String,Object> map);
	
	/**
	 * @author qwm
	 * 查询单点分析根节点
	 */
	String queryRootSinglePoint();
	
	
}