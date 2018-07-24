package zhitu.sq.dataset.service;

import java.util.Map;


import com.github.pagehelper.PageInfo;


public interface DataSetService {

	/**
	 * 分页查询数据集
	 * @param map
	 * @param userId 
	 * @return
	 */
	PageInfo<Map<String, Object>> queryDateSet(Map<String, Object> map, String userId);

}
