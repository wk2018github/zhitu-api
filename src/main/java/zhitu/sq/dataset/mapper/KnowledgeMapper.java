package zhitu.sq.dataset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.Knowledge;
import zhitu.sq.dataset.model.Rdb;

@Mapper
public interface KnowledgeMapper {
	int deleteByPrimaryKey(String id);

	int insert(Knowledge record);

	int insertSelective(Knowledge record);

	Knowledge selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Knowledge record);

	int updateByPrimaryKey(Knowledge record);

	List<Map<String, Object>> findByName(@Param("name") String name);

	int deleteKnowledge(@Param("ids") List<String> ids);

	/**
	 * 
	 * @Author: qwm @Description:所有知识表下拉选项 @return: List<Select> @throws
	 */
	List<Select> queryKnowledge();

	/**
	 * 
	 * @Author: qwm @Description:查询数据集类型 @return: List<Select> @throws
	 */
	DataSet queryDataSetByKnId(Map<String, Object> map);

	/**
	 * 
	 * @Author: qwm @Description:查询数据库列名 @return: List<Select> @throws
	 */
	String queryColumnByRdbId(String rdbId);

	/**
	 * 
	 * @Author: qwm @Description:根据rdbId查询rdb信息 @return: Rdb @throws
	 */
	Rdb queryRdbById(String rdbId);

}