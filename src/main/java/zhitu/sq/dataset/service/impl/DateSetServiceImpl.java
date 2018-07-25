package zhitu.sq.dataset.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.DataSetMapper;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.utils.NumberDealHandler;
import zhitu.utils.StringHandler;

@Service
public class DateSetServiceImpl implements DataSetService{

	@Autowired
	private DataSetMapper dataSetMapper;

	public PageInfo<Map<String, Object>> queryDateSet(Map<String, Object> map,String userId) {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		String name = StringHandler.objectToString(map.get("name"));
		List<Map<String, Object>> list = dataSetMapper.findByName(name,userId);
		return new PageInfo<>(list);
	}

}
