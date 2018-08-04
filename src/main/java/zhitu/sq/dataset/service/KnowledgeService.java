package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.model.Knowledge;

public interface KnowledgeService {

	/**
	 * 进入知识库页面
	 * @param map
	 * @return
	 * @throws Exception
	 */
	PageInfo<Map<String, Object>> queryKnowledge(Map<String, Object> map) throws Exception;

	/**
	 * 修改知识库信息
	 * @param knowledge
	 * @return
	 * @throws Exception
	 */
	int updateKnowledge(Knowledge knowledge)throws Exception;

	/**
	 * 批量删除知识库信息
	 * @param ids
	 * @return
	 */
	int deleteKnowledge(List<String> ids)throws Exception;

	/**
	 * 保存知识库信息
	 * @param knowledge
	 * @return
	 */
	int saveKnowledge(Knowledge knowledge)throws Exception;

	/**
	 * 根据数据集id 查询 表字段
	 * @param datasetIds
	 * @return
	 */
	List<Map<String, Object>> selectByDataSetId(List<String> datasetIds)throws Exception;

}