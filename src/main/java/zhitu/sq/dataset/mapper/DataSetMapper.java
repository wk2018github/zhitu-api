package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface DataSetMapper {
	
    List<Map<String, Object>> findByName(@Param("name")String name,@Param("userId")String userId);
}