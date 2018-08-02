package zhitu.sq.dataset.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.KnowledgeMapper;
import zhitu.sq.dataset.model.Knowledge;
import zhitu.sq.dataset.service.KnowledgeService;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;

@Service
public class KnowledgeServiceImpl implements KnowledgeService{

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Override
	public PageInfo<Map<String, Object>> queryKnowledge(Map<String, Object> map) throws Exception {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		String name = StringHandler.objectToString(map.get("name"));
		List<Map<String, Object>> list = knowledgeMapper.findByName(name);
		return new PageInfo<>(list);
	}

	@Override
	public int updateKnowledge(Knowledge knowledge) throws Exception {
		return knowledgeMapper.updateByPrimaryKeySelective(knowledge);
	}

	@Override
	public int deleteKnowledge(List<String> ids) {
		return knowledgeMapper.deleteKnowledge(ids);
	}

	@Override
	public int saveKnowledge(Knowledge knowledge) {
		knowledge.setId("KN_"+System.currentTimeMillis());
		knowledge.setCreateTime(new Date());
		return knowledgeMapper.insert(knowledge);
	}
}
