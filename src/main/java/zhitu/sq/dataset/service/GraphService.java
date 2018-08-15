package zhitu.sq.dataset.service;


import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.model.Graph;

public interface GraphService {
	
	/**
	 * @author qwm
	 * @description: 查询graph表所有数据  或者按照名称 分页
	 */
	PageInfo<Graph> queryAllGraph(Map<String,Object> map) throws Exception;
	/**
	 * @Author: qwm
	 * @Description:行内编辑 图谱名称和描述
	 */
	boolean editGraph(Graph graph) throws Exception;
	/**
	 * @Author: qwm
	 * @Description:删除图谱
	 */
	boolean deleteGraph(Map<String,Object> map) throws Exception;
	
	
	
	/**
	 * @author qwm
	 * @Description 默认分页
	 */
	Map<String,Object> orderMap(Map<String,Object> map) throws Exception;
	/**
	 * @author qwm
	 * @Description 多个id,分割
	 */
	String splitIds(String ids) throws Exception;
	
}
