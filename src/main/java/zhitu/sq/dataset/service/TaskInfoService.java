package zhitu.sq.dataset.service;


import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.model.TaskInfo;


public interface TaskInfoService {

	/**
	 * 查询任务
	 * @param map
	 * @return
	 */
	PageInfo<TaskInfo> selectAllTask(Map<String, Object> map);

	/**
	 * 更新任务
	 * @param taskInfo
	 * @return
	 */
	int updateTask(TaskInfo taskInfo);

	/**
	 * 根据任务id查询
	 * @param taskId
	 * @return
	 */
	TaskInfo selectById(String taskId);
	
}
