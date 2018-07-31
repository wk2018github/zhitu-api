package zhitu.sq.dataset.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import zhitu.sq.dataset.controller.vo.DataSetRdbVo;
import zhitu.sq.dataset.controller.vo.RdbVo;
import zhitu.sq.dataset.controller.vo.Select;
import zhitu.sq.dataset.model.DataSet;
import zhitu.sq.dataset.model.Rdb;

public interface DataSetService {

	/**
	 * 分页查询数据集
	 * @param map
	 * @param userId 
	 * @return
	 */
	PageInfo<Map<String, Object>> queryDateSet(Map<String, Object> map, String userId)throws Exception;

	/**
	 * 多文件上传保存
	 * @param name
	 * @param describe
	 * @param projectId
	 * @param userId 
	 * @param files
	 * @return
	 */
	int saveLocalDataSet(String userId, String name, String describe, String projectId,
			List<MultipartFile> files)throws Exception;

	/**
	 * 更新数据集信息
	 * @param dSet
	 * @return
	 */
	int updateDataSet(DataSet dSet)throws Exception;
	
	/**
	 * 查询数据集详细信息
	 * @param id
	 * @param typeId 
	 * @return
	 */
	PageInfo<Map<String, Object>> findById(Map<String, Object> map)throws Exception;

	/**
	 * 删除数据集
	 * @param ids
	 * @param typeId 
	 * @param dataTable 
	 * @param rdbId 
	 * @return
	 */
	void deleteById(List<String> ids)throws Exception;

	/**
	 * 保存数据库
	 * @param dRdbVo
	 * @param userId
	 * @return
	 */
	int saveSqlDataSet(DataSetRdbVo dRdbVo, String userId)throws Exception;


	/**
	 * 根据表名和id查询数据
	 * @param map
	 * @return
	 */
	Map<String, Object> findByTableAndId(Map<String, Object> map)throws Exception;

	/**
	 * 根据name 和登录人员id 统计项目下数据集类型
	 * @param name
	 * @param userId
	 * @param projectId 
	 * @return
	 */
	List<Map<String, Object>> chartsByName(String name,String projectId, String userId)throws Exception;

	/**
	 * 根据表查询数据
	 * @param rdbVo
	 * @return
	 */
	PageInfo<Map<String, Object>> findByTable(RdbVo rdbVo) throws Exception;
	
	/**
	 * 根据表查询表字段
	 * @param rdbVo
	 * @return
	 */
	List<String> findByTableFiled(RdbVo rdbVo)throws Exception;
	
	
	/**
	 * @author qwm
	 * @return
	 * @throws Exception
	 */
	List<Select> queryDBType() throws Exception;
	/**
	 * @author qwm
	 * @return
	 * @throws Exception
	 */
	List<String> queryDBTables(Rdb rdb) throws Exception;
	/**
	 * @author qwm
	 * @return
	 * @throws Exception
	 */
	List<String> queryTableData(Map<String, Object> map) throws Exception;
	/**
	 * @author qwm
	 * @return
	 * @throws Exception
	 */
	List<String> queryTableColumn(Map<String, Object> map) throws Exception;
	/**
	 * @author qwm
	 * @return
	 * @throws Exception
	 */
	List<String> queryTableColumnData(Map<String, Object> map) throws Exception;
	/**
	 * @author qwm
	 * @param userId 
	 * @return
	 * @throws Exception
	 */
	List<String> queryLocalTableColumnData(Map<String, Object> map, String userId) throws Exception;


}
