package zhitu.sq.dataset.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.NodeDetail;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.mapper.GraphMapper;
import zhitu.sq.dataset.mapper.SequentialMapper;
import zhitu.sq.dataset.mapper.SinglePointMapper;
import zhitu.sq.dataset.service.GraphService;
import zhitu.sq.dataset.service.SequentialService;
import zhitu.sq.dataset.service.SinglePointService;
import zhitu.util.NumberDealHandler;
import zhitu.util.StringHandler;
import zhitu.util.MyTest.RuleNode;
import zhitu.vgraph.Graphs;
import zhitu.vgraph.Node;
import zhitu.vgraph.NodeTypes;

@Service
@Transactional
public class SequentialServiceImpl implements SequentialService {

	@Autowired
	private SequentialMapper sequentialMapper;
	
	@Override
	public List<Select> queryAnalysisSelect() throws Exception {

		return sequentialMapper.queryAnalysisSelect();
	}
	
	@Override
	public List<Select> querySelectOne() throws Exception {

		return sequentialMapper.querySelectOne();
	}
	
	@Override
	public List<Select> querySelectTwo(Map<String,Object> map) throws Exception {

		return sequentialMapper.querySelectTwo(map);
	}

	@Override
	public List<String> queryYearScope() throws Exception {

//		String rootName = singlePointMapper.queryRootSinglePoint();


		return null;
	}


}
