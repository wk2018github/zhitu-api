package zhitu.sq.dataset.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zhitu.sq.dataset.mapper.ProjectMapper;
import zhitu.sq.dataset.service.PorjectService;

@Service
public class PorjectServiceImpl implements PorjectService{

	@Autowired
	private ProjectMapper projectMapper;
}
