package zhitu.sq.dataset.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhitu.sq.dataset.mapper.RdbMapper;
import zhitu.sq.dataset.service.RdbService;

@Service
public class RdbServiceImpl implements RdbService{

	@Autowired
	private RdbMapper rdbMapper;
}
