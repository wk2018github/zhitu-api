package zhitu.sq.dataset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.model.Knowledge;

@Mapper
public interface KnowledgeMapper {
    int deleteByPrimaryKey(String id);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);

	List<Map<String, Object>> findByName(@Param("name")String name);

	int deleteKnowledge(@Param("ids")List<String> ids);
}