package zhitu.sq.dataset.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zhitu.sq.dataset.mapper.DictMapper;
import zhitu.sq.dataset.model.Dict;
import zhitu.sq.dataset.service.DictService;

@Service
@Transactional
public class DictServiceImpl implements DictService{

	@Autowired
	private DictMapper dictMapper;

	@Override
	public List<Dict> findKnowledge() throws Exception {
		return dictMapper.findKnowledge();
	}

	@Override
	public List<Dict> findByCodeAndValue(String code, String parentValue) throws Exception {
		return dictMapper.findByCodeAndValue(code,parentValue);
	}

	@Override
	public int insert(Dict dict) throws Exception{
		return dictMapper.insert(dict);
	}

	@Override
	public int update(Dict dict) throws Exception {
		return dictMapper.updateByPrimaryKeySelective(dict);
	}
}
