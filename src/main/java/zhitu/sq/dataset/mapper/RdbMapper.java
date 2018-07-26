package zhitu.sq.dataset.mapper;

import zhitu.sq.dataset.model.Rdb;

public interface RdbMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(Rdb record);

    int insertSelective(Rdb record);

    Rdb selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Rdb record);

    int updateByPrimaryKey(Rdb record);
}