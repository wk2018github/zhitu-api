package zhitu.sq.dataset.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.TaskInfoMapper;
import zhitu.sq.dataset.model.TaskInfo;
import zhitu.sq.dataset.service.TaskInfoService;
import zhitu.util.NumberDealHandler;

@Service
public class TaskInfoServiceImpl implements TaskInfoService{

	@Autowired
	private TaskInfoMapper taskInfoMapper;

	@Override
	public PageInfo<TaskInfo> selectAllTask(Map<String, Object> map) {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		List<TaskInfo> list = taskInfoMapper.selectAllTask();
		return new PageInfo<>(list);
	}

	@Override
	public int updateTask(TaskInfo taskInfo) {
		return taskInfoMapper.updateTask(taskInfo);
	}
	
	
}
