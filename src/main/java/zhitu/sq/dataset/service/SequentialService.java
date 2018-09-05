package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;

public interface SequentialService {
	
	/**
	 * @Author: qwm
	 * @Description: 1单点分析-初始化根节点
	 */
	List<String> queryYearScope() throws Exception;
	
	

}
