package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.controller.vo.SuspendDetail;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.model.ProcessGraph;

public interface GraphService {

	/**
	 * @author qwm
	 * @description: 查询graph表所有数据 或者按照名称 分页
	 */
	PageInfo<Graph> queryAllGraph(Map<String, Object> map) throws Exception;

	/**
	 * @Author: qwm
	 * @Description:行内编辑 图谱名称和描述
	 */
	boolean editGraph(Graph graph) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:更新图谱
	 */
	Graph updateGraph(Map<String, Object> map) throws Exception;

	/**
	 * @Author: qwm
	 * @Description:删除图谱
	 */
	boolean deleteGraph(Map<String, Object> map) throws Exception;

	/**
	 * @Author: qwm
	 * @Description: 查询过滤器下面具体的值
	 */
	List<Select> queryTableFilter(Map<String, Object> map) throws Exception;

	/**
	 * @author qwm
	 * @Description 默认分页
	 */
	Map<String, Object> orderMap(Map<String, Object> map) throws Exception;

	/**
	 * @author qwm
	 * @Description 多个id,分割
	 */
	String splitIds(String ids) throws Exception;

	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-加载四个初始节点
	 */
	String initProcessAnalysis() throws Exception;

	/**
	 * @Author: qwm
	 * @Description: 图谱分析-流程分析-初始4个节点环形菜单
	 */
	List<String> initAnnularData() throws Exception;

	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-添加过滤器节点
	 */
	Map<String, Object> addFilterNode(Map<String, Object> map) throws Exception;

	/**
	 * @Author: qwm
	 * @Description: 图谱分析-流程分析-过滤器节点环形菜单
	 */
	List<String> queryFilterNodeAnnularData(Map<String,Object> map) throws Exception;

	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-查询过滤器节点下级菜单的值
	 */
	List<Select> queryFilterNodeLowerLevelMenu(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-添加其他表节点线
	 */
	Map<String, Object> addOtherNodesLine(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-点击节点上的查看详情
	 */
	PageInfo<NodeDetail> queryNodeDetails(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-鼠标悬浮节点显示信息
	 */
	List<SuspendDetail> querySuspendDetails(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:图谱分析-流程分析-删除当前节点
	 */
	Map<String, Object> deleteThisNode(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:2.1图谱分析-流程分析-保存当前流程图
	 */
	Map<String, Object> saveProcessGraph(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:2.1图谱分析-流程分析-查询流程图列表
	 */
	List<ProcessGraph> queryProcessGraph(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description: 图谱分析-流程分析-删除流程图列表
	 */
	Map<String, Object> deleteProcessGraph(Map<String, Object> map) throws Exception;
	
	/**
	 * @Author: qwm
	 * @Description:写死 鼠标悬停4个表的后面简介内容
	 */
	SuspendDetail newSuspendDetail(SuspendDetail sd) throws Exception;

}
