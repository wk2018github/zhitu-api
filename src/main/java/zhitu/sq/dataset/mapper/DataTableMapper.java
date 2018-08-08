package zhitu.sq.dataset.mapper;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.controller.vo.ActivityVo;
import zhitu.sq.dataset.controller.vo.AdviceVo;
import zhitu.sq.dataset.controller.vo.EnforcementVo;
import zhitu.sq.dataset.controller.vo.OpinionVo;
import zhitu.sq.dataset.controller.vo.ProblemVo;
import zhitu.sq.dataset.controller.vo.UnderlyingVo;


/**
 * 用于动态sql表
 * @author wkk
 *
 */
@Mapper
public interface DataTableMapper {

	List<Map<String, Object>> findByDataSetId(@Param("tableName")String tableName);

	int deleteByDataSetId(@Param("tableName")String tableName);

	Map<String, Object> findByTableAndId(@Param("tableName")String tableName,@Param("id")String id);

	List<String> findFields(String dataTable);

	int createTalbe(@Param("sql")String sql);

	int insertActivity(@Param("activityVo")ActivityVo activityVo,@Param("tableName")String tableName);

	int insertAdvice(@Param("adviceVo")AdviceVo adviceVo,@Param("tableName")String tableName);

	int insertOpinion(@Param("opinionVo")OpinionVo opinionVo,@Param("tableName")String tableName);

	int insertUnderlying(@Param("underlyingVo")UnderlyingVo underlyingVo,@Param("tableName")String tableName);

	int insertProblem(@Param("problemVo")ProblemVo problemVo,@Param("tableName")String tableName);

	int insertEnforcement(@Param("enforcementVo")EnforcementVo enforcementVo,@Param("tableName")String tableName);
	
}