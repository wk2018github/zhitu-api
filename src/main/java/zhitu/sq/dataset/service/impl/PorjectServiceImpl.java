package zhitu.sq.dataset.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zhitu.sq.dataset.mapper.ProjectMapper;
import zhitu.sq.dataset.service.PorjectService;

@Service
@Transactional
public class PorjectServiceImpl implements PorjectService{

	@Autowired
	private ProjectMapper projectMapper;
}
