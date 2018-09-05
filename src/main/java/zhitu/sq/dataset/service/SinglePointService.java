package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.SuspendDetail;

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
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-单点分析-添加过滤器节点
	 */
	Map<String, Object> addFilterNode(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 图谱分析-单点分析-过滤器节点环形菜单
	 */
	List<String> queryFilterNodeAnnularData(Map<String,Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-单点分析-查询过滤器节点下级菜单的值
	 */
	List<Select> queryFilterNodeLowerLevelMenu(Map<String,Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-单点分析-点击节点上的查看详情
	 */
	PageInfo<NodeDetail> queryNodeDetails(Map<String,Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-单点分析-添加流程节点
	 */
	Map<String, Object> addProcessNode(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-单点分析-鼠标悬浮节点显示信息
	 */
	List<SuspendDetail> querySuspendDetails(Map<String, Object> map) throws Exception;
	

}
