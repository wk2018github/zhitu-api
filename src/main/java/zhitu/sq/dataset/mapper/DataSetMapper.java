package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.model.DataSet;


@Mapper
public interface DataSetMapper {
	
    List<Map<String, Object>> findByName(@Param("name")String name,@Param("userId")String userId,@Param("projectId")String projectId);

	int insert(DataSet dataSet);

	DataSet findById(String id);

	int updateDataSet(DataSet dSet);

	int deleteByPrimaryKey(String id);

	List<Map<String, Object>> chartsByName(@Param("name")String name,@Param("projectId")String projectId,@Param("userId")String userId);
}