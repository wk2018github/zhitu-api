package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.model.DataSet;


@Mapper
public interface DataSetMapper {
	
    List<Map<String, Object>> findByName(@Param("name")String name,@Param("userId")String userId);

	int insert(DataSet dataSet);

	DataSet findById(String id);

	int updateDataSet(DataSet dSet);

	int deleteByPrimaryKey(String id);

	Map<String, Object> chartsByName(@Param("name")String name,@Param("userId")String userId);
}