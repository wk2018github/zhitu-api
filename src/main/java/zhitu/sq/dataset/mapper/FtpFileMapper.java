package zhitu.sq.dataset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import zhitu.sq.dataset.model.FtpFile;

public interface FtpFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(FtpFile record);

    int insertSelective(FtpFile record);

    FtpFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FtpFile record);

    int updateByPrimaryKeyWithBLOBs(FtpFile record);

    int updateByPrimaryKey(FtpFile record);

	List<Map<String, Object>> findByDataSetId(@Param("datasetId")String datasetId);

	int deleteByDataSetId(@Param("datasetId")String datasetId);
}