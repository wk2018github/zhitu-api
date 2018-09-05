package zhitu.sq.dataset.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import zhitu.sq.dataset.model.Graph;


@Mapper
public interface SequentialMapper {
	
	/**
	 * @author qwm
	 * 查询单点分析根节点
	 */
	String queryRootSinglePoint();
	
	
}