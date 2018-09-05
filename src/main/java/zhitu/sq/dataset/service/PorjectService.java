package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import zhitu.sq.dataset.model.Project;

public interface PorjectService {

	/**
	 * 保存项目
	 * @param id
	 * @param name
	 * @param description
	 * @return
	 */
	int saveProject(String id, String name, String description) throws Exception;

	/**
	 * 查询项目
	 * @param name
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> queryProject(String name, String id)throws Exception;

	/**
	 * 保存修改的项目
	 * @param project
	 * @return
	 */
	int updateProject(Project project)throws Exception;

	/**
	 * 删除项目
	 * @param map
	 * @return
	 */
	int deleteProject(Map<String, Object> map);

}
