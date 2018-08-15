package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.model.Graph;




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
	int deleteGraph(@Param("ids") String ids);
    
	
	
}