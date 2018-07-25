package zhitu.sq.dataset.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.model.DataSet;

public interface DataSetService {

	/**
	 * 分页查询数据集
	 * @param map
	 * @param userId 
	 * @return
	 */
	PageInfo<Map<String, Object>> queryDateSet(Map<String, Object> map, String userId);

	/**
	 * 多文件上传保存
	 * @param name
	 * @param describe
	 * @param projectId
	 * @param userId 
	 * @param files
	 * @return
	 */
	int saveLocalDataSet(String userId,String name, String describe, String projectId, MultipartFile[] files);

	/**
	 * 根据id查询数据集详细信息
	 * @param id
	 * @return
	 */
	DataSet findById(String id);

	/**
	 * 更新数据集信息
	 * @param dSet
	 * @return
	 */
	int updateDataSet(DataSet dSet);

}
