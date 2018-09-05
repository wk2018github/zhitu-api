package zhitu.sq.dataset.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import zhitu.sq.dataset.controller.vo.SuspendDetail;


@Mapper
public interface SinglePointMapper {
	
	/**
	 * @author qwm
	 * 查询单点分析根节点
	 */
	String queryRootSinglePoint();
	
	/**
	 * @author qwm
	 * 查询4个初始点的text
	 */
	List<SuspendDetail> queryProcessNodeText();
	
	
}