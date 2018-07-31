package zhitu.sq.dataset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import zhitu.sq.dataset.model.TaskInfo;

@Mapper
public interface TaskInfoMapper {
    int insert(TaskInfo record);

    int insertSelective(TaskInfo record);

	List<TaskInfo> selectAllTask();
}