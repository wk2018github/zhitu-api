package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.TableMoney;


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
	
	/**
	 * @author qwm
	 * 查询基础表名称
	 */
	List<TableMoney> queryBasicsTableName(@Param(value="tableCodes")String tableCodes);
	
	/**
	 * @author qwm
	 * 查询基础表 的金额列名称
	 */
	List<String> queryNodeColumnName(String parentValue);
	
	/**
	 * @author qwm
	 * 查询基础表 的金额列 的 金额的总和
	 */
	String queryMoney(@Param(value="tableName")String tableName, @Param(value="column")String column, @Param(value="year")String year);
	
	/**
	 * @author qwm
	 * 查询基础表 的金额列名称
	 */
	List<String> queryColumnNameByCodeParentCodeFromDict(@Param(value="key")String key, @Param(value="parentValue")String parentValue);
	
	/**
	 * @author qwm
	 * 查询基础表 的金额列名称
	 */
	String queryBasicsTableNameByCode(@Param(value="key")String key);
	
}