package zhitu.sq.dataset.mapper;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


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
	
}