package zhitu.sq.dataset.mapper;


import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.model.DataSet;

public interface DateSetMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(DataSet record);

    int insertSelective(DataSet record);

    DataSet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataSet record);

    int updateByPrimaryKey(DataSet record);
    
    PageInfo<Map<String, Object>> findByName(@Param("name")String name,@Param("userId")String userId);
}