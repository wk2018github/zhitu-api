package zhitu.sq.dataset.service;

import java.util.List;

import zhitu.sq.dataset.model.Dict;

public interface DictService {

	/**
	 * 查询知识抽取器下拉框
	 * @return
	 */
	List<Dict> findKnowledge() throws Exception;

	/**
	 * 根据code和parentValue查询信息
	 * @param code
	 * @param parenValue
	 * @return
	 */
	List<Dict> findByCodeAndValue(String code, String parenValue) throws Exception;

	/**
	 * 新增dict数据
	 * @param dict
	 * @return
	 */
	int insert(Dict dict)throws Exception;

	/**
	 * 修改dict数据
	 * @param dict
	 * @return
	 */
	int update(Dict dict)throws Exception;

}
