package zhitu.sq.dataset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.model.Dict;

@Mapper
public interface DictMapper {
    int deleteByPrimaryKey(String id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

	List<Dict> findKnowledge();

	List<Dict> findByCodeAndValue(@Param("code")String code,@Param("parentValue")String parentValue);
}