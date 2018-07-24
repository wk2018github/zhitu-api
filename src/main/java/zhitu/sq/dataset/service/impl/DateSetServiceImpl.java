package zhitu.sq.dataset.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.mapper.DateSetMapper;
import zhitu.sq.dataset.service.DataSetService;
import zhitu.utils.NumberDealHandler;
import zhitu.utils.StringHandler;

@Service
public class DateSetServiceImpl implements DataSetService{

	@Autowired
	private DateSetMapper dataSetMapper;

	@Override
	public PageInfo<Map<String, Object>> queryDateSet(Map<String, Object> map,String userId) {
		PageHelper.startPage(NumberDealHandler.objectToInt(map.get("page")),
				NumberDealHandler.objectToInt(map.get("rows")));
		String name = StringHandler.objectToString(map.get("name"));
		return dataSetMapper.findByName(name,userId);
	}
}
