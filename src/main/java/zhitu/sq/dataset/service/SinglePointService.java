package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.Select;

public interface SinglePointService {
	
	/**
	 * @Author: qwm
	 * @Description: 1单点分析-初始化根节点
	 */
	String initRootSinglePoint() throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 1.2单点分析-环形菜单
	 */
	List<String> initAnnularData() throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 1.3单点分析-查询表节点的过滤器中的具体的值
	 */
	List<Select> queryTableFilter(Map<String, Object> map) throws Exception;

}
