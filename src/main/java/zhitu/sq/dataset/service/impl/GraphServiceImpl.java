package zhitu.sq.dataset.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.GraphMapper;
import zhitu.sq.dataset.model.Graph;
import zhitu.sq.dataset.service.GraphService;
import zhitu.util.NumberDealHandler;

@Service
@Transactional
public class GraphServiceImpl implements GraphService{

	@Autowired
	private GraphMapper graphMapper;

	@Override
	public PageInfo<Graph> queryAllGraph(Map<String,Object> map) throws Exception {
		
		map = orderMap(map);
		
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		PageHelper.orderBy(map.get("sidx") + " " + map.get("sord"));
		List<Graph> list = graphMapper.queryAllGraph(map);
		
		return new PageInfo<Graph>(list);
	}
	

	@Override
	public boolean editGraph(Graph graph) throws Exception {
		int i = graphMapper.editGraph(graph);
		return i > 0;
	}
	@Override
	public boolean deleteGraph(Map<String,Object> map) throws Exception {
		String ids = splitIds(map.get("ids").toString());
		int i = graphMapper.deleteGraph(ids);
		return i > 0;
	}
	
	

	@Override
	public Map<String,Object> orderMap(Map<String,Object> map) throws Exception {
		if(null == map.get("page") || 
				StringUtils.isEmpty(map.get("page").toString())){
			map.put("page",1);
		}
		if(null == map.get("rows") || 
				StringUtils.isEmpty(map.get("rows").toString())){
			map.put("rows",10);
		}
		
		if(null == map.get("sidx") || 
				StringUtils.isEmpty(map.get("sidx").toString())){
			map.put("sidx","createTime");
		}
		if(null == map.get("sord") || 
				StringUtils.isEmpty(map.get("sord").toString())){
			map.put("sord","desc");
		}
		
		return map;
	}
	
	@Override
	public String splitIds(String ids) throws Exception{
		if(StringUtils.isEmpty(ids)){
			return ids;
		}
		StringBuffer sb = new StringBuffer();
		String[] split = ids.split(",");
		for (String s : split) {
			sb.append("'").append(s).append("',");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}






	
	
}
