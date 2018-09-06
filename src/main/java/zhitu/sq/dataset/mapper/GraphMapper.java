package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.model.ProcessGraph;




@Mapper
public interface GraphMapper {
	
	/**
	 * @author qwm
	 * 查询graph表所有数据  或者按照名称
	 * @param email
	 * @return
	 */
	List<Graph> queryAllGraph(Map<String,Object> map);
	
	/**
	 * @Author: qwm
	 * @Description:新增一条graph
	 */
	int insertGraph(Map<String,Object> map);
	
	/**
	 * @Author: qwm
	 * @Description:更新graph的name 和 description
	 */
	int editGraph(Graph graph);
	
	/**
	 * @Author: qwm
	 * @Description:删除graph
	 */
	int deleteGraph(@Param("ids") List<String> ids);
	
	/**
	 * @Author: qwm
	 * @Description: 查询过滤器下面具体的值
	 */
	List<Select> queryTableFilter(@Param("table") String table, @Param("code") String code, @Param("name") String name);
	/**
	 * @Author: qwm
	 * @Description: 查询下一级过滤器下面具体的值
	 */
	List<Select> queryLowerLevelTableFilter(@Param("table") String table, @Param("code") String code
			, @Param("name") String name, @Param("codeValue") String codeValue);
    
	/**
	 * @Author: qwm
	 * @Description: 保存流程图
	 */
	int saveProcessGraph(Map<String,Object> map);
	
	/**
	 * @Author: qwm
	 * @Description:2.1图谱分析-流程分析-查询流程图列表
	 */
	List<ProcessGraph> queryProcessGraph(Map<String, Object> map);
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-删除流程图列表
	 */
	Integer deleteProcessGraph(Map<String, Object> map);
	
	/**
	 * @author qwm
	 * 查询graph表所有数据  或者按照名称
	 * @param email
	 * @return
	 */
	List<NodeDetail> queryNodeDetails(Map<String,Object> map);
	
}