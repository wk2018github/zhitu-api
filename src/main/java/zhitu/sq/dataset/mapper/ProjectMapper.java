package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.model.Project;



@Mapper
public interface ProjectMapper {

	int deleteByPrimaryKey(String id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

	List<Map<String, Object>> queryProject(@Param("name")String name,@Param("userId") String userId);

	int deleteProject(@Param("ids")List<String> ids);

	
}