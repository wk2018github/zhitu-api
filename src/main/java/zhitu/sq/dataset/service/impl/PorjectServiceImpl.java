package zhitu.sq.dataset.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zhitu.sq.dataset.mapper.ProjectMapper;
import zhitu.sq.dataset.model.Project;
import zhitu.sq.dataset.service.PorjectService;

@Service
@Transactional
public class PorjectServiceImpl implements PorjectService{

	@Autowired
	private ProjectMapper projectMapper;

	@Override
	public int saveProject(String userId, String name,String description) throws Exception{
		Project project = new Project();
		project.setCreateTime(new Date());
		project.setId("PROJECT_"+System.currentTimeMillis());
		project.setName(name);
		project.setDescription(description);
		project.setUserId(userId);
		return projectMapper.insert(project);
	}

	@Override
	public List<Map<String, Object>> queryProject(String name, String userId) throws Exception{
		
		return projectMapper.queryProject(name,userId);
	}

	@Override
	public int updateProject(Project project) throws Exception{
		return projectMapper.updateByPrimaryKeySelective(project);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteProject(Map<String, Object> map) {
		List<String> ids = (List<String>) map.get("ids");
		return projectMapper.deleteProject(ids);
	}

}
