package zhitu.sq.dataset.mapper;

import org.apache.ibatis.annotations.Mapper;

import zhitu.sq.dataset.model.TaskInfo;

@Mapper
public interface TaskInfoMapper {
    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);
}